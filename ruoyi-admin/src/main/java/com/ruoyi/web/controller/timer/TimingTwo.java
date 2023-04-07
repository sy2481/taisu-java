package com.ruoyi.web.controller.timer;

import com.ruoyi.base.bo.FactoryBo;
import com.ruoyi.base.bo.FactoryWorkBO;
import com.ruoyi.base.bo.WorkBo;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.service.*;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.service.impl.InOutLogBasicDataServiceImpl;
import com.ruoyi.base.service.impl.WorkDataService;
import com.ruoyi.common.constant.TsConstant;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.timer.domain.CarInfo;
import com.ruoyi.timer.domain.MrVw11er0;
import com.ruoyi.timer.domain.Safetyman;
import com.ruoyi.timer.domain.TimInOutLog;
import com.ruoyi.timer.mapper.MrVw11er0Mapper;
import com.ruoyi.timer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class TimingTwo {
    @Autowired
    private RedisCache redisUtils;

    @Autowired
    private ITimInOutLogService inOutLogService;

    @Autowired
    private WorkDataService workDataService;

    @Autowired
    private ThreadPoolConfig pool;

    @Autowired
    private PlateSendService plateSendService;

    @Autowired
    private IManFactoryService factoryService;

    @Autowired
    private ApiService apiService;
    @Autowired
    private IDangerWorkService dangerWorkService;
    @Autowired
    private IOaMaxInfoService oaMaxInfoService;

    @Autowired
    private SafetymanService safetymanService;

    @Autowired
    private SafetycarService safetycarService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private IPersonBindService personBindService;

    @Value("${scheduling.getFactoryMsgEnabled}")
    private boolean getFactoryMsgEnabled;

    @Value("${scheduling.getDangerWorkEnabled}")
    private boolean getDangerWorkEnabled;
    @Value("${scheduling.getCarTypeEnable}")
    private boolean getCarTypeEnable;

    @Value("${scheduling.getFactoryEnabled}")
    private boolean getFactoryEnabled;

    @Autowired
    private InOutLogBasicDataService inOutLogBasicDataService;

    @Autowired
    private MrVw11er0Service mrVw11er0Service;

    @Autowired
    private BaseErpService baseErpService;

    @Autowired
    private InOutUserStatusService inOutUserStatusService;

    @Autowired
    private PeakInfoService peakInfoService;

    /**
     * 厂区编号
     */
    @Value("${factoryCode}")
    private String factoryCode;

    /**
     * 5分钟，厂商人员数据
     */
    @Scheduled(cron = "0/90 * * * * ?")
    //@Scheduled(cron = "*/30 * * * * ?")
    private void getFactoryMsg() {
        if (!getFactoryMsgEnabled) {
            return;
        }

        try {
            OaMaxInfo oaMaxInfo = oaMaxInfoService.initOaMaxInfo(factoryCode, TsConstant.IN_OUT_LOG_TABLE);
            int maxId = Integer.valueOf(oaMaxInfo.getMaxId().toString());
            Map<String, Object> sourceData = inOutLogService.getInOutLog(maxId, factoryCode);
            List<WorkBo> aeWorkBo = new ArrayList<>();
            if (sourceData.get("newMaxId") != null && sourceData.get("workBos") != null) {
                int newMaxId = Integer.valueOf(sourceData.get("newMaxId").toString());
                List<WorkBo> inOutLogs = (List<WorkBo>) sourceData.get("workBos");
                aeWorkBo = inOutLogs;
                //厂商人员从中心库获取头像信息
                workDataService.setFaceFromCenter(inOutLogs);

                for (WorkBo workBo : inOutLogs) {
                    //拿到每一个bo对象
                    if (workBo == null || StringUtils.isBlank(workBo.getIP()) || StringUtils.isBlank(workBo.getDatetime())) {
                        continue;
                    }
                    workDataService.saveWorkBo(workBo);
                    //信息下发
                    infoDown(workBo);
                }

                //最后更新一下maxId
                oaMaxInfo.setMaxId(Long.valueOf(newMaxId));
                oaMaxInfoService.updateOaMaxInfo(oaMaxInfo);

            }

            //5点以后处理延期的工单
            /*Date begRunTime= DateUtils.parseDate(DateUtils.dateTimeNow("yyyy-MM-dd")+" 17:00:00");
            if(DateUtils.getNowDate().compareTo(begRunTime)>=0){

            }*/
            List<WorkBo> sourceDataExtend = inOutLogService.getInOutLogExtend(maxId, factoryCode);

            for (WorkBo workBo : sourceDataExtend) {
                //拿到每一个bo对象
                if (workBo == null || StringUtils.isBlank(workBo.getIP()) || StringUtils.isBlank(workBo.getDatetime())) {
                    continue;
                }
                workDataService.saveWorkBo(workBo);
            }

            if (factoryCode.equals("PPC2A01")) {
                for (int i = 0; i < aeWorkBo.size(); i++) {
                    List<FactoryBo> aeFactoryBos = aeWorkBo.get(i).getFactoryBoList();
                    if (aeFactoryBos.size() > 0) {
                        for (int j = 0; j < aeFactoryBos.size(); j++) {
                            personBindService.updateFactoryName(aeFactoryBos.get(j).getIdCard(), aeFactoryBos.get(j).getFactoryName());
                        }
                    }
                }
            }

            // HttpUtils.sendJsonPost("http://127.0.0.1:36653/api/timer/InOutLogData", JSON.toJSONString(inOutLog));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/20 * * * * ?")
    //@Scheduled(cron = "*/30 * * * * ?")
    private void getFactoryInOutLog() {
        if (!getFactoryEnabled) {
            return;
        }
        try {
            OaMaxInfo oaMaxInfo = oaMaxInfoService.initOaMaxInfo(factoryCode, TsConstant.IN_OUT_LOG_TABLE_DATA);
            int maxId = Integer.valueOf(oaMaxInfo.getMaxId().toString());
            List<TimInOutLog> sourceData = inOutLogService.getInOutLogBasicData(maxId, factoryCode);
            if (sourceData != null && sourceData.size() > 0) {
                for (TimInOutLog data : sourceData) {
                    InOutLogBasicData inOutLogBasicData = new InOutLogBasicData();
                    inOutLogBasicData.setAid(data.getAid().longValue());
                    inOutLogBasicData.setWorkTime(data.getDatetime());
                    inOutLogBasicData.setAreaNo(data.getAreano());
                    inOutLogBasicData.setIp(data.getIp());
                    inOutLogBasicData.setVhNo(data.getVhno());
                    inOutLogBasicData.setEgNo(data.getEgno());
                    inOutLogBasicData.setEgName(data.getEgnm());
                    inOutLogBasicData.setOprEnvt(data.getOprenvt21());
                    inOutLogBasicData.setIdno(data.getIdno());
                    inOutLogBasicData.setName(data.getNm());
                    inOutLogBasicData.setIpltLic(data.getIpltlic());
                    inOutLogBasicData.setPz(data.getPz());
                    inOutLogBasicData.setTkVnd(data.getTkvnd());
                    inOutLogBasicData.setBeginTime(data.getBegtime());
                    inOutLogBasicData.setEndTime(data.getEndtime());
                    inOutLogBasicData.setInTime(data.getIntime());
                    inOutLogBasicData.setInspector(data.getInspector());
                    inOutLogBasicData.setMark(data.getMk());
                    inOutLogBasicData.setProfsId(data.getProfsid());
                    inOutLogBasicData.setFctDoorNm(data.getFctdornm());
                    inOutLogBasicData.setInOrOutFlag(0);
                    inOutLogBasicDataService.insertInOutLogBasicData(inOutLogBasicData);
                }
                oaMaxInfo.setMaxId(Long.valueOf(sourceData.get(0).getAid()));
                oaMaxInfoService.updateOaMaxInfo(oaMaxInfo);
            }
        } catch (Exception e) {

        }
    }

    @Scheduled(cron = "0 */1  * * * ?")
    private void getFactoryERP() {

        int base = baseErpService.selectCount();
        int mrVw = mrVw11er0Service.selectMrVw11er0Count();
        if (mrVw != base) {
            List<MrVw11er0> mrVw11er0List = mrVw11er0Service.selectMrVw11er0AllList();
            if (mrVw11er0List != null && mrVw11er0List.size() > 0) {
                for (MrVw11er0 mrVw11er0 : mrVw11er0List) {
                    BaseErp baseErp = new BaseErp();
                    baseErp.setVhNo(mrVw11er0.getVhno());
                    List<BaseErp> baseErps = baseErpService.selectBaseErpList(baseErp);
                    if (baseErps != null && baseErps.size() > 0) {

                    } else {
                        BaseErp newBaseErp = new BaseErp();
                        newBaseErp.setVhNo(mrVw11er0.getVhno());
                        newBaseErp.setAplDp(mrVw11er0.getApldp());
                        newBaseErp.setEgNm(mrVw11er0.getEgnm());
                        newBaseErp.setEgNo(mrVw11er0.getEgno());
                        newBaseErp.setCnstNum(mrVw11er0.getCnstnum());
                        newBaseErp.setCnstSite(mrVw11er0.getCnstsite());
                        newBaseErp.setTkVnd(mrVw11er0.getTkvnd());
                        newBaseErp.setTkVndSafBos(mrVw11er0.getTkvndsafbos());
                        newBaseErp.setCnstType(mrVw11er0.getCnsttype15());
                        newBaseErp.setCnstTime(mrVw11er0.getCnsttime19());
                        newBaseErp.setOprEnvt(mrVw11er0.getOprenvt21());
                        newBaseErp.setTaskMaster(mrVw11er0.getTaskmaster22());
                        newBaseErp.setInspector(mrVw11er0.getInspector23());
                        newBaseErp.setMlType(mrVw11er0.getMltype());
                        newBaseErp.setSafetyManMk(mrVw11er0.getSafetymanmk());
                        newBaseErp.setIpltDatTm(mrVw11er0.getIpltdattm());
                        newBaseErp.setOpltDatTm(mrVw11er0.getOpltdattm());
                        newBaseErp.setJbcont(mrVw11er0.getJbcont());
                        newBaseErp.setPtnm(mrVw11er0.getPtnm());
                        newBaseErp.setStartwork(mrVw11er0.getStartwork());
                        newBaseErp.setJsa(mrVw11er0.getJsa());
                        baseErpService.insertBaseErp(newBaseErp);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 5 0 * * ?")
    private void deleteFactoryERP() {
        baseErpService.deleteBaseErpByVhNo();
        peakInfoService.clearPeakInfo();
    }


    @Scheduled(cron = "10 1/5 * * * ? ")
    private void getDangerWork() {
        if (!getDangerWorkEnabled) {
            return;
        }

        try {
            List<WorkBo> list = dangerWorkService.dangerWorkData();
            if (list == null || list.size() == 0) {
                return;
            }
            for (WorkBo workBo : list) {
                if (workBo == null || StringUtils.isBlank(workBo.getIP()) || StringUtils.isBlank(workBo.getDatetime())) {
                    continue;
                }
                workDataService.saveDangerWork(workBo);
                //TODO 危险品的人员信息要自动下发
                infoDown(workBo);
            }
            // HttpUtils.sendJsonPost("http://127.0.0.1:36653/api/timer/dangerWorkData", JSON.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 信息下发
     */
    private void infoDown(WorkBo workBo) {
        try {
            pool.threadPoolTaskExecutor().execute(() -> plateSendService.timerWorkPlate(workBo.getWorkNumber()));
            //最后调用下厂商人脸下发的方法
            pool.threadPoolTaskExecutor().execute(() -> {
                // 然后把人员也下发一遍；工单号->厂商人员->下发
                List<FactoryWorkBO> factoryWorkBOS = factoryService.listByWorkNoAndDate(workBo.getWorkNumber(), DateUtils.getDate(), 0);
                Long[] ids = new Long[factoryWorkBOS.size()];
                for (int i = 0; i < factoryWorkBOS.size(); i++) {
                    ids[i] = Long.parseLong(factoryWorkBOS.get(i).factoryId);
                }
                apiService.sendFactoryMsgList(ids);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 定时拉取核卡车辆
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void getSaftCar() {
        //获取核卡数据
        List<Safetyman> list = safetymanService.getCarList();
        //System.out.println("list = " + list);
        //查询不为空,存入list,保存数据
        if (!CollectionUtils.isEmpty(list)) {
            /*
             * 将获取到的车辆信息截取部分信息存入
             * 存入之前判断是否存在
             * */
            BaseSafetycar baseSafetycar;
            for (Safetyman safetyman : list) {
                BaseSafetycar exist = safetycarService.isExist(safetyman.getIpltlic());
                if (exist == null) {
                    baseSafetycar = new BaseSafetycar();
                    baseSafetycar.setIdno(safetyman.getIdno());
                    baseSafetycar.setWorkName(safetyman.getNm());
                    baseSafetycar.setIpLtLic(safetyman.getIpltlic());
                    baseSafetycar.setPz(safetyman.getPz());
                    safetycarService.insertCarlist(baseSafetycar);
                }
            }
//            safetycarService.insertCarlist(list);
        }

    }

    /**
     * 定时拉取核卡车辆
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 3/10 * * * ? ")
    public void getCarInfo() {
        if (!getCarTypeEnable) {
            return;
        }
        //获取核卡数据
        List<BaseSafetycar> list = safetycarService.getSafetycarByNoCarType();
        //查询不为空,存入list,保存数据
        if (!CollectionUtils.isEmpty(list)) {
            /*
             * 将获取到的车辆信息截取部分信息存入
             * 存入之前判断是否存在
             * */
            for (BaseSafetycar baseSafetycar : list) {
                List<CarInfo> carInfos = carInfoService.getCarList(baseSafetycar.getIdno().substring(1));
                if (carInfos != null && carInfos.size() > 0) {
                    String carType = carInfos.get(0).getCarType();
                    //S 1大车 B 2小车
                    if (carType.equals("S")) {
                        baseSafetycar.setCarType(1L);
                        baseSafetycar.setCarTypeName("大車");
                    } else {
                        baseSafetycar.setCarType(2L);
                        baseSafetycar.setCarTypeName("小車");
                    }

                } else {
                    baseSafetycar.setCarType(2L);
                    baseSafetycar.setCarTypeName("小車");
                }
                safetycarService.updateSafetyCarType(baseSafetycar);
            }

        }

    }

    @Scheduled(cron = "0 */1 * * * ? ")
    public void getPeakNum() {
        if (!getCarTypeEnable) {
            Integer personInNum = inOutLogBasicDataService.selectPersonInNum();
            Integer carInNum = inOutLogBasicDataService.selectCarInNum();
            Integer manFactoryInNum = inOutLogBasicDataService.selectPersonInNum();
            PeakInfo peakInfo = new PeakInfo();
            peakInfo.setPeakType("peakPerson");
            List<PeakInfo> list = peakInfoService.selectPeakInfoList(peakInfo);
            if (list != null && list.size() > 0) {
                PeakInfo personPeakInfo = list.get(0);
                if (personInNum + manFactoryInNum > personPeakInfo.getPeakCount()) {
                    personPeakInfo.setPeakCount(personInNum + manFactoryInNum);
                    peakInfoService.updatePeakInfo(personPeakInfo);
                }
            } else {
                PeakInfo insertPeakInfo = new PeakInfo();
                insertPeakInfo.setPeakType("peakPerson");
                insertPeakInfo.setPeakCount(personInNum + manFactoryInNum);
                peakInfoService.insertPeakInfo(insertPeakInfo);
            }
//            PeakInfo personPeakInfo = peakInfoService.selectPeakInfoList(peakInfo).get(0);
//            if (personPeakInfo == null) {
//                PeakInfo insertPeakInfo = new PeakInfo();
//                insertPeakInfo.setPeakType("peakPerson");
//                insertPeakInfo.setPeakCount(personInNum + manFactoryInNum);
//                peakInfoService.insertPeakInfo(insertPeakInfo);
//            } else {
//                if (personInNum + manFactoryInNum > personPeakInfo.getPeakCount()) {
//                    personPeakInfo.setPeakCount(personInNum + manFactoryInNum);
//                    peakInfoService.updatePeakInfo(personPeakInfo);
//                }
//            }
            peakInfo = new PeakInfo();
            peakInfo.setPeakType("peakCar");
            list = peakInfoService.selectPeakInfoList(peakInfo);
            if (list != null && list.size() > 0) {
                PeakInfo carPeakInfo = list.get(0);
                if (carInNum > carPeakInfo.getPeakCount()) {
                    carPeakInfo.setPeakCount(carInNum);
                    peakInfoService.updatePeakInfo(carPeakInfo);
                }
            } else {
                PeakInfo insertPeakInfo = new PeakInfo();
                insertPeakInfo.setPeakType("peakCar");
                insertPeakInfo.setPeakCount(carInNum);
                peakInfoService.insertPeakInfo(insertPeakInfo);
            }

//            peakInfo = new PeakInfo();
//            peakInfo.setPeakType("peakCar");
//            PeakInfo carPeakInfo = peakInfoService.selectPeakInfoList(peakInfo).get(0);
//            if (carPeakInfo == null) {
//                PeakInfo insertPeakInfo = new PeakInfo();
//                insertPeakInfo.setPeakType("peakCar");
//                insertPeakInfo.setPeakCount(carInNum);
//            } else {
//                if (carInNum > carPeakInfo.getPeakCount()) {
//                    carPeakInfo.setPeakCount(carInNum);
//                    peakInfoService.updatePeakInfo(carPeakInfo);
//                }
//            }

        }
    }

}

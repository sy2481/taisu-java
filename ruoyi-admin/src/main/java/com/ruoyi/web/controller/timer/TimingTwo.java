package com.ruoyi.web.controller.timer;

import com.ruoyi.base.bo.FactoryWorkBO;
import com.ruoyi.base.bo.WorkBo;
import com.ruoyi.base.domain.BaseSafetycar;
import com.ruoyi.base.domain.OaMaxInfo;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.base.service.IOaMaxInfoService;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.service.impl.WorkDataService;
import com.ruoyi.common.constant.TsConstant;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.timer.domain.Safetyman;
import com.ruoyi.timer.service.IDangerWorkService;
import com.ruoyi.timer.service.ITimInOutLogService;
import com.ruoyi.base.service.SafetycarService;
import com.ruoyi.timer.service.SafetymanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.util.CollectionUtils;


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

    @Value("${scheduling.getFactoryMsgEnabled}")
    private boolean getFactoryMsgEnabled;

    @Value("${scheduling.getDangerWorkEnabled}")
    private boolean getDangerWorkEnabled;


    /**
     * ????????????
     */
    @Value("${factoryCode}")
    private String factoryCode;

    /**
     * 5???????????????????????????
     */
    @Scheduled(cron = "0/90 * * * * ?")
    //@Scheduled(cron = "*/30 * * * * ?")
    private void getFactoryMsg() {
        if(!getFactoryMsgEnabled){
            return;
        }

        try {
            OaMaxInfo oaMaxInfo = oaMaxInfoService.initOaMaxInfo(factoryCode, TsConstant.IN_OUT_LOG_TABLE);
            int maxId = Integer.valueOf(oaMaxInfo.getMaxId().toString());
            Map<String, Object> sourceData = inOutLogService.getInOutLog(maxId,factoryCode);

            if (sourceData.get("newMaxId") != null && sourceData.get("workBos") != null) {
                int newMaxId = Integer.valueOf(sourceData.get("newMaxId").toString());
                List<WorkBo> inOutLogs = (List<WorkBo>) sourceData.get("workBos");

                //??????????????????????????????????????????
                workDataService.setFaceFromCenter(inOutLogs);

                for (WorkBo workBo : inOutLogs) {
                    //???????????????bo??????
                    if (workBo == null || StringUtils.isBlank(workBo.getIP()) || StringUtils.isBlank(workBo.getDatetime())) {
                        continue;
                    }
                    workDataService.saveWorkBo(workBo);
                    //????????????
                    infoDown(workBo);
                }

                //??????????????????maxId
                oaMaxInfo.setMaxId(Long.valueOf(newMaxId));
                oaMaxInfoService.updateOaMaxInfo(oaMaxInfo);

            }

            //5??????????????????????????????
            /*Date begRunTime= DateUtils.parseDate(DateUtils.dateTimeNow("yyyy-MM-dd")+" 17:00:00");
            if(DateUtils.getNowDate().compareTo(begRunTime)>=0){

            }*/
            List<WorkBo> sourceDataExtend = inOutLogService.getInOutLogExtend(maxId,factoryCode);

            for (WorkBo workBo : sourceDataExtend) {
                //???????????????bo??????
                if (workBo == null || StringUtils.isBlank(workBo.getIP()) || StringUtils.isBlank(workBo.getDatetime())) {
                    continue;
                }
                workDataService.saveWorkBo(workBo);
            }


            // HttpUtils.sendJsonPost("http://127.0.0.1:36653/api/timer/InOutLogData", JSON.toJSONString(inOutLog));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    private void getDangerWork() {
        if(!getDangerWorkEnabled){
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
                //TODO ???????????????????????????????????????
                infoDown(workBo);
            }
            // HttpUtils.sendJsonPost("http://127.0.0.1:36653/api/timer/dangerWorkData", JSON.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ????????????
     */
    private void infoDown(WorkBo workBo) {
        try {
            pool.threadPoolTaskExecutor().execute(() -> plateSendService.timerWorkPlate(workBo.getWorkNumber()));
            //??????????????????????????????????????????
            pool.threadPoolTaskExecutor().execute(() -> {
                // ??????????????????????????????????????????->????????????->??????
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
     * ????????????????????????
     * */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void getSaftCar(){
        //??????????????????
        List<Safetyman> list = safetymanService.getCarList();
        System.out.println("list = " + list);
        //???????????????,??????list,????????????
        if (!CollectionUtils.isEmpty(list)){
            /*
             * ???????????????????????????????????????????????????
             * ??????????????????????????????
             * */
            BaseSafetycar baseSafetycar;
            for (Safetyman safetyman : list) {
                BaseSafetycar exist = safetycarService.isExist(safetyman.getIpltlic());
                if (exist == null){
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

}

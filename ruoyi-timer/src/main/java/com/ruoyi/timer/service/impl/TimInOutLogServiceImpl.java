package com.ruoyi.timer.service.impl;

import com.ruoyi.base.bo.CarBo;
import com.ruoyi.base.bo.FactoryBo;
import com.ruoyi.base.bo.WorkBo;
import com.ruoyi.base.domain.InOutLogPvc;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.timer.domain.TimInOutLog;
import com.ruoyi.timer.mapper.TimInOutLogMapper;
import com.ruoyi.timer.service.ITimInOutLogService;
import com.ruoyi.timer.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@DataSource(value = DataSourceType.IEMDG)
public class TimInOutLogServiceImpl implements ITimInOutLogService {
    @Autowired
    private TimInOutLogMapper timInOutLogMapper;

    @Override
    public Map<String, Object> getInOutLog(Integer maxId,String factoryCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        Integer newMaxId = maxId;
        List<WorkBo> workBos = new ArrayList<>();
        Date today = DateUtils.parseDate(DateUtils.getDate());
        Date nextDay = DateUtils.addDays(today, 1);

        //拿到全部的当天记录
        List<TimInOutLog> allLogs=null;
        if(factoryCode.equals("PPC8601")) {
             allLogs = timInOutLogMapper.getInOutLogEVA(DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS1, today),  DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS1, nextDay), maxId, factoryCode);
        }
        else{
            allLogs= timInOutLogMapper.getInOutLog(today, nextDay, maxId, factoryCode);
        }
        if (allLogs == null || allLogs.size() == 0) {
            return result;
        }
        //新的最大ID
        newMaxId = allLogs.stream().map(TimInOutLog::getAid).max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        }).get();

        workBos = transToWorkBoList(allLogs);

        result.put("newMaxId", newMaxId);
        result.put("workBos", workBos);
        return result;
    }

    @Override
    public List<WorkBo> getInOutLogExtend(Integer maxId,String factoryCode){
        List<WorkBo> workBos = new ArrayList<>();
        Date today = DateUtils.parseDate(DateUtils.getDate());
        Date nextDay = DateUtils.addDays(today, 1);

        List<TimInOutLog> extendLogs=null;
        //延时数据
        if(factoryCode.equals("PPC8601")){
            extendLogs=timInOutLogMapper.getInOutLogExtendEVA(DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS1, today),  DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS1, nextDay),maxId, factoryCode);
        }else {
           extendLogs = timInOutLogMapper.getInOutLogExtend(today, nextDay, maxId, factoryCode);
        }
        workBos = transToWorkBoList(extendLogs);

        return workBos;
    }

    //OA数据转为工单数据
    private List<WorkBo> transToWorkBoList(List<TimInOutLog> allLogs) {
        List<WorkBo> workBos = new ArrayList<>();

        //开始处理数据
        Map<String, List<TimInOutLog>> map = allLogs.stream().collect(Collectors.groupingBy(TimInOutLog::getVhno));
        map.forEach((no, list) -> {
            //开始处理数据
            // 把工单（厂商、危险品）的开始结束时间也查出来，保存下来
            List<FactoryBo> factoryBos = new ArrayList<>();
            List<CarBo> carBoList = new ArrayList<>();
            WorkBo workBo = new WorkBo();

            //工单号
            workBo.setWorkNumber(no);
            HashSet<String> ips = new HashSet<>();
            if (list.size() > 0) {
                final String[] carNo = {""};
                StringBuilder stringBuilder = new StringBuilder();
                list.forEach(inOutLog -> {
                    //先保存IP
                    ips.add(inOutLog.getIp());
                    //车牌号
                    if (inOutLog.getIdno().length() == 7 && !carNo[0].equals(inOutLog.getIdno())) {
                        String idno = inOutLog.getIdno();
                        carNo[0] = idno;
                        stringBuilder.append(inOutLog.getIdno()).append(",");
                    }
                });
                //IP会有多个
                if (ips.size() > 0) {
                    //多个IP全部保存
                    String join = String.join(",", ips);
                    workBo.setIP(join);
                }

                workBo.setCarId(stringBuilder.toString());
                workBo.setWorkNumber(no);
                competeWorkBo(workBo, list.get(0));
            }

            list.forEach(inOutLog -> {
                //人员信息
                FactoryBo factoryBo = instanceFactory(inOutLog);
                factoryBo.setWorkNumber(no);
                factoryBos.add(factoryBo);
                //车辆信息
                if (inOutLog.getEgno() == null || "".equals(inOutLog.getEgno())) {
                    CarBo carBo = new CarBo();
                    carBo.setLcensePlate(inOutLog.getIdno());
                    carBo.setName(inOutLog.getNm());
                    carBo.setFactoryName(inOutLog.getTkvnd());
                    carBo.setIpLtLic(inOutLog.getIpltlic());
                    carBo.setFactoryNo(inOutLog.getPz());
                    carBo.setDatetime(inOutLog.getDatetime());
                    carBoList.add(carBo);
                }
            });
            workBo.setFactoryBoList(factoryBos);
            workBo.setCarBoList(carBoList);
            workBos.add(workBo);
        });

        return workBos;
    }


    public List<InOutLogPvc> getInOutLogOrderByAid() {

        Date today1 = DateUtils.parseDate(DateUtils.getDate());
        Date today = DateUtils.addDays(today1, 0);
        Date nextDay = DateUtils.addDays(today1, 1);
        List<TimInOutLog> allLogs = timInOutLogMapper.getInOutLogOrderByAid(today, nextDay);
        Date dt = new Date();
        List<InOutLogPvc> timeInOutLogPvcList = new ArrayList<>();
        for (TimInOutLog inOutLog : allLogs) {
            InOutLogPvc inOutLogPvc = new InOutLogPvc();
            inOutLogPvc.setWorkTime(inOutLog.getDatetime());
            inOutLogPvc.setAreaNo(inOutLog.getAreano());
            inOutLogPvc.setIp(inOutLog.getIp());
            inOutLogPvc.setVhNo(inOutLog.getVhno());
            inOutLogPvc.setEgNo(inOutLog.getEgno());
            inOutLogPvc.setEgName(inOutLog.getEgnm());
            inOutLogPvc.setOprEnvt(inOutLog.getOprenvt21());
            inOutLogPvc.setIdno(inOutLog.getIdno());
            inOutLogPvc.setName(inOutLog.getNm());
            inOutLogPvc.setIpltLic(inOutLog.getIpltlic());
            inOutLogPvc.setPz(inOutLog.getPz());
            inOutLogPvc.setTkVnd(inOutLog.getTkvnd());
            inOutLogPvc.setBeginTime(inOutLog.getBegtime());
            inOutLogPvc.setEndTime(inOutLog.getEndtime());
            inOutLogPvc.setInTime(inOutLog.getIntime());
            inOutLogPvc.setOutTime(inOutLog.getOuttime());
            inOutLogPvc.setInspector(inOutLog.getInspector());
            inOutLogPvc.setInspector2(inOutLog.getInspector2());
            inOutLogPvc.setMark(inOutLog.getMk());
            inOutLogPvc.setProfsId(inOutLog.getProfsid());
            inOutLogPvc.setOpltTime(inOutLog.getDoutdattm());
            inOutLogPvc.setFctDoorNm(inOutLog.getFctdornm());
            inOutLogPvc.setFvId(inOutLog.getFvid());
            inOutLogPvc.setAid(inOutLog.getAid());
            inOutLogPvc.setIsProcess(0);
            inOutLogPvc.setInsertTime(dt);
            inOutLogPvc.setDelFlag("0");
            inOutLogPvc.setCreateBy("system");
            inOutLogPvc.setCreateTime(dt);
            inOutLogPvc.setUpdateBy("system");
            inOutLogPvc.setUpdateTime(dt);
            inOutLogPvc.setRemark("");
            timeInOutLogPvcList.add(inOutLogPvc);
        }
        return timeInOutLogPvcList;
    }

    private void competeWorkBo(WorkBo workBo, TimInOutLog inOutLog) {
        workBo.setAreaNo(inOutLog.getAreano());
        workBo.setEgno(inOutLog.getEgno());
        workBo.setEgnm(inOutLog.getEgnm());
        workBo.setOprEnvt21(inOutLog.getOprenvt21());
        workBo.setInspector(inOutLog.getInspector());
        workBo.setInspector2(inOutLog.getInspector2());
        workBo.setDatetime(inOutLog.getDatetime());
        workBo.setBegtime(inOutLog.getBegtime());
        workBo.setEndtime(inOutLog.getEndtime());
        workBo.setIntime(inOutLog.getIntime());
        workBo.setOuttime(inOutLog.getOuttime());
        workBo.setFctdornm(inOutLog.getFctdornm());
    }

    private FactoryBo instanceFactory(TimInOutLog inOutLog) {
        FactoryBo factoryBo = new FactoryBo();
        factoryBo.setIdCard(inOutLog.getIdno());
        factoryBo.setName(inOutLog.getNm());
        factoryBo.setIpltlic(inOutLog.getIpltlic());
        factoryBo.setFactoryNo(inOutLog.getPz());
        factoryBo.setFactoryName(inOutLog.getTkvnd());
        factoryBo.setThisNumber(inOutLog.getEgno());
        factoryBo.setDatetime(inOutLog.getDatetime());
        factoryBo.setProfsid(inOutLog.getProfsid());
        factoryBo.setAid(inOutLog.getAid());
        return factoryBo;
    }


}

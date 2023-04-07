package com.ruoyi.web.controller.timer;

import com.github.pagehelper.PageHelper;
import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.service.IHcWorkOrderService;
import com.ruoyi.timer.domain.V0NBRKX5;
import com.ruoyi.timer.service.IV0NBRKX5Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class TaskSyncErp {

    private static final Logger log = LoggerFactory.getLogger(TaskSyncErp.class);

    @Autowired
    IV0NBRKX5Service v0NBRKX5Service;
    @Autowired
    IHcWorkOrderService hcWorkOrderService;

    //每3分鐘跑一次
    //@Scheduled(cron = "0 0/3 * * * ?")
    public void syncHcFromErp(){
        log.info("------------從v0NBRKX5同步危化工單(出廠日期為*的只新增)-開始-------------");
        List<V0NBRKX5Bo> boList = new ArrayList<>();
        int pageNum = 1;
        int pageSize = 1000;
        String orderBy = "";
        do {
            V0NBRKX5 v0NBRKX5=new V0NBRKX5();
            Map<String, Object> params = v0NBRKX5.getParams();
            params.put("isAdd", "Y");
            v0NBRKX5.setParams(params);

            PageHelper.startPage(pageNum++, pageSize, orderBy).setReasonable(true);
            boList = v0NBRKX5Service.selectV0NBRKX5List(v0NBRKX5);
            hcWorkOrderService.syncHcErp(boList,"Y");
        } while (boList.size() >= pageSize);
        log.info("------------從v0NBRKX5同步危化工單(出廠日期為*只新增)-結束-------------");


        log.info("------------從v0NBRKX5同步危化工單(出廠日期不為*的只修改)-開始-------------");
        boList = new ArrayList<>();
        pageNum = 1;
        pageSize = 1000;
        orderBy = "";
        do {
            V0NBRKX5 v0NBRKX5=new V0NBRKX5();
            Map<String, Object> params = v0NBRKX5.getParams();
            params.put("isAdd", "N");
            v0NBRKX5.setParams(params);

            PageHelper.startPage(pageNum++, pageSize, orderBy).setReasonable(true);
            boList = v0NBRKX5Service.selectV0NBRKX5List(v0NBRKX5);
            hcWorkOrderService.syncHcErp(boList,"N");
        } while (boList.size() >= pageSize);
        log.info("------------從v0NBRKX5同步危化工單(出廠日期不為*只修改)-結束-------------");
    }

    //每天凌晨1點10分執行
    @Scheduled(cron = "0 10 1 * * ?")
    public void hcToHr(){
        log.info("------------危化工單轉入歷史表-開始-------------");
        hcWorkOrderService.toHr();
        log.info("------------危化工單轉入歷史表-結束-------------");
    }

}

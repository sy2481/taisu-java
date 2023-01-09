package com.ruoyi.web.controller.timer;


import com.github.pagehelper.PageHelper;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class TaskSyncCent {

    @Autowired
    ISysUserService sysUserService;
    @Autowired
    IManFactoryService manFactoryService;

    //@Scheduled(cron = "0 1 0 * * ?")
    public int syncEmpFromCent() {
        return sysUserService.syncCent();
    }

    //@Scheduled(cron = "0 1 0 * * ?")
    public int syncVndFromCent() {
        return manFactoryService.syncCent();
    }

}

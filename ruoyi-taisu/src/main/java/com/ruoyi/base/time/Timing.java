package com.ruoyi.base.time;

import com.ruoyi.base.mapper.CarCardMapper;
import com.ruoyi.base.service.ILocateCardService;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.base.service.impl.ManWorkServiceImpl;
import com.ruoyi.base.utils.ZhenQuUtils;
import com.ruoyi.base.vo.AccessTokenVO;
import com.ruoyi.base.vo.MacSuccessVo;
import com.ruoyi.system.domain.SysJobLog;
import com.ruoyi.system.mapper.SysJobLogMapper;
import com.ruoyi.system.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * @author 兴跃
 */
@Configuration
@EnableScheduling
@Slf4j
public class Timing {

    @Autowired
    private IManFactoryService manFactoryService;
    @Autowired
    private CarCardMapper carCardMapper;
    @Autowired
    private ManWorkServiceImpl workService;

    @Autowired
    private ZhenQuUtils zhenQuUtils;

    @Autowired
    private ILocateCardService locateCardService;
    @Autowired
    private SysJobLogMapper sysJobLogMapper;
    /**
     * 每天00：00执行
     * 定时去清理工单，厂商的车卡和车牌号
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void timingRefresh() {
        try {
            log.info(new Date() + "定时开始");
            manFactoryService.updateCar();
            carCardMapper.dailyClear();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("异常：", e);
        }
    }

    /**
     * 每天凌晨执行；把手工单的日期重置为当日。
     * 顺便要删掉中间表的关联关系
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetLongTimeWork() {
        workService.resetLongTimeWork();
    }

    /**
     * 定时删除厂商的身份证号为空的
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void delFactory() {
        manFactoryService.delFactory();
    }

    /**
     * 定时获取定位卡的电量
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void getBattery(){
        locateCardService.updateBySn();
    }


    /**
     * 定时连接数据库
     */


    @Scheduled(cron = "*/30 * * * * ?")
    public void connectDatabase(){
        SysJobLog sysJobLog=new SysJobLog();
        sysJobLogMapper.selectSysJobLogList(sysJobLog);
        System.out.println("鏈接成功");
    }

}

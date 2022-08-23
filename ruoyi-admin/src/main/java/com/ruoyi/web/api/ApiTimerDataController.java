package com.ruoyi.web.api;

import com.ruoyi.base.bo.FactoryWorkBO;
import com.ruoyi.base.bo.PvcWorkBo;
import com.ruoyi.base.bo.WorkBo;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.service.impl.PvcWorkDataService;
import com.ruoyi.base.service.impl.WorkDataService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ThreadPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shiva   2022/3/6 11:56
 */
@RestController
@RequestMapping("/api/timer")
public class ApiTimerDataController {
    @Autowired
    private ApiService apiService;
    @Autowired
    private PlateSendService plateSendService;
    @Autowired
    private IManFactoryService factoryService;
    @Autowired
    private ThreadPoolConfig pool;
    @Autowired
    private PvcWorkDataService pvcWorkDataService;
    @Autowired
    private WorkDataService workDataService;

    //TODO 下面两个接口，车牌号是不是要转简体

    // 開放接口，提供給定時任務調用
    @ResponseBody
    @RequestMapping("/InOutLogData")
    public String InOutLogData(@RequestBody List<WorkBo> sourceData) {
        for (WorkBo workBo : sourceData) {
            //拿到每一个bo对象
            if (workBo == null || StringUtils.isBlank(workBo.getIP()) || StringUtils.isBlank(workBo.getDatetime())) {
                continue;
            }
            workDataService.saveWorkBo(workBo);
            //信息下发
            infoDown(workBo);


        }
        return "0";
    }

    @ResponseBody
    @RequestMapping("/processInOutLogPvcData")
    public String processInOutLogPvcData() {
        List<PvcWorkBo> sourceData= pvcWorkDataService.processInOutLogPvcData();
        for (PvcWorkBo workBo : sourceData) {
            //拿到每一个bo对象
            if (workBo == null || StringUtils.isBlank(workBo.getIp()) || StringUtils.isBlank(workBo.getDatetime())) {
                continue;
            }
            pvcWorkDataService.savePvcWorkBo(workBo);
            //信息下发
            //infoDown(workBo);


        }

        return "";
//        for(InOutLogPvc inOutLogPvc: sourceData) {
//            int count=iInOutLogPvcService.selectInOutLogPvcByAid(inOutLogPvc.getAid());
//            if(count>0){
//                break;
//            }else{
//                iInOutLogPvcService.insertInOutLogPvc(inOutLogPvc);
//            }
//        }
//        return "success";
    }


    /**
     * 危化品的工单保存
     * 危化品不需要实时更新，它的数据一般都是一次性的。
     * 但是危化品的有效时间也可能是多天的。
     * 所以传过来的工单号，都要设置为当天有效。
     */
    @ResponseBody
    @RequestMapping("/dangerWorkData")
    public String dangerWorkData(@RequestBody List<WorkBo> sourceData) {
        for (WorkBo workBo : sourceData) {
            if (workBo == null || StringUtils.isBlank(workBo.getIP()) || StringUtils.isBlank(workBo.getDatetime())) {
                continue;
            }

            workDataService.saveDangerWork(workBo);
            //TODO 危险品的人员信息要自动下发
            infoDown(workBo);
        }
        return "0";
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


}

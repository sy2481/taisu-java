package com.ruoyi.web.api;

import com.ruoyi.base.domain.ZqSubscribe;
import com.ruoyi.base.service.IZqSubscribeService;
import com.ruoyi.web.api.basic.Response;
import com.ruoyi.web.api.bo.SubscribeInfoBo;
import com.ruoyi.web.api.bo.subscribeInfoDataBo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 兴跃
 */
@Api(tags = "订阅信息")
@RestController
@RequestMapping(value = "/api/warn")
public class ApiWarnInfoController {

    @Autowired
    private IZqSubscribeService zqSubscribeService;

    @ResponseBody
    @PostMapping(value = "addWarnInfo")
    public Response addWarnInfo(@RequestBody SubscribeInfoBo subscribeInfoBo,@RequestParam String buildId) {

        ZqSubscribe zqSubscribe = zqSubscribe(subscribeInfoBo);
        int i = zqSubscribeService.addWarnInfo(zqSubscribe,buildId);
        if (i>0){
            return Response.success("报警信息保存成功");
        }else if (i==0){
            return Response.error("无法匹配到建筑！");
        }
       return Response.error("出现异常！");
    }


    /**
     * 补全数据
     */
    private ZqSubscribe zqSubscribe(SubscribeInfoBo subscribeInfoBo) {
        subscribeInfoDataBo data = subscribeInfoBo.getData();

        ZqSubscribe zqSubscribe = new ZqSubscribe();
        System.out.println(new Date(subscribeInfoBo.getTime()));
        zqSubscribe.setSendTime(new Date(subscribeInfoBo.getTime()));
        zqSubscribe.setDataType(subscribeInfoBo.getType());

        zqSubscribe.setSocialCreditCode(data.getSocialCreditCode());
        zqSubscribe.setWarnId(data.getId());
        zqSubscribe.setWarnTime(new Date(data.getTime()));
        zqSubscribe.setWarnType(data.getType());
        zqSubscribe.setAlarmInfo(data.getAlarmInfo());
        zqSubscribe.setCede(data.getCode());
        zqSubscribe.setAlarmType(data.getAlarmType());
        zqSubscribe.setAlarmLocation(data.getAlarmLocation());
        return zqSubscribe;

    }


}

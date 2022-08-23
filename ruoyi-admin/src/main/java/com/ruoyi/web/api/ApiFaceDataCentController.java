package com.ruoyi.web.api;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.ManFactory;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.api.basic.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author shiva   2022/3/5 14:34
 */
@Api(tags = "FaceDataCent")
@RestController
@RequestMapping("/api/wechat/faceDataCent")
public class ApiFaceDataCentController {

    @Autowired
    private IManFactoryService manFactoryService;

    @Autowired
    private ISysUserService sysUserService;

    @ResponseBody
    @PostMapping("/getListByIdCardsForSupplier")
    public Response getListByIdCardsForSupplier(@RequestBody JSONObject param) {
        try {
            List<String> noFaceIdCardList = Arrays.asList(param.getString("noFaceIdCards").split(","));
            Map<String, ManFactory> noFaceManFactoryMap = manFactoryService.getListByIdCards(noFaceIdCardList);

            return Response.builder().code(0).data(noFaceManFactoryMap).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @PostMapping(value = "/saveFaceForSupplier")
    public Response saveFaceForSupplier(@RequestBody ManFactory manFactory) {
        try {
            manFactoryService.saveFaceForCenter(manFactory);
            return Response.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @PostMapping("/getListByIdCardsForEmployee")
    public Response getListByIdCardsForEmployee(@RequestBody JSONObject param) {
        try {
            List<String> noFaceIdCardList = Arrays.asList(param.getString("noFaceIdCards").split(","));
            Map<String, SysUser> noFaceSysUserMap = sysUserService.getListByIdCards(noFaceIdCardList);

            return Response.builder().code(0).data(noFaceSysUserMap).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @PostMapping(value = "/saveFaceForEmployee")
    public Response saveFaceForEmployee(@RequestBody SysUser sysUser) {
        try {
            sysUserService.saveFaceForCenter(sysUser);
            return Response.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }


}

package com.ruoyi.web.api;

import com.ruoyi.base.domain.*;
import com.ruoyi.base.service.IHikEquipmentService;
import com.ruoyi.base.service.IInOutLogPerilousService;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.base.utils.PlcRedisUtils;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.web.api.basic.Response;
import com.ruoyi.web.api.bo.GuestBo;
import com.ruoyi.web.api.bo.PerilousBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 兴跃
 */
@Api(tags = "危化门禁")
@RestController
@RequestMapping(value = "/api/perilous")
@Slf4j
public class ApiPerilousController {

    @Autowired
    private IManFactoryService manFactoryService;
    @Autowired
    private IInOutLogPerilousService logPerilousService;
    @Autowired
    private IHikEquipmentService hikEquipmentService;
    @Autowired
    private PlcRedisUtils plcRedisUtils;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IPlcEquipmentService plcEquipmentService;

    /**
     * 车牌
     */
    private static final String plateNo = "plateNo";
    /**
     * 人脸
     */
    private static final String face = "face";

    /**
     * 1：進廠
     * 2：出來
     *
     * @param perilousBo
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "危化门禁验证")
    @PostMapping(value = "checkingPerilous")
    public Response checkingPerilous(@RequestBody PerilousBo perilousBo) {

        if (StringUtils.isBlank(perilousBo.getCheckingType())) {
            return Response.error("PLC门禁方式为空！");
        }
        if (StringUtils.isBlank(perilousBo.getCarNo())) {
            return Response.error("危化品门车牌号不能为空！");
        }

        Integer integer = inOutSwitch(perilousBo);
        if (integer != -1) {
            if (integer == 1) {
                return Response.error("已经进入，不可重复进入");
            } else if (integer == 2) {
                return Response.error("没有进的记录，出去需要去补录");
            } else if (integer == 3) {
                return Response.error("最近一条记录为出去记录，无法再次出厂");
            }
        }
        if (perilousBo.getInOutType() == 2) {
            addLog(perilousBo);
            return Response.success("危化品出厂成功");
        }

        String[] split = perilousBo.getCheckingType().split(",");
        //判断门禁方式
        //门禁方式为车牌,当验证为只车牌时，判断门禁方式
        if (split.length == 1 && !(plateNo.equals(split[0]))) {
            return Response.error("门禁方式错误");
        }

        if (split.length == 1) {
            List<ManFactory> manFactory = getManFactory(null, perilousBo.getCarNo());
            if (manFactory != null && manFactory.size() > 0) {
                addLog(perilousBo);
                return Response.success("车牌验证通过");
            }
        }

        //门禁方式为车牌+人脸
        List<ManFactory> manFactory = getManFactory(perilousBo.getIdCard(), perilousBo.getCarNo());
        if (manFactory != null && manFactory.size() > 0) {
            addLog(perilousBo);
            return Response.success("车牌+人脸验证通过");
        }

        return Response.error("门禁验证失败");
    }


    //根据身份证号，车牌验证

    private List<ManFactory> getManFactory(String idCard, String carNO) {
        List<ManFactory> byCommonParams = manFactoryService.selectCangerousCar(idCard, carNO);
        return byCommonParams;
    }


    //保存进出记录

    public void addLog(PerilousBo perilousBo) {
        InOutLogPerilous log = new InOutLogPerilous();
        log.setIp(perilousBo.getIp());
        log.setIdCard(perilousBo.getIdCard() == null ? "" : perilousBo.getIdCard());
        log.setCarNo(perilousBo.getCarNo());
        log.setLogType(perilousBo.getInOutType());
        if (StringUtils.isNotBlank(perilousBo.getCheckingType())) {
            String[] split = perilousBo.getCheckingType().split(",");
            if (split.length == 1) {
                log.setCheckType("車牌");
            } else {
                log.setCheckType("車牌+人臉");
            }
        }
        //根据Ip查询海康设备
        List<HikEquipment> equipments = hikEquipmentService.findByIp(perilousBo.getIp());
        if (equipments != null && equipments.size() > 0) {
            HikEquipment hikEquipment = equipments.get(0);
            log.setDeviceName(hikEquipment.getName());
            PlcEquipment plcEquipment = plcRedisUtils.getPlcEquipment(hikEquipment.getFrontIp());
            log.setDeptId(plcEquipment.getPlantAreaId());
            SysDept sysDept = sysDeptService.selectDeptById(plcEquipment.getPlantAreaId());
            log.setAreaName(sysDept.getDeptName());
        } else {
            PlcEquipment plcEquipment = plcRedisUtils.getPlcEquipment(perilousBo.getIp());
            if (plcEquipment != null) {
                log.setDeviceName(plcEquipment.getName());
                log.setDeptId(plcEquipment.getPlantAreaId());
                SysDept sysDept = sysDeptService.selectDeptById(plcEquipment.getPlantAreaId());
                log.setAreaName(sysDept.getDeptName());
            }

        }
        logPerilousService.insertInOutLogPerilous(log);
    }


    /**
     * 一进一出
     */
    private Integer inOutSwitch(PerilousBo perilousBo) {
        PlcEquipment equipment=null;
        //根据来宾卡绑定的身份证查看进出记录
        equipment = plcEquipmentService.findByIp(perilousBo.getIp());
        if (StringUtils.isNull(equipment)){
            List<HikEquipment> hikEquipmentList = hikEquipmentService.findByIp(perilousBo.getIp());
            if (hikEquipmentList.size()>0){
                equipment = plcEquipmentService.findByIp(hikEquipmentList.get(0).getFrontIp());
            }

        }

        if (StringUtils.isNotNull(equipment)) {


            SysDept sysDept = sysDeptService.selectDeptById(equipment.getPlantAreaId());
            //判断该厂区是否开启一进一出功能
            if (sysDept.getPassSwitch() != null) {
                //开启一进一出
                if (sysDept.getPassSwitch() == 0) {
                    //进
                    if (perilousBo.getInOutType() == 1) {
                        //进出日志
                        InOutLogPerilous inOutLog = logPerilousService.getInOutLogGuestByIdCard(perilousBo.getIdCard(), sysDept.getTime(),sysDept.getDeptId());

                        //没有进出记录，可以进
                        if (StringUtils.isNull(inOutLog)) {
                            log.info("没有进出记录，可以进入");
                            return 0;
                        } else {
                            //进
                            if (inOutLog.getLogType() == 1) {
                                log.info("已经进入，不可重复进入");
                                return 1;
                            } else {
                                log.info("已经出来，可以再次进入");
                                return 0;
                            }
                        }


                    } else {
                        //进出日志
                        InOutLogPerilous inOutLog = logPerilousService.getInOutLogGuestByIdCard(perilousBo.getIdCard(), sysDept.getTime(),sysDept.getDeptId());

                        if (StringUtils.isNull(inOutLog)) {
                            log.info("没有进的记录，出去需要去补录");
                            return 2;
                        } else {
                            //进
                            if (inOutLog.getLogType() == 1) {
                                log.info("存在进入记录，可以出来");
                                return 0;

                            } else {
                                log.info("最近一条记录为出去记录，无法再次出厂");
                                return 3;
                            }
                        }
                    }

                }
            }
        }
        return -1;
    }


}

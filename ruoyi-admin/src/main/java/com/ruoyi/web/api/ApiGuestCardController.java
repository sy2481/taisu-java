package com.ruoyi.web.api;

import com.ruoyi.base.domain.GuestCard;
import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.InOutLogGuest;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.mapper.EquipmentMapper;
import com.ruoyi.base.service.IGuestCardService;
import com.ruoyi.base.service.IHikEquipmentService;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.base.service.impl.InOutLogGuestServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.web.api.basic.Response;
import com.ruoyi.web.api.bo.GuestBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 兴跃
 */
@Api(tags = "来宾卡")
@RestController
@RequestMapping(value = "/api/guestCard")
@Slf4j
public class ApiGuestCardController {
    @Autowired
    private IGuestCardService guestCardService;
    @Autowired
    private InOutLogGuestServiceImpl inOutLogGuestService;
    @Autowired
    private IPlcEquipmentService plcEquipmentService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private IHikEquipmentService hikEquipmentService;


    @ApiOperation(value = "来宾卡验证")
    @PostMapping(value = "/guestCardCheck")
    public Response guestCardCheck(@RequestBody GuestBo guestBo) {
        //查询门禁卡是否存在
        List<GuestCard> guestCards = guestCardService.listByCardNo(guestBo.getGuestCard());
        if (guestCards.size() == 0) {
            return Response.error("来宾卡信息不存在");
        }
        List<GuestCard> cardNo = guestCardService.getCardNo(guestBo.getGuestCard());
        if (cardNo.size() == 0) {
            return Response.error("来宾卡已过期");
        }

        Integer integer = inOutSwitch(guestBo, cardNo.get(0));
        if (integer != -1) {
            if (integer == 1) {
                return Response.error("已经进入，不可重复进入");
            } else if (integer == 2) {
                return Response.error("没有进的记录，出去需要去补录");
            } else if (integer == 3) {
                return Response.error("最近一条记录为出去记录，无法再次出厂");
            }
        }

        //权限
        List<Long> longList = new ArrayList<>();
        List<String> indexNos = new ArrayList<>();
        String[] split = guestCards.get(0).getPass().split(",");
        for (String plc : split) {
            longList.add(new Long(plc));
        }
        List<PlcEquipment> plcEquipmentById = plcEquipmentService.getPlcEquipmentById(longList);
        for (PlcEquipment plcEquipment : plcEquipmentById) {
            indexNos.addAll(equipmentMapper.listPersonEquipmentCode(plcEquipment.getIp()));
        }
        //校验通过，保存日志
        inOutLogGuestService.insertInOutLogGuest(setInOutLogGuest(guestBo));
        return Response.success("来宾卡验证通过", indexNos);
    }


    /**
     * 一进一出
     * -1:没有开启
     * 0:可以进
     * 1：不可以进
     * 2：不能出
     * 3：无法再次出
     */

    private Integer inOutSwitch(GuestBo guestBo, GuestCard guestCard) {
        PlcEquipment equipment=null;
        //根据来宾卡绑定的身份证查看进出记录
        equipment = plcEquipmentService.findByIp(guestBo.getIp());
        if (StringUtils.isNull(equipment)){
            List<HikEquipment> hikEquipmentList = hikEquipmentService.findByIp(guestBo.getIp());
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
                    if (guestBo.getInOutType() == 0) {
                        //进出日志
                        InOutLogGuest inOutLog = inOutLogGuestService.getInOutLogGuestByIdCard(guestCard.getIdCard(), sysDept.getTime(),sysDept.getDeptId());

                        //没有进出记录，可以进
                        if (StringUtils.isNull(inOutLog)) {
                            log.info("没有进出记录，可以进入");
                            return 0;
                        } else {
                            //进
                            if (inOutLog.getLogType() == 0) {
                                log.info("已经进入，不可重复进入");
                                return 1;
                            } else {
                                log.info("已经出来，可以再次进入");
                                return 0;
                            }
                        }


                    } else {
                        //进出日志
                        InOutLogGuest inoutLog = inOutLogGuestService.getInOutLogGuestByIdCard(guestCard.getIdCard(), sysDept.getTime(),sysDept.getDeptId());

                        if (StringUtils.isNull(inoutLog)) {
                            log.info("没有进的记录，出去需要去补录");
                            return 2;
                        } else {
                            //进
                            if (inoutLog.getLogType() == 0) {
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

    /**
     * 数据方法保存进出日志
     *
     * @param guestBo
     * @return
     */
    private InOutLogGuest setInOutLogGuest(GuestBo guestBo) {
        InOutLogGuest inOutLogGuest = new InOutLogGuest();
        inOutLogGuest.setIp(guestBo.getIp());
        /**
         * 根据IP获取厂区信息
         */
        PlcEquipment plcEquipment = plcEquipmentService.findByIp(guestBo.getIp());
        if (plcEquipment != null) {
            if (StringUtils.isNotBlank(plcEquipment.getName())) {
                inOutLogGuest.setPass(plcEquipment.getName());
            }
            SysDept sysDept = sysDeptService.selectDeptById(plcEquipment.getPlantAreaId());
            if (sysDept != null) {
                inOutLogGuest.setAreaName(sysDept.getDeptName() == null ? "" : sysDept.getDeptName());
            }
            inOutLogGuest.setDeptId(plcEquipment.getPlantAreaId());
        }

        /**
         * 根据来宾卡号查询
         */
        List<GuestCard> guestCards = guestCardService.listByCardNo(guestBo.getGuestCard());
        if (StringUtils.isNotNull(guestCards)) {
            GuestCard guestCard = guestCards.get(0);
            if (StringUtils.isNotBlank(guestCard.getFactoryName())) {
                inOutLogGuest.setFactoryName(guestCard.getFactoryName());
            }
            if (StringUtils.isNotBlank(guestCard.getUserName())) {
                inOutLogGuest.setUserName(guestCard.getUserName());
            }
            if (StringUtils.isNotBlank(guestCard.getIdCard())) {
                inOutLogGuest.setIdCard(guestCard.getIdCard());
            }
        }
        inOutLogGuest.setGuestCard(guestBo.getGuestCard());
        inOutLogGuest.setLogType(guestBo.getInOutType());
        inOutLogGuest.setValidType(0);
        return inOutLogGuest;
    }
}

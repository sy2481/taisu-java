package com.ruoyi.web.api;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.mapper.ManBlackInfoMapper;
import com.ruoyi.base.mapper.ManFactoryMapper;
import com.ruoyi.base.mapper.ManWorkMapper;
import com.ruoyi.base.service.*;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.web.api.basic.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 进出
 *
 * @author shiva   2022/3/7 14:18
 */
@Slf4j
@RestController
@RequestMapping("/api/inOut")
public class ApiInOutCheckController {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ManFactoryMapper manFactoryMapper;
    @Autowired
    private ManWorkMapper workMapper;

    @Autowired
    private IPlcEquipmentService plcEquipmentService;

    @Autowired
    private IHikEquipmentService hikEquipmentService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private IInOutLogService inOutLogService;

    @Autowired
    private IManBlackInfoService manBlackInfoService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private SafetycarService safetycarService;

    //出
    private static final String OUT = "OUT";
    //进
    private static final String ENTER = "ENTER";

    /**
     * 进出允许判断
     *
     * @param idCard    人员身份证
     * @param inOutType ENTER：进 OUT出
     */
    @ResponseBody
    @GetMapping("/verification")
    public Response verification(String idCard, String inOutType, String ip) {
        //是内部员工,直接放行
        List<SysUser> userList = userMapper.getByCommonParams(idCard, null, null, null);
        if (userList.size() > 0) {

            Integer integer = inOutUser(inOutType, idCard, ip, "0");
            if (integer != -1) {
                if (integer == 1) {
                    return Response.error("上次未出廠");
                    //return Response.error("已经进入，不可重复进入");
                } else if (integer == 2) {
                    return Response.error("上次未入廠");
                    //return Response.error("没有进的记录，出去需要去补录");
                } else if (integer == 3) {
                    return Response.error("上次未入廠");
                    //return Response.error("最近一条记录为出去记录，无法再次出厂");
                }
            }
            return Response.success("内部員工,认证成功，请通行");
            //return Response.success("内部員工,放行");
        }


        List<ManFactory> factoryList = manFactoryMapper.getByCommonParams(idCard, null, null, null);
        if (factoryList.size() == 0) {
            return Response.error("查無此人");
        }

        //黑名单禁入
        ManBlackInfo manBlackInfo = manBlackInfoService.getBlackInfoByCard(idCard);
        if (manBlackInfo != null) {
            return Response.error("黑名单禁入");
        }


        Integer integer = inOutUser(inOutType, idCard, ip, "1");

        if (integer != -1) {
            if (integer == 1) {
                return Response.error("上次未出廠");
                //return Response.error("已经进入，不可重复进入");
            } else if (integer == 2) {
                return Response.error("上次未入廠");
                //return Response.error("没有进的记录，出去需要去补录");
            } else if (integer == 3) {
                return Response.error("上次未入廠");
                //return Response.error("最近一条记录为出去记录，无法再次出厂");
            }
        }
        //是厂商人员，manFactory：厂商人員信息
        ManFactory manFactory = factoryList.get(0);
        //workNo：厂商人员所属工单编号
        String workNo = manFactory.getWorkNo();
        //工单信息
        ManWork manWork = workMapper.selectManWorkByworkNo(workNo);


        //TODO 此功能需要
        //入场判断工单时间 是否能进场 出场不需要管制
        Long loadType = 0L;
        List<HikEquipment> hikEquipmentList = hikEquipmentService.findByIp(ip);
        if (hikEquipmentList.size() > 0) {
            loadType = hikEquipmentList.get(0).getSign();
        }
        if (ENTER.equals(inOutType)) {
            //判断工单是否结束
            Date now = DateUtils.getNowDate();
            //if ((now.after(manWork.getStartTime()) && now.before(manWork.getEndTime())) || (now.after(manWork.getExtendStartTime()) && now.before(manWork.getExtendEndTime()))) {
            log.error("manWork.getStartTime()-->" + manWork.getStartTime() + "manWork.getEndTime()" + manWork.getEndTime());
            if (now.after(manWork.getStartTime()) && now.before(manWork.getEndTime())) {
                log.info("正常工单时间可以进入");
                //return Response.success("施工時間可以进入");
            } else {
                if (manWork.getExtendStartTime() != null && manWork.getExtendEndTime() != null) {
                    if (now.after(manWork.getExtendStartTime()) && now.before(manWork.getExtendEndTime())) {
                        log.info("延長工单时间可以进入");
                        //return Response.success("施工時間可以进入");
                    } else {
                        log.info("工单时间不可以进入");
                        return Response.error("非施工時間");
                    }
                } else {
                    log.info("工单时间不可以进入");
                    return Response.error("非施工時間");
                }
            }
        }
        //午休管制
        if (ENTER.equals(inOutType)) {
            String noonControl = sysConfigService.selectConfigByKey("sys.noon.control");
            String noonBeginTime = sysConfigService.selectConfigByKey("sys.noon.beginTime");
            String noonEndTime = sysConfigService.selectConfigByKey("sys.noon.endTime");
            if (!StringUtils.isEmpty(noonControl) && "0".equals(noonControl)) {
                String nowTime = DateUtils.parseDateToStr("HH:mm:ss", DateUtils.getNowDate());
                if (!StringUtils.isEmpty(noonBeginTime) && !StringUtils.isEmpty(noonEndTime)) {
                    if (nowTime.compareTo(noonBeginTime) > 0 && nowTime.compareTo(noonEndTime) < 0) {
                        return Response.error("午休禁入厂");
                    }
                }
            }
        }


        //判断是否是临时单，不需要判断直接放行
        if (StringUtils.isNotBlank(manWork.getProjectNo()) && manWork.getProjectNo().startsWith("1")) {
            log.error("StringUtils.isNotBlank(manWork.getProjectNo()) && manWork.getProjectNo().startsWith(1)");
            return Response.success("认证成功，请通行");
            //return Response.success("放行");
        }


        //这里的两个判断是：门开了但是人没走，重复刷脸的问题
        //进来的，而且这个人不受直接放行
       /* if (ENTER.equals(inOutType) && 1 == manFactory.getEntered()) {
            return Response.success("放行");
        }
        //出去的，而且这个人已经出去了，直接放行
        if (OUT.equals(inOutType) && 0 == manFactory.getEntered()) {
            return Response.success("放行");
        }*/

        if ("XT".equals(manFactory.getLead())) {
            log.error("XT--->" + ("XT".equals(manFactory.getLead())));
            //是XT（厂商人员负责人）
            //进条件：直接进
            //出条件：如还有XT在内可以出如果没有则需要厂商普通人员出完才能出
            if (ENTER.equals(inOutType)) {
                log.error("ENTER");
                //XT进请求；判断人员是否已经在内
                if (1 != manFactory.getEntered()) {
                    //还不在里面，那就 +1，顺便设置为1表示已经进去了
                    workMapper.xtIn(workNo);
                    manFactory.setEntered(1);
                    manFactoryMapper.updateManFactory(manFactory);
                }
                return Response.success("认证成功，请通行");
                //return Response.success("放行");
            } else if (OUT.equals(inOutType)) {
                log.error("OUT");
                //XT出请求
                if (checkXtOut(manWork, workNo, manFactory)) {
                    return Response.success("认证成功，请通行");
                    //return Response.success("放行");
                } else {
                    return Response.error("工安應後出");
                }
            }
        } else {
            //普通厂商人员
            //进条件：有一个及以上的XT人员在内
            //出条件：直接出
            if (inOutType.equals(ENTER)) {
                //普通厂商人员进请求
                if (checkEmployeeIn(manWork, workNo, manFactory)) {
                    return Response.success("认证成功，请通行");
                    //return Response.success("放行");
                } else {
                    return Response.error("工安應先入");
                }
            } else if (inOutType.equals(OUT)) {
                //普通厂商人员出请求
                if (0 != manFactory.getEntered()) {
                    manFactory.setEntered(0);
                    workMapper.comOut(workNo);
                    manFactoryMapper.updateManFactory(manFactory);
                }
                return Response.success("认证成功，请通行");
                //return Response.success("放行");
            }
        }
        return Response.error("请求参数异常！");
    }

    //判断XT是否能出
    private Boolean checkXtOut(ManWork manWork, String workNo, ManFactory manFactory) {
        int xtInNum = manWork.getXtInNum();
        int comInNum = manWork.getComInNum();
        if (xtInNum > 1) {
            log.info("还有其他负责人在内,当前负责人可以出");
            if (0 != manFactory.getEntered()) {
                manFactory.setEntered(0);
                workMapper.xtOut(workNo);
                manFactoryMapper.updateManFactory(manFactory);
            }
            return true;
        } else {
            //除当前负责人都已经出完,则需要判断厂商普通人员是否出完
            if (comInNum == 0) {
                if (0 != manFactory.getEntered()) {
                    manFactory.setEntered(0);
                    workMapper.xtOut(workNo);
                    manFactoryMapper.updateManFactory(manFactory);
                }
                log.info("除当前负责人,其他负责人都已经出完但厂商普通人员已出完,当前负责人可以出");
                return true;
            } else {
                log.info("除当前负责人,其他负责人都已经出完且厂商普通人员未出完,当前负责人不能出");
                return false;
            }
        }
    }

    //判断厂商普通人员是否能进
    private Boolean checkEmployeeIn(ManWork manWork, String workNo, ManFactory manFactory) {
        int xtInNum = manWork.getXtInNum();
        if (xtInNum >= 1) {
            log.info("有负责人在内,厂商普通人员可以进入");
            if (1 != manFactory.getEntered()) {
                //还不在里面，那就 +1，顺便设置为1表示已经进去了
                workMapper.comIn(workNo);
                manFactory.setEntered(1);
                manFactoryMapper.updateManFactory(manFactory);
            }
            return true;
        } else {
            log.info("没有负责人在内暂时不能进");
            return false;
        }
    }


    /**
     * 一进一出
     * -1:没有开启
     * 0:可以进
     * 1：不可以进
     * 2：不能出
     * 3：无法再次出
     */
    private Integer inOutUser(String inOutType, String idCard, String ip, String personType) {

        PlcEquipment equipment = null;
        //根据来宾卡绑定的身份证查看进出记录
        equipment = plcEquipmentService.findByIp(ip);
        if (StringUtils.isNull(equipment)) {
            List<HikEquipment> hikEquipmentList = hikEquipmentService.findByIp(ip);
            if (hikEquipmentList.size() > 0) {
                equipment = plcEquipmentService.findByIp(hikEquipmentList.get(0).getFrontIp());
            }
        }

        if (StringUtils.isNotNull(equipment)) {
            SysDept sysDept = sysDeptService.selectDeptById(equipment.getPlantAreaId());

            if (sysDept.getPassSwitch() != null) {
                //开启一进一出
                if (sysDept.getPassSwitch() == 0) {
                    InOutLog inOutLog = inOutLogService.getInOutLogGuestByIdCard(idCard, sysDept.getTime(), personType, sysDept.getDeptId());
                    //进
                    if (inOutType.equals(ENTER)) {
                        if (StringUtils.isNull(inOutLog)) {
                            log.info("没有进出记录，可以进入");
                            return 0;
                        } else {
                            if (StringUtils.isNotBlank(inOutLog.getLogType())) {
                                String type = inOutLog.getLogType().substring(0, 1);
                                //进
                                if ("0".equals(type)) {
                                    log.info("已经进入，不可重复进入");
                                    return 1;
                                } else {
                                    log.info("已经出来，可以再次进入");
                                    return 0;
                                }
                            }
                        }

                    } else {

                        if (StringUtils.isNull(inOutLog)) {
                            log.info("没有进的记录，出去需要去补录");
                            return 2;
                        } else {
                            if (StringUtils.isNotBlank(inOutLog.getLogType())) {
                                String type = inOutLog.getLogType().substring(0, 1);

                                if ("0".equals(type)) {
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

        }

        return -1;
    }


    /**
     * TODO 来宾卡检查是否存在；自动保存进出记录
     */

    /**
     * 根據車牌號查詢危化品
     */
    @ResponseBody
    @GetMapping("/checkDangerPlate")
    public Response checkDangerPlate(String plateNo) {
        int i = workMapper.countDangerPlate(plateNo, new Date());
        if (i > 0) {
            return Response.success("放行");
        } else {
            return Response.error("未檢索到車牌");
        }

    }

    @ResponseBody
    @GetMapping("/getCarType")
    public Response getCarType(String plateNo) {
        BaseSafetycar baseSafetycar = new BaseSafetycar();
        baseSafetycar.setIdno(plateNo);
        List<BaseSafetycar> baseSafetycars = safetycarService.selectSafetyCarList(baseSafetycar);
        Long carType = 2L;
        if (baseSafetycars != null&& baseSafetycars.size()>0) {
             if(baseSafetycars.get(0).getCarType()!=null){
                 carType=baseSafetycars.get(0).getCarType();
             }
        }
//        HashMap<String, Object> resultObj = new HashMap<>();
//        resultObj.put("carType", carType);
        return Response.success(carType.toString());
    }

}



package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.IdCardBO;
import com.ruoyi.base.interact.CarCardSendService;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.interact.SyncCenterService;
import com.ruoyi.base.mapper.CarCardMapper;
import com.ruoyi.base.mapper.CardRecordMapper;
import com.ruoyi.base.service.CarCardBindService;
import com.ruoyi.base.service.IPersonBindService;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.utils.IDcard;
import com.ruoyi.base.vo.CarCardVO;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 10月份-------用户信息接口更新 :
 * 修改接口
 * 增加接口
 * 删除接口
 *
 * @author wang
 */
@RestController
@RequestMapping("/system/newsysuser")
public class SysUserNewController extends BaseController {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ApiService apiService;
    @Autowired
    private ThreadPoolConfig pool;
    @Autowired
    private PlateSendService plateSendService;
    @Autowired
    private IPersonBindService personBindService;
    @Autowired
    private CarCardBindService carCardBindService;
    private SyncCenterService syncCenterService;
    @Autowired
    private SysUserCenterSyncService userCenterSyncService;

    @Autowired
    private PersonSendService personSendService;

    @Value("${cent.host}")
    private String centHost;
    /**
     * 更新用户
     * 分厂更新图片后完 人员资料发送给中心做同步
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) throws Exception {
        SysUser oldUser = userService.selectUserById(user.getUserId());
        //判斷是否修改
        if (!oldUser.getEmpNo().equals(user.getEmpNo())) {
            //修改判斷編號是否重複
            SysUser userNo = userService.getByUserNo(user.getEmpNo());
            if (userNo != null) {
                return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，員工編號已存在");
            }
        }
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getUsername());

        StringBuilder factory = new StringBuilder();
        if (StringUtils.isNull(user.getFactoryIdArray())) {
            user.setFactoryId(null);
        } else {
            for (int i = 0; i < user.getFactoryIdArray().length; i++) {
                if (i == 0) {
                    factory.append(user.getFactoryIdArray()[i]);
                } else {
                    factory.append(",").append(user.getFactoryIdArray()[i]);
                }
            }
            user.setFactoryId(factory.toString());
        }
        //添加通道
        StringBuffer plc = new StringBuffer();
        if (StringUtils.isNull(user.getPlcInfo())) {
            user.setPlc(null);
        } else {
            for (int i = 0; i < user.getPlcInfo().length; i++) {
                if (i == 0) {
                    plc.append(user.getPlcInfo()[i]);
                } else {
                    plc.append(",").append(user.getPlcInfo()[i]);
                }
            }
            user.setPlc(plc.toString());
        }

        IDcard.competeUserByIdcard(user);
        if(user.getPlc()==null|| user.getPlc().length()==0){
            user.setDisplayStatus("2");
        }else {
            user.setDisplayStatus("0");
        }
        int userRow = userService.updateUser(user);
        if (userRow > 0) {
            SysUser sysUser = sysUserMapper.selectUserById(user.getUserId());
            pool.threadPoolTaskExecutor().execute(() -> apiService.userBindHlkSubSysUserUpdate(sysUser));
            //这里需要对比前后车牌号差异，有的需要新增，有的需要删除
            pool.threadPoolTaskExecutor().execute(() -> plateSendService.userPlateDiffSend(sysUserMapper.selectUserById(user.getUserId()), oldUser.getCarId()));

            //TODO 10-08 更新完照片数据同步到中心
            //pool.threadPoolTaskExecutor().execute(() -> syncCenterService.centerUserUpdate(sysUser));
            return toAjax(userRow);
        }
        return AjaxResult.error(user.getIdCard() + "身份证号已存在");
    }

    /**
     * 更新用户
     * 开放接口,中心会定时调用此接口,会把中心的员工数据同步或者更新到分厂
     */
    @PostMapping("/syncUser")
    public AjaxResult syncUser(@Validated @RequestBody SysUser centerUser) {
        System.out.println("中心 --> 分厂   ---------user = " + JSON.toJSONString(centerUser));
        SysUser factoryUser = userService.selectUserByIdCard(centerUser.getIdCard());
        System.out.println("factoryUser = " + JSON.toJSONString(factoryUser));
        //如果该厂区没有这个人,就同步添加到该厂区,并将这个人状态隐藏
        if (factoryUser == null) {
            int status = userCenterSyncService.addCenterUser(centerUser);
            if (status > 0) {
                System.out.println(centerUser.getIdCard() + "-----> 中心同步到分厂成功");
                return AjaxResult.success();
            } else {
                System.out.println(centerUser.getIdCard() + "-----> 中心同步到分厂失败");
                return AjaxResult.error();
            }
        }
        //如果这个人已经在这个厂区中,照片没有更新不做处理
        if (factoryUser.getFace().equals(centerUser.getFace())) {
            return AjaxResult.success();
        }
        userCenterSyncService.syncUpdateUser(centerUser);
        return AjaxResult.success();
    }

    /**
     * 新增內部員工用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/new")
    public AjaxResult addSysUser(@Validated @RequestBody SysUser user) {
        //修改判斷編號是否重複
        SysUser userNo = userService.getByUserNo(user.getEmpNo());
        if (userNo != null) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，員工編號已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && "1".equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotNull(user.getFactoryIdArray())&&user.getFactoryIdArray().length>0){
            StringBuilder factory = new StringBuilder();
            for (int i = 0; i < user.getFactoryIdArray().length; i++) {
                if (i == 0) {
                    factory.append(user.getFactoryIdArray()[i]);
                } else {
                    factory.append(",").append(user.getFactoryIdArray()[i]);
                }
            }
            user.setFactoryId(factory.toString());
        }

        if (StringUtils.isNotNull(user.getPlcInfo())&&user.getPlcInfo().length>0){
            StringBuilder factory = new StringBuilder();
            for (int i = 0; i < user.getPlcInfo().length; i++) {
                if (i == 0) {
                    factory.append(user.getPlcInfo()[i]);
                } else {
                    factory.append(",").append(user.getPlcInfo()[i]);
                }
            }
            user.setPlc(factory.toString());
        }
        user.setCreateBy(getUsername());
        user.setDisplayStatus("0");
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
//        user.setPassword("");
        //先判断身份证格式
        try {
            IDcard.checkIdCard(user.getIdCard());
            IDcard.competeUserByIdcard(user);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }

        user.setSended(0l);

        int userRow = userService.insertUser(user);
        if (userRow > 0) {
//            // 新增用户的时候，可能需要下发车牌权限
//            pool.threadPoolTaskExecutor().execute(() -> plateSendService.userCarDownSend(userService.selectUserById(user.getUserId())));
            return toAjax(userRow);
        }
        return AjaxResult.error(user.getIdCard() + "身份证号已存在");

    }

    @GetMapping("/deleteFace")
    public AjaxResult deleteFace(Long userId) {
        try {
            SysUser sysUser = userService.selectUserById(userId);
            if (StringUtils.isNotBlank(sysUser.getIdCard())){
                personSendService.downSendDeletePersonOnlyFace(sysUser.getIdCard());
            }
            userService.deleteFaceByUserId(userId);

            IdCardBO idCardBO = new IdCardBO();
            idCardBO.setIdCard(sysUser.getIdCard());

            String json = JSONObject.toJSONString(idCardBO);
            HttpUtils.sendJsonPost(centHost + "/api/wechat/faceData/deleteFaceCenterUser", json);


            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("操作失敗，請稍後再試。");
        }
    }




}

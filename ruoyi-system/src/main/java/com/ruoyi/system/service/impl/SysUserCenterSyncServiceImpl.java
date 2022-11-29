package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IZhongUserService;
import com.ruoyi.system.service.SysUserCenterSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 分厂--分厂--分厂
 * 分厂人员保存 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserCenterSyncServiceImpl implements SysUserCenterSyncService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysUserCenterSyncService userCenterSyncService;

    @Override
    public int addCenterUser(SysUser user) {
        //修改判斷編號是否重複
        SysUser userNo = userService.getByUserNo(user.getEmpNo());
        int userRow = -1;
        //如果原来有基础数据
        if (userNo != null) {
            BeanUtils.copyBeanProp(userNo,user);
            userNo.setPassword("");
            userNo.setSended(0L);
            //同步后会隐藏该人员
            userNo.setDisplayStatus("2");
            System.out.println("老数据添补数据 = " + JSON.toJSONString(userNo));

            userRow = userService.insertUser(userNo);
        }else {
            user.setPassword("");
            user.setSended(0L);
            //同步后会隐藏该人员
            user.setDisplayStatus("2");
            System.out.println("新数据加入 = " + JSON.toJSONString(user));

            userRow = userService.insertUser(user);
        }
        return userRow;
    }

    @Override
    public int syncUpdateUser(SysUser centerUser) {
        SysUser oldUser = userService.selectUserByIdCard(centerUser.getIdCard());
        //判斷是否修改
        if (!oldUser.getEmpNo().equals(centerUser.getEmpNo())) {
            //修改判斷編號是否重複
            SysUser userNo = userService.getByUserNo(centerUser.getEmpNo());
            if (userNo != null) {
                return -1;
            }
        }
        userService.checkUserAllowed(centerUser);
        userService.checkUserDataScope(centerUser.getUserId());
        if (StringUtils.isNotEmpty(centerUser.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(centerUser))) {
            return -1;
        } else if (StringUtils.isNotEmpty(centerUser.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(centerUser))) {
            return -1;
        }
        centerUser.setUpdateBy(getUsername());
        int userRow = userService.updateUser(centerUser);

        return userRow;
    }

}

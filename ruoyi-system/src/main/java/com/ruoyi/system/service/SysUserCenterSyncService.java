package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.CenterSyncUser;

import java.util.List;

/**
 * 用户 业务层
 *
 * @author ruoyi
 */
public interface SysUserCenterSyncService {


    /**
     * 从中心 ---> 分厂区
     * 添加员工
     */
    int addCenterUser(SysUser user);

    /**
     * 从中心 ---> 分厂区
     * 更新员工
     */
    int syncUpdateUser(SysUser centerUser);

}

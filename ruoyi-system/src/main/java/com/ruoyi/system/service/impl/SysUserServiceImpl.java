package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.BatisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.bo.CentMemberBo;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.ZhongUser;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISyncCentEmpService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IZhongUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;
    @Autowired
    protected Validator validator;

    @Autowired
    private IZhongUserService zhongUserService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private ISyncCentEmpService syncCentEmpService;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserListDisplay(SysUser user) {
        return userMapper.selectUserListDisplay(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public SysUser selectUserByIdCard(String idCard) {
        return userMapper.selectUserByIdCard(idCard);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        if (user.getIdCard() != null) {
            user.setUserId(null);

            //根據身份証號查詢
            SysUser sysUser = userMapper.selectUserByIdCard(user.getIdCard());
            if (sysUser == null) {
                // 新增用户信息
                int rows = userMapper.insertUser(user);
                // 新增用户岗位关联
                insertUserPost(user);
                // 新增用户与角色管理
                insertUserRole(user);
                return rows;
            }
            return -1;
        } else {
            user.setUserId(null);
            // 新增用户信息
            int rows = userMapper.insertUser(user);
            // 新增用户岗位关联
            insertUserPost(user);
            // 新增用户与角色管理
            insertUserRole(user);
            return rows;
        }
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        if (user.getIdCard() != null) {
            SysUser sysUser = userMapper.selectUserById(user.getUserId());
            if (user.getIdCard().equals(sysUser.getIdCard())) {
                Long userId = user.getUserId();
                // 删除用户与角色关联
                userRoleMapper.deleteUserRoleByUserId(userId);
                // 新增用户与角色管理
                insertUserRole(user);
                // 删除用户与岗位关联
                userPostMapper.deleteUserPostByUserId(userId);
                // 新增用户与岗位管理
                insertUserPost(user);
                return userMapper.updateUser(user);
            } else {
                SysUser sysUser1 = userMapper.selectUserByIdCard(user.getIdCard());
                if (sysUser1 == null) {
                    Long userId = user.getUserId();
                    // 删除用户与角色关联
                    userRoleMapper.deleteUserRoleByUserId(userId);
                    // 新增用户与角色管理
                    insertUserRole(user);
                    // 删除用户与岗位关联
                    userPostMapper.deleteUserPostByUserId(userId);
                    // 新增用户与岗位管理
                    insertUserPost(user);
                    return userMapper.updateUser(user);
                }
            }
            return -1;
        } else {
            Long userId = user.getUserId();
            // 删除用户与角色关联
            userRoleMapper.deleteUserRoleByUserId(userId);
            // 新增用户与角色管理
            insertUserRole(user);
            // 删除用户与岗位关联
            userPostMapper.deleteUserPostByUserId(userId);
            // 新增用户与岗位管理
            insertUserPost(user);
            return userMapper.updateUser(user);
        }
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                if (StringUtils.isBlank(user.getEmpNo())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、員工編號 " + user.getEmpNo() + " 不能為空");
                    continue;
                }
                // 验证是否存在这个用户
                if (StringUtils.isNotBlank(user.getIdCard())){
                    SysUser u = userMapper.selectUserByIdCard(user.getIdCard());
                    if (StringUtils.isNotNull(u)){
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、身份證號 " + user.getIdCard() + " 已存在");
                        continue;
                    }
                }
                if (StringUtils.isNotBlank(user.getEmpNo())){
                    //根据用户编号查询
                    List<SysUser> sysUsers = userMapper.selectByEmpNo(user.getEmpNo());
                    if (sysUsers.size() >0){
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、員工編號 " + user.getEmpNo() + " 已存在");
                        continue;
                    }
                }
                //部門，根据部门名称查询
                if (StringUtils.isNotBlank(user.getDeptName())){
                    List<SysDept> sysDepts = sysDeptMapper.selectDeptByName(user.getDeptName());
                    if (sysDepts.size()>0){
                        user.setDeptId(sysDepts.get(0).getDeptId());
                    }
                }
                //默认导入的是员工
                user.setStatus("1");
                user.setPassword(SecurityUtils.encryptPassword(password));
                user.setCreateBy(operName);
                user.setUserName(user.getEmpNo());

                this.insertUser(user);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共導入"+userList.size()+"條數據，共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public SysUser getByUserNo(String no) {
        return userMapper.getByUserNo(no);
    }


    /****************数据导入*********************/

    @Override
    public int addUser() {
        List<ZhongUser> zhongUsers = zhongUserService.findAll();
        if (zhongUsers.size()==0){
            return 0;
        }
        zhongUsers.forEach(zhongUser -> {
            if (zhongUser.getEmpNo()!=null){
                String substring = zhongUser.getEmpNo().substring(0, 1);
                if (substring.equals("C")||substring.equals("N")){
                    return;
                }
                //查询员工代号
                SysUser byUserNo = userMapper.getByUserNo(zhongUser.getEmpNo());
                if (byUserNo!=null){
                    return;
                }
                //添加
                SysUser sysUser=new SysUser();
                sysUser.setDeptId(zhongUser.getDeptId());
                sysUser.setNickName(zhongUser.getNickName());
                sysUser.setUserName(zhongUser.getNickName());
                sysUser.setEmpNo(zhongUser.getEmpNo());
                sysUser.setIdCard(zhongUser.getIdCard());
                userMapper.insertUser(sysUser);
            }
        });
        return 1;
    }


    @Override
    public int deleteFaceByUserId(Long userId) {
        //修改状态
        //userMapper.sendBackStatus(userId,0);
        //删除人脸
        return userMapper.deleteFaceByUserId(userId);

    }

    @Override
    public int deleteSysUserFaceByUserId(Long userId) {
        //修改状态
        //userMapper.sendBackStatus(userId,0);
        //删除人脸
        return userMapper.deleteSysUserFaceByUserId(userId);

    }

    @Override
    @Transactional
    public SysUser saveForNoFace(SysUser sysUser, Map<String, JSONObject> map) {
        String idCard = sysUser.getIdCard();
        if (map.containsKey(idCard)) {
            JSONObject jObj = map.get(idCard);
            String face = jObj.getString("face");

            sysUser.setFace(face);//从中心库获得头像
            userMapper.updateFaceByIdCard(face, idCard);//更新无头像的人员
        }
        return sysUser;
    }

    /**
     * 保存中心库人脸
     *
     * @param sysUser
     */
    public void saveFaceForCenter(SysUser sysUser) {
        //通过身份证查找人员
        SysUser sysUserCenter = userMapper.selectUserByIdCard(sysUser.getIdCard());
        if (sysUserCenter == null) {//无数据则新增
            sysUserCenter = new SysUser();
            BeanUtils.copyProperties(sysUser, sysUserCenter);
            sysUserCenter.setUserId(null);
            userMapper.insertUser(sysUserCenter);

        }else{//有数据
            sysUserCenter.setFace(sysUser.getFace());
            userMapper.updateUser(sysUserCenter);
        }
    }

    /**
     * 根据身份证获取
     * @param idCards 身份证号
     */
    public Map<String,SysUser> getListByIdCards(List<String> idCards){
        Map<String,SysUser> map=new HashMap<String ,SysUser>();
        List<SysUser> list= userMapper.selectListFaceByIdCards(idCards);
        for (SysUser item:list){
            String key=item.getIdCard();
            if(!map.containsKey(key)){
                map.put(key,item);
            }
        }
        return map;
    }



//    @Override
//    @Transactional
//    public int syncCentByUserIds(Long[] userIds) {
//        boolean forceUpdate = true;
//        List<SysUser> sysUserList = userMapper.selectSysUserListByIds(userIds);
//        return syncCentByEmp(sysUserList, forceUpdate);
//    }

    @Override
    @Transactional
    public int syncCentByUserIds(Long[] userIds) {
        boolean forceUpdate = true;
        List<SysUser> oldList = userMapper.selectSysUserListByIds(userIds);
        List<String> empNos = oldList.stream().map(SysUser::getEmpNo).collect(Collectors.toList());
        List<CentMemberBo> memberBoList = syncCentEmpService.getListFromCentByPage(1, 1000, "", StringUtils.join(empNos, ","));
        //處理
        return this.syncCentByPage(memberBoList, oldList);
    }

    /**
     * 從中心庫同步
     * @param list
     * @return
     */
    /**
    public int syncCentByEmp(List<SysUser> list,boolean forceUpdate){
        int result = 0;
        List<SysUser> updateListEmp = new ArrayList<SysUser>();
        SysUser entityEmp = null;

        List<String> idNoList = list.stream().filter(x -> !StringUtils.isEmpty(x.getIdCard()))
                .map(SysUser::getIdCard).collect(Collectors.toList());

        Map<String, CentMemberBo> memberBoMap = syncCentEmpService.getListFromCent(StringUtils.join(idNoList,","));

        for (SysUser item : list) {
            //不存在證件號，不更新
            if (StringUtils.isEmpty(item.getIdCard())) {
                continue;
            }
            CentMemberBo memberBo = memberBoMap.get(item.getIdCard());
            if (memberBo == null) {
                continue;
            }
            //是否更新數據
            boolean updateInfo = this.isUpdateInfoFromCent(item,memberBo);

            if (updateInfo) {
                entityEmp = new SysUser();
                entityEmp.setUserId(item.getUserId());
                entityEmp.setIdCard(item.getIdCard());
                entityEmp.setUpdateBy(SecurityUtils.getUsername());
                entityEmp.setUpdateTime(DateUtils.getNowDate());

                if (updateInfo) {
                    entityEmp=this.getSysUserByCentMemberBo(entityEmp,memberBo);
                }

                updateListEmp.add(entityEmp);
            }
        }

        //維護人員表
        if (updateListEmp.size() > 0) {
            List<BasePeople> peopleList = basePeopleService.transEmpToBasePeople(updateListEmp);
            result += basePeopleService.saveBasePeople(peopleList);
        }

        //內部人員
        if (updateListEmp.size() > 0) {
            List<List<SysUser>> lists = BatisUtils.splitList(updateListEmp, 40);
            for (List<SysUser> ls : lists) {
                result += userMapper.batchUpdateUserFromCent(ls);
            }
        }
        return result;
    }
     ***/

    //是否从中心库更新基本信息（人脸除外）
    public boolean isUpdateInfoFromCent(SysUser oldVo, CentMemberBo memberBo) {
        SysUser newVo = new SysUser();
        newVo = getSysUserByCentMemberBo(newVo, memberBo);

        //不相等才更新
        if (!oldVo.toSyncCentCompareString().equals(newVo.toSyncCentCompareString())) {
            return true;
        } else {
            return false;
        }
    }

    //是否从中心库更新基本信息（人脸除外）
    public boolean isUpdateInfoFromCentNew(SysUser oldVo, CentMemberBo memberBo) {
        SysUser newVo = new SysUser();
        newVo = CentMemberBo.transToSysUser(memberBo, newVo);

        //不相等才更新
        if (!oldVo.toSyncCentCompareString().equals(newVo.toSyncCentCompareString())) {
            return true;
        } else {
            return false;
        }
    }

    //中心库人员转为内部人员数据
    public SysUser getSysUserByCentMemberBo(SysUser newVo, CentMemberBo memberBo) {
        newVo.setNickName(memberBo.getName());
        newVo.setSex(memberBo.getSex());
        newVo.setPhonenumber(memberBo.getMobile());
        newVo.setFamilyAddress(memberBo.getAddress());
        //newVo.setCarId(memberBo.getLicensePlate());
        newVo.setFace(memberBo.getFace());
        return newVo;
    }

    /**
     * 從中心庫同步
     *
     * @return
     */
    public int syncCentByPage(List<CentMemberBo> memberBoList, List<SysUser> oldList) {
        int result = 0;
        List<SysUser> addList = new ArrayList<>();
        List<SysUser> updateList = new ArrayList<SysUser>();
        SysUser entity = null;

        if (memberBoList == null) {
            return result;
        }

        for (CentMemberBo memberBo : memberBoList) {
            String key = memberBo.getEmpNo();
            if (StringUtils.isEmpty(key)) {
                continue;
            }
            List<SysUser> tmpList = oldList.stream()
                    .filter(x -> Objects.equals(x.getEmpNo(), key))
                    .collect(Collectors.toList());
            boolean isAdd = true;
            SysUser oldVo = new SysUser();
            if (tmpList.size() > 0) {//修改
                entity = tmpList.get(0);
                BeanUtils.copyProperties(entity, oldVo);
                isAdd = false;
            } else {//新增
                entity = new SysUser();
                isAdd = true;
            }
            entity = CentMemberBo.transToSysUser(memberBo, entity);

            if (isAdd) {
                //新增數據默認隱藏
                entity.setDisplayStatus("2");
                addList.add(entity);
            } else {
                //是否更新數據
                boolean updateInfo = this.isUpdateInfoFromCent(oldVo, memberBo);

                if (updateInfo) {
                    entity.setUserId(oldVo.getUserId());
                    entity.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
                    entity.setUpdateTime(DateUtils.getNowDate());

                    if (updateInfo) {
                        updateList.add(entity);
                    }
                }
            }
        }

        if (addList.size() > 0) {
            List<List<SysUser>> lists = BatisUtils.splitList(addList, 40);
            for (List<SysUser> ls : lists) {
                result += userMapper.batchInsertUserFromCent(ls);
            }
        }

        //內部人員
        if (updateList.size() > 0) {
            List<List<SysUser>> lists = BatisUtils.splitList(updateList, 40);
            for (List<SysUser> ls : lists) {
                result += userMapper.batchUpdateUserFromCent(ls);
            }
        }
        return result;
    }

    /*@Override
    @Transactional
    public int syncCent() {
        int result = 0;
        boolean forceUpdate = false;

        //批量獲取
        List<SysUser> sysUserList=new ArrayList<>();
        int pageNum = 1;
        int pageSize = 1000;
        String orderBy="";
        do {
            PageHelper.startPage(pageNum++, pageSize, orderBy).setReasonable(true);
            sysUserList = userMapper.selectUserList(new SysUser());
            //處理廠商人員
            result+=this.syncCentByEmp(sysUserList,forceUpdate);

        }while (sysUserList.size()>=pageSize);
        return result;
    }*/

    @Override
    @Transactional
    public int syncCent() {
        int result = 0;
        List<SysUser> oldList = userMapper.selectUserListAll(null);

        //批量獲取
        List<CentMemberBo> memberBoList = new ArrayList<>();
        int pageNum = 1;
        int pageSize = 1000;
        String orderBy = "";
        do {
            memberBoList = syncCentEmpService.getListFromCentByPage(pageNum++, pageSize, orderBy, "");
            //處理
            result += this.syncCentByPage(memberBoList, oldList);


        } while (memberBoList.size() >= pageSize);
        return result;
    }

}

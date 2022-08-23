package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.CarCard;
import com.ruoyi.base.domain.CardRecord;
import com.ruoyi.base.interact.CarCardSendService;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.mapper.CarCardMapper;
import com.ruoyi.base.mapper.CardRecordMapper;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.utils.IDcard;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.api.basic.Response;
import com.ruoyi.web.api.dto.FacePhotoDTO;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {


    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysPostService postService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private CarCardMapper carCardMapper;
    @Autowired
    private CardRecordMapper cardRecordMapper;
    @Autowired
    private ApiService apiService;
    @Autowired
    private ThreadPoolConfig pool;
    @Autowired
    private CarCardSendService carCardSendService;
    @Autowired
    private PersonSendService personSendService;
    @Autowired
    private PlateSendService plateSendService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        if (!StringUtils.isEmpty(user.getDeptfStr())) {
            //条件查询含有部门
            SysDept sysDept = deptService.selectDeptByName(user.getDeptfStr()).get(0);
            if (sysDept != null) {
                Long deptId = sysDept.getDeptId();
                user.setDeptId(deptId);
            } else {
                user.setDeptId(Long.valueOf(99999999L));
            }
        }
        startPage();
        List<SysUser> list = userService.selectUserList(user);

        list.forEach(sysUser -> {
            StringBuffer factoryName = new StringBuffer();
            if (StringUtils.isNotBlank(sysUser.getFactoryId())) {
                String[] split = sysUser.getFactoryId().split(",");
                for (int i = 0; i < split.length; i++) {
                    SysDept factory = deptService.selectDeptById(new Long(split[i]));
                    if (i == 0) {
                        factoryName.append(factory.getDeptName());
                    } else {
                        factoryName.append("," + factory.getDeptName());
                    }

                }
            }
            sysUser.setFactoryName(factoryName.toString());
        });
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");

    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId)) {
            SysUser sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));

            if (StringUtils.isNotBlank(sysUser.getFactoryId())) {
                String[] split = sysUser.getFactoryId().split(",");
                List<Long> longList = new ArrayList<>();
                for (String s : split) {
                    longList.add(new Long(s));
                }
                ajax.put("factoryIdArray", longList);
            }


            if (StringUtils.isNotBlank(sysUser.getPlc())){
                String[] split = sysUser.getPlc().split(",");
                List<Long> list = new ArrayList<>();
                for (String s : split) {
                    list.add(new Long(s));
                }
                ajax.put("plcInfo", list);
            }

        }

        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        //修改判斷編號是否重複
        SysUser userNo = userService.getByUserNo(user.getEmpNo());
        if (userNo != null) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，員工編號已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
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
        //user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setPassword("");
        //先判断身份证格式
        try {
            IDcard.checkIdCard(user.getIdCard());
            IDcard.competeUserByIdcard(user);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }

//        user.setSended(0L);
        user.setSended(1L);

        int userRow = userService.insertUser(user);
        if (userRow > 0) {
            // 新增用户的时候，可能需要下发车牌权限
            pool.threadPoolTaskExecutor().execute(() -> plateSendService.userCarDownSend(userService.selectUserById(user.getUserId())));
            return toAjax(userRow);
        }
        return AjaxResult.error(user.getIdCard() + "身份证号已存在");
    }

    /**
     * 修改用户
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
        int userRow = userService.updateUser(user);
        if (userRow > 0) {
            pool.threadPoolTaskExecutor().execute(() -> apiService.userBindHlk(sysUserMapper.selectUserById(user.getUserId())));
            //这里需要对比前后车牌号差异，有的需要新增，有的需要删除
            pool.threadPoolTaskExecutor().execute(() -> plateSendService.userPlateDiffSend(sysUserMapper.selectUserById(user.getUserId()), oldUser.getCarId()));
            return toAjax(userRow);
        }
        return AjaxResult.error(user.getIdCard() + "身份证号已存在");
    }

//    @PreAuthorize("@ss.hasPermi('system:user:edit')")
//    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@Validated @RequestBody SysUser user) throws Exception {
//        SysUser oldUser = userService.selectUserById(user.getUserId());
//        //判斷是否修改
//        if (!oldUser.getEmpNo().equals(user.getEmpNo())) {
//            //修改判斷編號是否重複
//            SysUser userNo = userService.getByUserNo(user.getEmpNo());
//            if (userNo != null) {
//                return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，員工編號已存在");
//            }
//        }
//        userService.checkUserAllowed(user);
//        userService.checkUserDataScope(user.getUserId());
//        if (StringUtils.isNotEmpty(user.getPhonenumber())
//                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
//            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
//        } else if (StringUtils.isNotEmpty(user.getEmail())
//                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
//        }
//        user.setUpdateBy(getUsername());
//
//        StringBuilder factory = new StringBuilder();
//        if (StringUtils.isNull(user.getFactoryIdArray())) {
//            user.setFactoryId(null);
//        } else {
//            for (int i = 0; i < user.getFactoryIdArray().length; i++) {
//                if (i == 0) {
//                    factory.append(user.getFactoryIdArray()[i]);
//                } else {
//                    factory.append(",").append(user.getFactoryIdArray()[i]);
//                }
//            }
//            user.setFactoryId(factory.toString());
//        }
//        //添加通道
//        StringBuffer plc = new StringBuffer();
//        if (StringUtils.isNull(user.getPlcInfo())) {
//            user.setPlc(null);
//        } else {
//            for (int i = 0; i < user.getPlcInfo().length; i++) {
//                if (i == 0) {
//                    plc.append(user.getPlcInfo()[i]);
//                } else {
//                    plc.append(",").append(user.getPlcInfo()[i]);
//                }
//            }
//            user.setPlc(plc.toString());
//        }
//
//        IDcard.competeUserByIdcard(user);
//        int userRow = userService.updateUser(user);
//        if (userRow > 0) {
//            pool.threadPoolTaskExecutor().execute(() -> apiService.userBindHlk(sysUserMapper.selectUserById(user.getUserId())));
//            //这里需要对比前后车牌号差异，有的需要新增，有的需要删除
//            pool.threadPoolTaskExecutor().execute(() -> plateSendService.userPlateDiffSend(sysUserMapper.selectUserById(user.getUserId()), oldUser.getCarId()));
//            return toAjax(userRow);
//        }
//        return AjaxResult.error(user.getIdCard() + "身份证号已存在");
//    }


    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, getUserId())) {
            return error("当前用户不能删除");
        }
        //判断是否还有车卡、定位卡的绑定
        for (int i = 0; i < userIds.length; i++) {
            SysUser sysUser = userService.selectUserById(userIds[i]);
            if (StringUtils.isNotBlank(sysUser.getCarCard())) {
                return error("员工车卡尚未解绑，不允许删除");
            }
            if (StringUtils.isNotBlank(sysUser.getPositionCardNo())) {
                return error("员工定位卡尚未解绑，不允许删除");
            }
        }
        for (int i = 0; i < userIds.length; i++) {
            //还需要同时解绑车牌
            SysUser sysUser = userService.selectUserById(userIds[i]);
            String oldPlateNo = sysUser.getCarId();
            sysUser.setCarId(null);
            plateSendService.userPlateDiffSend(sysUser, oldPlateNo);
            personSendService.downSendDeletePerson(sysUser.getIdCard());
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    // 人脸批量上传
    @ResponseBody
    @PostMapping("/batchFaceUp")
    public Response batchFaceUp(@RequestBody String faceList) {
        try {
            JSONArray listArray = JSONArray.parseArray(faceList);
            List<FacePhotoDTO> facePhotoDTOS = JSONArray.parseArray(listArray.toString(), FacePhotoDTO.class);
            Integer upNum = 0;
            for (FacePhotoDTO facePhotoDTO : facePhotoDTOS) {
                //url:/profile/face/2022/03/07/王工_486542589615843551_20220307101350A007.jpg
                String url = facePhotoDTO.getUrl();
                //nameWithIdCard[0] 是員工姓名 nameWithIdCard[1] 是員工身份證號
                String nameWithIdCard[] = url.substring(url.lastIndexOf("/"), url.lastIndexOf("_")).split("_");
                SysUser sysUser = sysUserMapper.selectUserByIdCard(nameWithIdCard[1]);
                if (!StringUtils.isNull(sysUser)) {
                    sysUser.setFace(url);
                    int i = sysUserMapper.updateUser(sysUser);
                    if (i == 1) {
                        upNum = upNum + i;
                    }
                }
            }
            return Response.success("上传：" + facePhotoDTOS.size() + "张," + upNum + " 人更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("設置出錯，請稍後再試！");
    }


    /**
     * 删除车卡
     */
    @PostMapping("/delCarCard")
    public Response delCarCard(@RequestBody String requestObj) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestObj);
            //获取要删除的车卡
            String carCarNo = (String) jsonObject.get("carCardNo");
            //删除车卡的员工
            SysUser empNo = sysUserMapper.getByUserNo((String) jsonObject.get("empNo"));
            String[] split = empNo.getCarCard().split(",");
            List<String> list = Arrays.asList(split);
            int carCardIndex = getCarCardIndex(list, carCarNo);
            List<String> list1 = new ArrayList<>(list);
            //删除车卡
            list1.remove(carCardIndex);
            String carCardlongStr = "";
            //删除后重新拼接员工车卡
            for (int i = 0; i < list1.size(); i++) {
                String cardNo = list1.get(i);
                if (i == 0) {
                    carCardlongStr = cardNo;
                } else {
                    carCardlongStr = carCardlongStr + ("," + cardNo);
                }
            }
            //更新员工绑定车卡信息
            SysUser sysUser = new SysUser();
            sysUser.setCarCard(carCardlongStr);
            sysUser.setUserId(empNo.getUserId());
            sysUserMapper.updateUser(sysUser);
            //修改车卡状态为未绑定
            CarCard carCard = new CarCard();
            carCard.setCardCarNo(carCarNo);
            carCard.setCardCarStatus("0");
            carCard.setBindPlateNo(null);
            carCard.setCardCarUse("");
            carCard.setReturnName(empNo.getNickName());
            carCard.setReturnTime(new Date());
            carCardMapper.updateCarCardByNo(carCard);
            //插入卡片记录
            //根据删除的车卡编号拿到车卡信息插入到历史记录
            CarCard carCardInfo = carCardMapper.selectCarCardByCardCarNo(carCarNo);
            CardRecord cardRecord = new CardRecord();
            cardRecord.setCardType("0");
            cardRecord.setCardId(carCardInfo.getCardCarId());
            cardRecord.setCardNo(carCarNo);
            cardRecord.setCardRecordOperate("1");//归还操作记录
            cardRecord.setCardRecordObject(empNo.getNickName());
            cardRecord.setCardRecordTime(new Date());
            cardRecord.setCardRecordName(getUsername());
            cardRecordMapper.insertCardRecord(cardRecord);
            // 用户解绑车卡
            pool.threadPoolTaskExecutor().execute(() -> carCardSendService.downSendUnbindCarCard(carCarNo));
            return Response.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("删除失败");
        }
    }

    /**
     * 添加车卡
     */
    @PostMapping("/addCarCard")
    public Response addCarCard(@RequestBody String requestObj) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestObj);
            //获取要添加的车卡
            String carCarNo = (String) jsonObject.get("carCardNo");
            //要添加车卡的员工信息
            SysUser empNo = sysUserMapper.getByUserNo((String) jsonObject.get("empNo"));
            String[] split;
            List<String> list;
            List<String> list1;
            if (!StringUtils.isEmpty(empNo.getCarCard())) {
                split = empNo.getCarCard().split(",");
                list = Arrays.asList(split);
                list1 = new ArrayList<>(list);
                list1.add(carCarNo);
            } else {
                //添加车卡
                list1 = new ArrayList<>();
                list1.add(carCarNo);
            }
            String carCardlongStr = "";
            //删除后重新拼接员工车卡
            for (int i = 0; i < list1.size(); i++) {
                String cardNo = list1.get(i);
                if (i == 0) {
                    carCardlongStr = cardNo;
                } else {
                    carCardlongStr = carCardlongStr + ("," + cardNo);
                }
            }
            //更新员工绑定车卡信息
            SysUser sysUser = new SysUser();
            sysUser.setCarCard(carCardlongStr);
            sysUser.setUserId(empNo.getUserId());
            sysUserMapper.updateUser(sysUser);
            //修改车卡状态为已绑定
            CarCard carCard = new CarCard();
            carCard.setCardCarNo(carCarNo);
            carCard.setCardCarStatus("1");
            carCard.setCardCarUse("1");
            carCard.setBindPlateNo(null);
            carCard.setLeadName(empNo.getNickName());
            carCard.setLeadTime(new Date());
            carCardMapper.updateCarCardByNo(carCard);
            //插入卡片记录
            //根据T添加的的车卡编号拿到车卡信息
            CarCard carCardInfo = carCardMapper.selectCarCardByCardCarNo(carCarNo);
            CardRecord cardRecord = new CardRecord();
            cardRecord.setCardType("0");
            cardRecord.setCardId(carCardInfo.getCardCarId());
            cardRecord.setCardNo(carCarNo);
            cardRecord.setCardRecordOperate("0");//归还操作记录
            cardRecord.setCardRecordObject(empNo.getNickName());
            cardRecord.setCardRecordTime(new Date());
            cardRecord.setCardRecordName(getUsername());
            cardRecordMapper.insertCardRecord(cardRecord);
            //最后发送车牌权限
            pool.threadPoolTaskExecutor().execute(() -> carCardSendService.userCarCardDownSend(sysUserMapper.selectUserById(sysUser.getUserId())));
            return Response.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("添加失败");
        }
    }

    public static int getCarCardIndex(List<String> list, String carCarNo) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(carCarNo)) {
                return i;
            }
        }
        return 0;
    }

    @GetMapping("/deleteFace")
    public AjaxResult deleteFace(Long userId) {
        try {
            SysUser sysUser = userService.selectUserById(userId);
            if (StringUtils.isNotBlank(sysUser.getIdCard())){
                personSendService.downSendDeletePerson(sysUser.getIdCard());
            }
            userService.deleteFaceByUserId(userId);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("操作失敗，請稍後再試。");
        }
    }

    /**
     * 批量下发员工人员
     */
    @DeleteMapping(value = "sendUserMsgList/{ids}")
    public AjaxResult sendUserMsgList(@PathVariable Long[] ids) {
        apiService.sendUserIds(ids);
        return AjaxResult.success();
    }

    @GetMapping(value = "addUser")
    public AjaxResult addUser() {
        int i = userService.addUser();
        if (i > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }

    }
}

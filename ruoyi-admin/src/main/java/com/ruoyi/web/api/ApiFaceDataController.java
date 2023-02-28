package com.ruoyi.web.api;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.FactoryWorkBO;
import com.ruoyi.base.bo.WorkCarBo;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.interact.HlkFaceCheckUtil;
import com.ruoyi.base.mapper.ManFactoryMapper;
import com.ruoyi.base.service.*;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.service.impl.ManFactoryServiceImpl;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.utils.IDcard;
import com.ruoyi.base.utils.ZJFConverter;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.ImgFileTools;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.api.basic.Response;
import com.ruoyi.web.api.bo.EmployeeBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author shiva   2022/3/5 14:34
 */
@RestController
@RequestMapping("/api/wechat/faceData")
public class ApiFaceDataController {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ManFactoryMapper factoryMapper;
    @Autowired
    private ManFactoryServiceImpl factoryService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private ThreadPoolConfig pool;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private HlkFaceCheckUtil hlkFaceCheckUtil;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IManWorkService iManWorkService;
    @Autowired
    private IManBlackInfoService manBlackInfoService;
    @Autowired
    private IBaseCarService baseCarService;
    @Autowired
    private IHcWorkOrderCarService hcWorkOrderCarService;
    @Autowired
    private IHcWorkOrderUserService hcWorkOrderUserService;
    @Autowired
    private IHcUserService hcUserService;
    @Autowired
    private IHcWorkOrderService hcWorkOrderService;
    @Autowired
    private IHcCarService hcCarService;
    /**
     * 中心库地址
     */
    @Value("${cent.host}")
    private String centHost;

    //通过员工编号查询员工信息，注意员工编号唯一
    @ResponseBody
    @GetMapping("/getByEmployeeNo")
    public Response getByEmployeeNo(String employeeNo) {
        try {
            if (StringUtils.isBlank(employeeNo)) {
                return Response.error("員工編號不能為空！");
            }
            SysUser user = sysUserMapper.getByUserNo(employeeNo);
            if (user == null) {
                return Response.error("未查詢到該編號，請確認後重新輸入！");
            }
            //如果没有头像，则从中心库调用
            if(StringUtils.isEmpty(user.getFace())){
                JSONObject param=new JSONObject();
                param.put("noFaceIdCards",user.getIdCard());
                String res = HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/getListByIdCardsForEmployee", JSONObject.toJSONString(param));
                if (!StringUtils.isEmpty(res)) {
                    JSONObject resObj = JSONObject.parseObject(res);
                    if("0".equals(resObj.getString("code"))) {
                        Map<String, JSONObject> noFaceSysUserMap =  (Map<String, JSONObject>) resObj.get("data");

                        //更新没有头像的数据，并返回数据
                        if (noFaceSysUserMap.size() > 0) {
                            sysUserService.saveForNoFace(user, noFaceSysUserMap);
                        }
                    }
                }
            }



            EmployeeBO employeeBO = new EmployeeBO();
            BeanUtils.copyProperties(user, employeeBO);


            return Response.builder().code(0).data(employeeBO).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

    //上傳照片，返回照片地址
    @ResponseBody
    @RequestMapping("/uploadFaceImg")
    public Response uploadFaceImg(MultipartFile file) {
        try {
            if (file.getSize() == 0) {
                return Response.error("圖片不存在，請重新上傳");
            }
            // 返回：/profile/face/2022/03/06/原文件名_20220306102949A001.png
            // 图片全路径为： http://localhost:8080 + fileUrl
            String fileUrl = FileUploadUtils.upload(RuoYiConfig.getProfile() + "/face", file);
            //先进行人脸校验
            String key = sysConfigService.selectConfigByKey("sys.face.check");
            if (StringUtils.isBlank(key)) {
                key = "0";
            }
            int type = Integer.parseInt(key);
            if (type == 0) {

                fileUrl = ImgFileTools.compressionLessByGoogle(RuoYiConfig.getProfile(), fileUrl, 300);
                String face = hlkFaceCheckUtil.hikFaceJudge(fileUrl);
                if (face != null) {
                    return Response.builder().code(1).data(face).build();
                }
            }
            //循环压缩
            if (file.getSize() > 1024 * 100) {
                fileUrl = ImgFileTools.compressionLessByGoogle(RuoYiConfig.getProfile(), fileUrl, 100);
            }
            System.out.println("profile:++" + fileUrl);

            return Response.builder().code(0).data(fileUrl).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("上傳錯誤，請稍後再試。");
    }

    //除了人臉的人員圖片，比如司機駕照、押運員許可證等
    @ResponseBody
    @RequestMapping("/uploadUserImg")
    public Response uploadUserImg(MultipartFile file) {
        try {
            if (file.getSize() == 0) {
                return Response.error("圖片不存在，請重新上傳");
            }
            // 返回：/profile/face/2022/03/06/原文件名_20220306102949A001.png
            // 图片全路径为： http://localhost:8080 + fileUrl
            String fileUrl = FileUploadUtils.upload(RuoYiConfig.getProfile() + "/user", file);

            //循环压缩
            if (file.getSize() > 1024 * 100) {
                fileUrl = ImgFileTools.compressionLessByGoogle(RuoYiConfig.getProfile(), fileUrl, 100);
            }
            System.out.println("profile:++" + fileUrl);

            return Response.builder().code(0).data(fileUrl).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("上傳錯誤，請稍後再試。");
    }

    //上傳照片，返回照片地址
    @ResponseBody
    @RequestMapping("/uploadCarImg")
    public Response uploadCarImg(MultipartFile file) {
        try {
            if (file.getSize() == 0) {
                return Response.error("圖片不存在，請重新上傳");
            }
            // 返回：/profile/face/2022/03/06/原文件名_20220306102949A001.png
            // 图片全路径为： http://localhost:8080 + fileUrl
            String fileUrl = FileUploadUtils.upload(RuoYiConfig.getProfile() + "/car", file);

            //循环压缩
            if (file.getSize() > 1024 * 100) {
                fileUrl = ImgFileTools.compressionLessByGoogle(RuoYiConfig.getProfile(), fileUrl, 100);
            }
            System.out.println("profile:++" + fileUrl);

            return Response.builder().code(0).data(fileUrl).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("上傳錯誤，請稍後再試。");
    }

    // 根据员工 id,設置人臉照片
    @ResponseBody
    @GetMapping("/facePicForEmployeeCar")
    public Response facePicForEmployeeCar(Long id, String facePicUrl, String idCard,Long emisStandard,String emisStandardName,String envSign) {
        try {
            if (id == null || StringUtils.isBlank(facePicUrl)) {
                return Response.error("資料不全，請稍後再試。");
            }
            SysUser sysUser = sysUserMapper.selectUserById(id);
            if (sysUser == null) {
                return Response.error("用戶不存在，請稍後再試！");
            }
            // 設置人臉照片，重新保存
            IDcard.checkIdCard(idCard);
            //身份证校验通过，塞进去
            sysUser.setIdCard(idCard);
            IDcard.competeUserByIdcard(sysUser);
            sysUser.setFace(facePicUrl);
            sysUserMapper.updateUser(sysUser);

            //保存車輛信息
            if(!StringUtils.isEmpty(envSign)) {
                BaseCar baseCar = new BaseCar();
                baseCar.setIdCard(sysUser.getIdCard());
                baseCar.setEmisStandard(emisStandard);
                baseCar.setEmisStandardName(emisStandardName);
                baseCar.setEnvSign(envSign);
                baseCarService.saveBaseCarForEmployee(baseCar,sysUser.getUserId());
            }

            //同时更新中心库-修改by-sunlj
            HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForEmployee", JSONObject.toJSONString(sysUser));

            //保存照片
            pool.threadPoolTaskExecutor().execute(() -> apiService.userBindHlk(sysUserMapper.selectUserById(sysUser.getUserId())));
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    // 根据员工 id,設置人臉照片
    @ResponseBody
    @GetMapping("/facePicForEmployee")
    public Response facePicForEmployee(Long id, String facePicUrl, String idCard) {
        try {
            if (id == null || StringUtils.isBlank(facePicUrl)) {
                return Response.error("資料不全，請稍後再試。");
            }
            SysUser sysUser = sysUserMapper.selectUserById(id);
            if (sysUser == null) {
                return Response.error("用戶不存在，請稍後再試！");
            }
            // 設置人臉照片，重新保存
            IDcard.checkIdCard(idCard);
            //身份证校验通过，塞进去
            sysUser.setIdCard(idCard);
            IDcard.competeUserByIdcard(sysUser);
            sysUser.setFace(facePicUrl);
            sysUserMapper.updateUser(sysUser);

            //同时更新中心库-修改by-sunlj
            HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForEmployee", JSONObject.toJSONString(sysUser));

            //保存照片
            pool.threadPoolTaskExecutor().execute(() -> apiService.userBindHlk(sysUserMapper.selectUserById(sysUser.getUserId())));
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    // 根据员工 id,設置人臉照片
    @ResponseBody
    @GetMapping("/facePicForEmployeeSubFactory")
    public Response facePicForEmployeeSubFactory(Long id, String facePicUrl, String idCard) {
        try {
            if (id == null || StringUtils.isBlank(facePicUrl)) {
                return Response.error("資料不全，請稍後再試。");
            }
            SysUser sysUser = sysUserMapper.selectUserById(id);
            if (sysUser == null) {
                return Response.error("用戶不存在，請稍後再試！");
            }
            // 設置人臉照片，重新保存
            IDcard.checkIdCard(idCard);
            //身份证校验通过，塞进去
            sysUser.setIdCard(idCard);
            IDcard.competeUserByIdcard(sysUser);
            sysUser.setFace(facePicUrl);
            sysUserMapper.updateUser(sysUser);

            //同时更新中心库-修改by-sunlj
            HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForEmployee", JSONObject.toJSONString(sysUser));

            //保存照片
            pool.threadPoolTaskExecutor().execute(() -> apiService.userBindHlkSubSysUser(sysUserMapper.selectUserById(sysUser.getUserId())));
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    // (分場分服務)根据员工 id,設置人臉照片
    @ResponseBody
    @GetMapping("/facePicForEmployeeForSubSysUser")
    public Response facePicForEmployeeForSubSysUser(Long id, String facePicUrl, String idCard) {
        try {
            if (id == null || StringUtils.isBlank(facePicUrl)) {
                return Response.error("資料不全，請稍後再試。");
            }
            SysUser sysUser = sysUserMapper.selectUserById(id);
            if (sysUser == null) {
                return Response.error("用戶不存在，請稍後再試！");
            }

            // 設置人臉照片，重新保存
            IDcard.checkIdCard(idCard);
            //身份证校验通过，塞进去
            sysUser.setIdCard(idCard);
            IDcard.competeUserByIdcard(sysUser);
            sysUser.setFace(facePicUrl);
            sysUserMapper.updateUser(sysUser);
            sysUser.setSended(1L);
            //同时更新中心库-修改by-sunlj
            String empNo=sysUser.getEmpNo().toUpperCase();
            if(empNo.startsWith("PP")|| empNo.startsWith("N")) {
                HttpUtils.sendJsonPost(centHost + "/api/wechat/faceDataCent/saveFaceForEmployee", JSONObject.toJSONString(sysUser));
            }
            //保存照片
             apiService.userBindHlkSubSysUser(sysUserMapper.selectUserById(sysUser.getUserId()));
            //System.out.println("start facePicForEmployeeForSubSysUser3");

            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    // 根据工单号，查询厂商人员列表，如果
    // 0-普通工单，1-危化品工单
    @ResponseBody
    @GetMapping("/listPersonByWorkNo")
    public Response listPersonByWorkNo(String workNo, Integer workType) {
        try {
            if (StringUtils.isBlank(workNo)) {
                return Response.error("工單號不允許為空！");
            }
            //根据工单号，查询厂商列表
            List<FactoryWorkBO> list = factoryService.listByWorkNoAndDate(workNo, DateUtils.getDate(), workType);

            //如果没有头像，从中心库取头像
            List<String> noFaceIdCardList = list.stream().filter(x -> StringUtils.isEmpty(x.getFace()))
                    .map(FactoryWorkBO::getIdCard).collect(Collectors.toList());
            if (noFaceIdCardList.size() > 0) {
                JSONObject param=new JSONObject();
                param.put("noFaceIdCards",StringUtils.join(noFaceIdCardList, ","));
                String res = HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/getListByIdCardsForSupplier", JSONObject.toJSONString(param));
                if (!StringUtils.isEmpty(res)) {
                    JSONObject resObj = JSONObject.parseObject(res);
                    if("0".equals(resObj.getString("code"))) {
                        Map<String, JSONObject> noFaceManFactoryMap = (Map<String, JSONObject>) resObj.get("data");

                        //更新没有头像的数据，并返回数据
                        if (noFaceManFactoryMap.size() > 0) {
                            factoryService.saveForNoFace(list, noFaceManFactoryMap);
                        }
                    }
                }
            }

            if (list.size() > 0) {
                list.forEach(factoryWorkBO -> {
                    StringBuilder stringBuffer = new StringBuilder();
                    if (StringUtils.isNotBlank(factoryWorkBO.getIdCard())) {
                        if (factoryWorkBO.getIdCard().length() > 10) {
                            stringBuffer.append(factoryWorkBO.getIdCard().substring(0, 4)).append("********").append(factoryWorkBO.getIdCard().substring(factoryWorkBO.getIdCard().length() - 4, factoryWorkBO.getIdCard().length()));
                        } else {
                            stringBuffer.append(factoryWorkBO.getIdCard().substring(0, 2)).append("******").append(factoryWorkBO.getIdCard().substring(factoryWorkBO.getIdCard().length() - 2, factoryWorkBO.getIdCard().length()));
                        }
                    }
                    factoryWorkBO.setIdCard(stringBuffer.toString());
                });
            }
            if (workType == 1) {
                List<WorkCarBo> workCarList = iManWorkService.selectManWork(workNo, DateUtils.getDate(),workType);
                JSONObject back = new JSONObject();
                back.put("list", list);
                back.put("workCarList", workCarList);
                return Response.builder().code(0).data(back).build();
            } else {
                return Response.builder().code(0).data(list).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("設置出錯，請稍後再試！");
    }

    // 根据厂商人员ID，上传人脸照片，并设置人脸
    @ResponseBody
    @GetMapping("/facePicForSupplierCar")
    public Response facePicForSupplierCar(Long id, String facePicUrl, String phone, String address, Long sex,String carIdCard,Long emisStandard,String emisStandardName,String envSign) {
        try {
            if (id == null || StringUtils.isBlank(facePicUrl)) {
                return Response.error("資料不全，請稍後再試。");
            }
            ManFactory manFactory = factoryMapper.selectManFactoryByFactoryId(id);
            if (manFactory == null) {
                return Response.error("人員不存在，請稍後再試！");
            }
            // 設置人臉照片，重新保存
            manFactory.setFace(facePicUrl);
            manFactory.setPhone(phone);
            manFactory.setAddress(ZJFConverter.SimToTra(address));
            manFactory.setSex(sex);
            manFactory.setPicInsertTime(new Date());
            factoryMapper.updateManFactory(manFactory);

            //保存車輛信息
            BaseCar baseCar=new BaseCar();
            baseCar.setIdCard(carIdCard);
            baseCar.setEmisStandard(emisStandard);
            baseCar.setEmisStandardName(emisStandardName);
            baseCar.setEnvSign(envSign);
            baseCarService.saveBaseCarForManFactory(baseCar,id);

            //同时更新中心库-修改by-sunlj
            HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForSupplier", JSONObject.toJSONString(manFactory));

            //最后调用下厂商人脸下发的方法
            pool.threadPoolTaskExecutor().execute(() -> {
                Long[] ids = new Long[]{manFactory.getFactoryId()};
                apiService.sendFactoryMsgList(ids);
            });
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("設置出錯，請稍後再試！");
    }


    // 根据厂商人员ID，上传人脸照片，并设置人脸
    @ResponseBody
    @GetMapping("/facePicForSupplier")
    public Response facePicForSupplier(Long id, String facePicUrl, String phone, String address, Long sex) {
        try {
            if (id == null || StringUtils.isBlank(facePicUrl)) {
                return Response.error("資料不全，請稍後再試。");
            }
            ManFactory manFactory = factoryMapper.selectManFactoryByFactoryId(id);
            if (manFactory == null) {
                return Response.error("人員不存在，請稍後再試！");
            }
            // 設置人臉照片，重新保存
            manFactory.setFace(facePicUrl);
            manFactory.setPhone(phone);
            manFactory.setAddress(ZJFConverter.SimToTra(address));
            manFactory.setSex(sex);
            manFactory.setPicInsertTime(new Date());
            factoryMapper.updateManFactory(manFactory);

            //同时更新中心库-修改by-sunlj
            HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForSupplier", JSONObject.toJSONString(manFactory));

            //最后调用下厂商人脸下发的方法
            pool.threadPoolTaskExecutor().execute(() -> {
                Long[] ids = new Long[]{manFactory.getFactoryId()};
                apiService.sendFactoryMsgList(ids);
            });
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("設置出錯，請稍後再試！");
    }


    // 根据厂商人员ID，上传人脸照片，并设置人脸
    @ResponseBody
    @GetMapping("/facePicForSupplierSubFactory")
    public Response facePicForSupplierSubFactory(Long id, String facePicUrl, String phone, String address, Long sex) {
        try {
            if (id == null || StringUtils.isBlank(facePicUrl)) {
                return Response.error("資料不全，請稍後再試。");
            }
            ManFactory manFactory = factoryMapper.selectManFactoryByFactoryId(id);
            if (manFactory == null) {
                return Response.error("人員不存在，請稍後再試！");
            }
            // 設置人臉照片，重新保存
            manFactory.setFace(facePicUrl);
            manFactory.setPhone(phone);
            manFactory.setAddress(ZJFConverter.SimToTra(address));
            manFactory.setSex(sex);
            manFactory.setPicInsertTime(new Date());
            factoryMapper.updateManFactory(manFactory);

            //同时更新中心库-修改by-sunlj
            HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForSupplier", JSONObject.toJSONString(manFactory));

            //最后调用下厂商人脸下发的方法
            pool.threadPoolTaskExecutor().execute(() -> {
                Long[] ids = new Long[]{manFactory.getFactoryId()};
                apiService.sendFactoryMsgListForManFactory(ids);
            });
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("設置出錯，請稍後再試！");
    }


    /**
     * H5公众号物流通道  工单下有车牌无人员 添加人员接口 2022.10.10
     * @param manFactory
     * @return
     */
    @ResponseBody
    @PostMapping("/addPersonAndFacePicForSupplier")
    public Response addPersonAndFacePicForSupplier(@RequestBody ManFactory manFactory) {
        System.out.println(manFactory);
        try {
            if (StringUtils.isBlank(manFactory.getFace())) {
                return Response.error("資料不全，請稍後再試。");
            }
            if (StringUtils.isBlank(manFactory.getIdCard())) {
                return Response.error("資料不全，請稍後再試。");
            }else{
                //判断是否在黑名单(身份証)
                ManBlackInfo blackInfo = manBlackInfoService.getBlackInfoByCard(manFactory.getIdCard());
                if (blackInfo != null) {
                    return Response.error(manFactory.getName() + "(" + manFactory.getIdCard() + ")已被拉黑，请核对后再添加！");
                }
            }
            ManFactory selectManFactoryByIdCard = factoryMapper.selectManFactoryByIdCard(manFactory.getIdCard());
            if(selectManFactoryByIdCard == null){
                // 工单插入，记得保存中间表
                manFactory.setName(ZJFConverter.SimToTra(manFactory.getName()));
                manFactory.setBirthDay(IDcard.getBirthday(manFactory.getIdCard()));
                manFactory.setSended(0);
                manFactory.setAddress(ZJFConverter.SimToTra(manFactory.getAddress()));
                manFactory.setDangerType(1);
                manFactory.setPicInsertTime(new Date());
                factoryService.insertManFactory(manFactory);
                selectManFactoryByIdCard = factoryMapper.selectManFactoryByIdCard(manFactory.getIdCard());
            }else{
                // 設置人臉照片，绑定WorkNo
                selectManFactoryByIdCard.setLcensePlate(manFactory.getLcensePlate());
                selectManFactoryByIdCard.setWorkNo(manFactory.getWorkNo());
                selectManFactoryByIdCard.setFace(manFactory.getFace());
                if(StringUtils.isNotEmpty(manFactory.getPhone())){
                    selectManFactoryByIdCard.setPhone(manFactory.getPhone());
                }
                if(manFactory.getSex()!=null){
                    selectManFactoryByIdCard.setSex(manFactory.getSex());
                }
                if(StringUtils.isNotEmpty(manFactory.getAddress())){
                    selectManFactoryByIdCard.setAddress(ZJFConverter.SimToTra(manFactory.getAddress()));
                }
                manFactory.setDangerType(1);
                selectManFactoryByIdCard.setPicInsertTime(new Date());
                factoryMapper.updateManFactory(selectManFactoryByIdCard);
            }

            //保存車輛信息
            if(!StringUtils.isEmpty(manFactory.getEnvSign())) {
                BaseCar baseCar = new BaseCar();
                baseCar.setIdCard(manFactory.getCarIdCard());
                baseCar.setEmisStandard(manFactory.getEmisStandard());
                baseCar.setEmisStandardName(manFactory.getEmisStandardName());
                baseCar.setEnvSign(manFactory.getEnvSign());
                baseCarService.saveBaseCarForManFactory(baseCar,manFactory.getFactoryId());
            }
            //同时更新中心库-修改by-sunlj
            HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForSupplier", JSONObject.toJSONString(selectManFactoryByIdCard));

            //最后调用下厂商人脸下发的方法
            ManFactory finalSelectManFactoryByIdCard = selectManFactoryByIdCard;
            pool.threadPoolTaskExecutor().execute(() -> {
                Long[] ids = new Long[]{finalSelectManFactoryByIdCard.getFactoryId()};
                apiService.sendFactoryMsgList(ids);
            });
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("設置出錯，請稍後再試！");
    }

    /**
     * 获取車详细信息
     */
    @ApiOperation("根據車牌獲取車輛信息")
    @ResponseBody
    @GetMapping(value = "/getInfoByIdCard")
    public Response getInfoByIdCard(String idCard)
    {
        BaseCar baseCar= baseCarService.selectBaseCarByIdCard(idCard);
        return Response.builder().code(0).data(baseCar).build();
    }


    //TODO   新危化流程

    @ResponseBody
    @ApiOperation("危化公众号根据车牌号查询")
    @PostMapping ("/hcQueryByIdNo")
    public AjaxResult hcQueryByIdNo(String idNo) {
        try {
            if (StringUtils.isBlank(idNo)) {
                return AjaxResult.error("車牌號不允許為空！");
            }
            //根據車牌號查詢
            List<HcWorkOrderCar> hcWorkOrderCars = hcWorkOrderCarService.selectHcWorkOrderCarByIdNo(idNo);
            return AjaxResult.success(hcWorkOrderCars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @ApiOperation("危化查询详情")
    @PostMapping("/hcQueryDetails")
    public AjaxResult hcQueryDetails(Long id) {
        try {
            return AjaxResult.success(hcWorkOrderCarService.hcQueryDetails(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @ApiOperation("物流通道根據車牌號查詢明細")
    @PostMapping("/hcQueryDetailByIdNo")
    public AjaxResult hcQueryDetailByIdNo(String idNo) {
        try {
            return AjaxResult.success(hcWorkOrderCarService.hcQueryDetailByIdNo(idNo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @ApiOperation("危化新增车辆司机详情")
    @PostMapping("/hcAddDetails")
    public AjaxResult hcAddDetails(@RequestBody HcWorkOrderCar hcWorkOrderCar) {
        try {
            return AjaxResult.success(hcWorkOrderCarService.saveHcWorkOrderCarForH5(hcWorkOrderCar));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        //return AjaxResult.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @ApiOperation("危化新增车辆押运员详情")
    @PostMapping("/hcAddEscort")
    public AjaxResult hcAddEscort(@RequestBody HcWorkOrderUser hcWorkOrderUser) {
        try {
            return AjaxResult.success(hcWorkOrderCarService.updateHcWorkOrderUserForH5(hcWorkOrderUser));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        //return AjaxResult.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @ApiOperation("危化查询人员详情")
    @PostMapping("/queryHcUserByIdNo")
    public AjaxResult queryHcUserByIdNo(String idNo) {
        try {
            if (StringUtils.isBlank(idNo)) {
                return AjaxResult.error("身份证不允許為空！");
            }
            //先從中心庫獲取數據
            List<String> idNoList=new ArrayList<>();
            idNoList.add(idNo);
            hcUserService.syncCentByHcUser(idNoList,true);
            HcUser hcUser = hcUserService.selectHcUserByIdNo(idNo);
            if(hcUser!=null){
                return AjaxResult.success(hcUser);
            }else{
                return AjaxResult.error("暫無查詢到人員信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @ApiOperation("危化查询車輛详情")
    @PostMapping("/queryHcCarByIdNo")
    public AjaxResult queryHcCarByIdNo(String idNo) {
        try {
            if (StringUtils.isBlank(idNo)) {
                return AjaxResult.error("車牌不允許為空！");
            }
            HcCar hcCar = hcCarService.selectHcCarByIdNo(idNo);
            return AjaxResult.success(hcCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("查詢出錯，請稍後再試！");
    }

    @ResponseBody
    @ApiOperation("危化工單車輛详情")
    @PostMapping("/queryHcWorkOrderCarByVhNoIdNo")
    public AjaxResult queryHcWorkOrderCarByVhNoIdNo(String vhNo,String idNo) {
        try {
            if (StringUtils.isBlank(vhNo)) {
                return AjaxResult.error("工單號不允許為空！");
            }
            if (StringUtils.isBlank(idNo)) {
                return AjaxResult.error("車牌不允許為空！");
            }
            List<HcWorkOrderCar> hcWorkOrderCarList = hcWorkOrderCarService.selectHcWorkOrderCarListByVhNoIdNo(vhNo,idNo);
            return AjaxResult.success(hcWorkOrderCarList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("查詢出錯，請稍後再試！");
    }

}

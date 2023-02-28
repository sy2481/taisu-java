package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.*;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.service.IBasicsMatchingRulesService;
import com.ruoyi.base.service.IHCPassagewayService;
import com.ruoyi.base.service.IHcWorkOrderCarService;
import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysColRequire;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysColRequireMapper;
import com.ruoyi.system.mapper.SysConfigMapper;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.spreada.utils.chinese.ZHConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.jsf.FacesContextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 开门的逻辑验证Service业务层处理
 */
@Service
public class HCPassagewayServiceImpl implements IHCPassagewayService {
    public String HistoryCarPlate = "";
    public String NewCarPlate = "";
    @Autowired
    private InOutLogMapper inOutLogMapper;
    @Autowired
    private HcWorkOrderCarMapper hcWorkOrderCarMapper;
    @Autowired
    private HcWorkOrderUserMapper hcWorkOrderUserMapper;
    @Autowired
    private IBasicsMatchingRulesService IBasicsMatchingRulesService;
    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private HcWorkOrderAuthMapper hcWorkOrderAuthMapper;
    @Autowired
    private PlcEquipmentMapper plcEquipmentMapper;
    @Autowired
    private HikEquipmentMapper hikEquipmentMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysColRequireMapper sysColRequireMapper;
    @Value("${factoryCode}")
    private String factoryCode;

    /**
     * 开门的逻辑验证
     *
     * @param param 參數
     * @return 返回內容
     */
    @Override
    public HCPassageway.HCPassagewayResultBody OpenDoorLogic(HCPassageway.HCPassagewayParamBody param) {
        HCPassageway.HCPassagewayResultBody resultBody = new HCPassageway.HCPassagewayResultBody();
        resultBody.setOpenDoor(false);
        resultBody.setFind(false);

        //判斷是否為物流通道的設備,不是的話直接返回,並不提示
        SysDept sysDept = sysDeptMapper.selectDeptByDeptNo(factoryCode);
        PlcEquipment searchPlcEquipment = new PlcEquipment();
        searchPlcEquipment.setHazardousChemicals(1L);
        searchPlcEquipment.setPlantAreaId(sysDept.getDeptId());
        List<PlcEquipment> plcEquipmentList = plcEquipmentMapper.selectPlcEquipmentList(searchPlcEquipment);//拿到物流通道的PLC
        List<HikEquipment> ThisHikEquipmentList = hikEquipmentMapper.findByIp(param.getDeviceIp());
        if (ThisHikEquipmentList.size() == 0) {
            resultBody.setMsg("未找到該海康設備數據 無需提示"+";"
                    +"factoryCode:"+factoryCode+";"
                    +"sysDept.getDeptId():"+sysDept.getDeptId()+";"
                    +"param.getDeviceIp():"+param.getDeviceIp()+";"
                    +"param.getContent():"+param.getContent()+";"
                    +"param.getType():"+param.getType()
            );
            return resultBody;
        }
        HikEquipment ThisHikEquipment = ThisHikEquipmentList.get(0);//獲取當前設備的信息
        List<PlcHik> plcHikList = hikEquipmentMapper.getPlcHik_CarByDeviceId(ThisHikEquipment.getId());
        if (plcHikList.size() == 0) {
            resultBody.setMsg("未找到該海康設備關聯的PLC關聯數據 無需提示");
            return resultBody;
        }
        PlcHik plcHik = plcHikList.get(0);//獲取當前設備綁定的PLC
        if (plcEquipmentList.stream().noneMatch(g -> Objects.equals(g.getId(), plcHik.getPlcId()))) {
            resultBody.setMsg("並非物流通道 無需提示");
            return resultBody;
        }
        PlcEquipment plcEquipment = plcEquipmentList.stream().filter(g -> Objects.equals(g.getId(), plcHik.getPlcId())).collect(Collectors.toList()).get(0);
        HikEquipment PersonDeviceEquipment = hikEquipmentMapper.selectHikEquipmentById(plcHik.getPersonDeviceId());
        if(PersonDeviceEquipment==null){
            resultBody.setMsg("未找到當前PLC關聯的海康人臉設備 無需提示");
            return resultBody;
        }
        HikEquipment CarDeviceEquipment = hikEquipmentMapper.selectHikEquipmentById(plcHik.getCarDeviceId());
        if(CarDeviceEquipment==null){
            resultBody.setMsg("未找到當前PLC關聯的海康車輛設備 無需提示");
            return resultBody;
        }
        resultBody.setDeviceIps(plcHik.getPersonEquip().getIp()+","+plcHik.getCarEquip().getIp()+","+CarDeviceEquipment.getSubtitleMachineIp());

        //獲取本廠所有的工單車輛
        HcWorkOrderCar searchForHcWorkOrderCar = new HcWorkOrderCar();
        searchForHcWorkOrderCar.setFacDorNm(factoryCode);
        List<HcWorkOrderCar> hcWorkOrderCarList = hcWorkOrderCarMapper.selectHcWorkOrderCarList(searchForHcWorkOrderCar);
        if (hcWorkOrderCarList.size() == 0) {
            resultBody.setMsg("未找到本廠工單車輛數據");
            return resultBody;
        }
        //獲取本廠所有的工單人員
        HcWorkOrderUser searchForHcWorkOrderUser = new HcWorkOrderUser();
        searchForHcWorkOrderUser.setFacDorNm(factoryCode);
        List<HcWorkOrderUser> hcWorkOrderUserList = hcWorkOrderUserMapper.selectHcWorkOrderUserList(searchForHcWorkOrderUser);
        if (hcWorkOrderCarList.size() == 0) {
            resultBody.setMsg("未找到本廠工單人員數據");
            return resultBody;
        }
        //配置項參數
        List<SysConfig> sysConfigList = sysConfigMapper.selectConfigList(new SysConfig());
        boolean ctrl_BrushlessFace = true;
        boolean ctrl_ParkingSpaceCount = false;
        boolean ctrl_StartBusiness = false;
        boolean ctrl_BrushlessDriver = false;
        boolean ctrl_BrushlessEscort = false;
        int num_ParkingSpaceCount = 0;
        if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessFace")) ||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_ParkingSpaceCount")) ||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_StartBusiness")) ||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.num_ParkingSpaceCount"))||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessEscort"))||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessDriver"))) {
            //详细判断输出
            if(sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessFace")))
                resultBody.setMsg("未找到是否免刷人臉系統配置");
            else if(sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_ParkingSpaceCount")))
                resultBody.setMsg("未找到是否開啓車位計數系統配置");
            else if(sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_StartBusiness")))
                resultBody.setMsg("未找到是否開始營業系統配置");
            else if(sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.num_ParkingSpaceCount")))
                resultBody.setMsg("未找到物流通道車位上限數系統配置");
            else if(sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessDriver")))
                resultBody.setMsg("未找到是否免填司機信息系統配置");
            else if(sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessEscort")))
                resultBody.setMsg("未找到是否免填押運員信息系統配置");
            else
                resultBody.setMsg("物流通道相關配置參數異常");
            resultBody.setOpenDoor(false);
            resultBody.setFind(false);
            return resultBody;
        }
        ctrl_BrushlessFace = Boolean.parseBoolean(sysConfigList.stream().filter(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessFace")).collect(Collectors.toList()).get(0).getConfigValue());
        ctrl_ParkingSpaceCount = Boolean.parseBoolean(sysConfigList.stream().filter(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_ParkingSpaceCount")).collect(Collectors.toList()).get(0).getConfigValue());
        ctrl_StartBusiness = Boolean.parseBoolean(sysConfigList.stream().filter(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_StartBusiness")).collect(Collectors.toList()).get(0).getConfigValue());
        num_ParkingSpaceCount = Integer.parseInt(sysConfigList.stream().filter(g -> Objects.equals(g.getConfigKey(), "sys.hc.num_ParkingSpaceCount")).collect(Collectors.toList()).get(0).getConfigValue());
        ctrl_BrushlessDriver = Boolean.parseBoolean(sysConfigList.stream().filter(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessDriver")).collect(Collectors.toList()).get(0).getConfigValue());
        ctrl_BrushlessEscort = Boolean.parseBoolean(sysConfigList.stream().filter(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessEscort")).collect(Collectors.toList()).get(0).getConfigValue());

        //获取参数配置表中的必填项内容
        String returnInfo ;
        SysColRequire colRequire_Search =new SysColRequire() ;
        colRequire_Search.setIsRequire("Y");
        List<SysColRequire> colRequireList =  sysColRequireMapper.selectSysColRequireList(colRequire_Search);
        List<SysColRequire> colRequireList_Car =colRequireList.stream().filter(g-> Objects.equals(g.getTableName(), "hcCar")).collect(Collectors.toList());
        List<SysColRequire> colRequireList_Driver =colRequireList.stream().filter(g-> Objects.equals(g.getTableName(), "hcDriver")).collect(Collectors.toList());
        List<SysColRequire> colRequireList_Escort =colRequireList.stream().filter(g-> Objects.equals(g.getTableName(), "hcEscort")).collect(Collectors.toList());

        //region 車輛計數
        if (ctrl_ParkingSpaceCount) {
            List<HcWorkOrderCar> hcWorkOrderCarList_Parking = hcWorkOrderCarList.stream().filter(g ->
                    g.getSecIpltTm() != null && g.getSecOpltTm() == null
            ).collect(Collectors.toList());

            int ParkingCarCount = (int) hcWorkOrderCarList_Parking.stream().map(HcWorkOrderCar::getIdNo).distinct().count();
            if (ParkingCarCount >= num_ParkingSpaceCount) {
                resultBody.setMsg("車位數已滿 請稍候");
                resultBody.setOpenDoor(false);
                resultBody.setFind(false);
                return resultBody;
            }
        }
        //endregion

        //region 開始營業
        if (!ctrl_StartBusiness) {
            if (CarDeviceEquipment.getSign() == 1L) {
                resultBody.setMsg("未開始營業 不允許入廠");
                resultBody.setOpenDoor(false);
                resultBody.setFind(false);
                return resultBody;
            }
        }
        //endregion


        if (Objects.equals(param.getType(), "車牌號")) {
            ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
            String Content_0_1 = ZHConverter.convert(param.getContent().substring(0,1), ZHConverter.TRADITIONAL);
            String Content_1_6 = param.getContent().substring(1).toUpperCase();
            param.setContent(Content_0_1+Content_1_6);

            String DealContentTip =param.getContent().trim().substring(0,1);
            String DealContent =param.getContent().trim().substring(1);
            //region 車牌號驗證流程
            List<HcWorkOrderCar> hcWorkOrderCarList_Basic = hcWorkOrderCarList.stream().filter(g ->
//                    Objects.equals(param.getContent(), g.getIdNo())
                    g.getIdNo().contains(DealContent)
                    //&& g.getSecOpltTm() == null   原有判断去掉  二道门可以自由出入
                    //还要加一个一道门的出场时间为空的判断
                    && g.getOpltTm() == null
            ).collect(Collectors.toList());

            //region 判斷車牌號是否存在于工單車List中
            if (hcWorkOrderCarList_Basic.size() == 0) {
                //模糊匹配
                List<String> CarPlateList = IBasicsMatchingRulesService.ReturnLicensePlate(DealContent);
                List<HcWorkOrderCar> hcWorkOrderCarList_MatchRule = hcWorkOrderCarList.stream().filter(g ->
                        CarPlateList.contains(g.getIdNo().substring(1))
                        //&& g.getSecOpltTm() == null   原有判断去掉  二道门可以自由出入
                        //还要加一个一道门的出场时间为空的判断
                                && g.getOpltTm() == null
                ).collect(Collectors.toList());
                if (hcWorkOrderCarList_MatchRule.size() == 0) {
                    //未匹配到有效車牌號(已模糊匹配),請聯繫管理員確認是否存在進出權限
                    resultBody.setMsg("車輛無權限 請聯繫管理員");
                    resultBody.setCarPlate(param.getContent());
                    return resultBody;
                } else {
                    HistoryCarPlate = param.getContent();
                    hcWorkOrderCarList_Basic = hcWorkOrderCarList_MatchRule;
                    NewCarPlate = hcWorkOrderCarList_Basic.get(0).getIdNo();
                }
            } else {
                HistoryCarPlate = param.getContent();
                NewCarPlate = param.getContent();
            }
            resultBody.setFind(true);
            resultBody.setCarPlate(NewCarPlate);
            resultBody.setCarPlateHistory(HistoryCarPlate);
            //endregion

            //显示字段：车牌号、工单号、工单有效期（从ERP获取时间）、司机姓名、司机身份证、车辆照片（需录入）、车辆颜色、车辆类型、行驶证照片（必填，需录入）、危化品运输证编号、车辆环保类型（必填，国五、国六）、车辆环保标志（必填，需录入）
            //显示字段：身份证（必填）、姓名（必填）、性别、驾驶证照片（必填，需上传）、手机号（必填）、出生年月、家庭住址、合约卡、人脸照片（必填、需上传）
            //显示字段：身份证（必填）、姓名（必填）、性别、押运员允许证（必填，需上传）、手机号（必填）、出生年月、家庭住址、合约卡、人脸照片（必填、需上传）
            //暫時先判斷必填字段 看看業主怎麽說
            //region 車牌號驗證流程-免刷人臉+刷人臉驗證邏輯
            if (ctrl_BrushlessFace) {
                //車牌號驗證流程-免刷人臉邏輯
                //region 判斷該車信息是否齊全
                HcWorkOrderCar car_check = hcWorkOrderCarList_Basic.get(0);
                if(colRequireList_Car.size() > 0){
                    returnInfo = CheckIsRequiredForCar(colRequireList_Car,car_check);
                    if(!returnInfo.contains("無需提示"))
                    {
                        resultBody.setMsg("車輛信息不全 請補全車輛信息");
                        return resultBody;
                    }
                }

                //此時車輛信息齊全,可以生成待入廠的組合認證對象
                HcWorkOrderAuth HCAuthModel = new HcWorkOrderAuth();
                HCAuthModel.setVhNo(car_check.getVhNo());
                HCAuthModel.setPlcId(plcHik.getPlcId());
                HCAuthModel.setPlcIp(plcEquipment.getIp());
                HCAuthModel.setCarDeviceId(CarDeviceEquipment.getId());
                HCAuthModel.setCarDeviceIp(CarDeviceEquipment.getIp());
                HCAuthModel.setPersonDeviceId(PersonDeviceEquipment.getId());
                HCAuthModel.setPersonDeviceIp(PersonDeviceEquipment.getIp());
                HCAuthModel.setInOutMode(CarDeviceEquipment.getSign() == 1L ? "進" : CarDeviceEquipment.getSign() == 2L ? "出" : "未知方向");
                HCAuthModel.setCarNo(NewCarPlate);
                HCAuthModel.setCarNoHistory(HistoryCarPlate);
                HCAuthModel.setUpdateTime(new Date());

                //endregion
                //region 判斷人員信息是否齊全
                List<HcWorkOrderUser> hcWorkOrderUserList_Basic = hcWorkOrderUserList.stream().filter(g ->
                        Objects.equals(NewCarPlate.substring(1), g.getLicense().substring(1))
                ).collect(Collectors.toList());
                //司機和押運員都免填就可以直接進
                if(ctrl_BrushlessDriver && ctrl_BrushlessEscort){
                    resultBody.setMsg("認證成功 請通行");
                    resultBody.setOpenDoor(true);
                    return resultBody;
                }else if (ctrl_BrushlessDriver){ //司機免填 但是押運員不免填  這個時候就算配置出錯
                    //這種情況暫時先不處理
                    resultBody.setMsg("物流通道免填配置參數錯誤 請聯繫管理員");
                    return resultBody;
                }


                if(!ctrl_BrushlessEscort && hcWorkOrderUserList_Basic.stream().anyMatch(g -> g.getUserType() == 1L)
                        && hcWorkOrderUserList_Basic.stream().anyMatch(g -> g.getUserType() == 2L)
                        ||ctrl_BrushlessEscort&&hcWorkOrderUserList_Basic.stream().anyMatch(g -> g.getUserType() == 1L)){
                    //判斷司機
                    HcWorkOrderUser DriverModel = hcWorkOrderUserList_Basic.stream().filter(g -> g.getUserType() == 1L).collect(Collectors.toList()).get(0);
                    if(colRequireList_Driver.size() > 0){
                        returnInfo =  CheckIsRequiredForUser(colRequireList_Driver,DriverModel);
                        if(!returnInfo.contains("無需提示"))
                        {
                            hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByPLCId(plcHik.getPlcId());
                            hcWorkOrderAuthMapper.insertHcWorkOrderAuth(HCAuthModel);

                            resultBody.setMsg("司機信息不全 請補全司機信息");
                            return resultBody;
                        }
                    }


                    HCAuthModel.setDriverIdCard(DriverModel.getIdNo());
                    HCAuthModel.setDriverName(DriverModel.getNm());
                    resultBody.setIdCard(DriverModel.getIdNo());

                    //判斷是否免刷押運員
                    if(ctrl_BrushlessEscort){
                        hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByPLCId(plcHik.getPlcId());
                        resultBody.setMsg("認證成功 請通行");
                        resultBody.setOpenDoor(true);
                        return resultBody;
                    }


                    //判斷押運員
                    HcWorkOrderUser EscortModel = hcWorkOrderUserList_Basic.stream().filter(g -> g.getUserType() == 2L).collect(Collectors.toList()).get(0);
                    if(colRequireList_Escort.size() > 0){
                        returnInfo =  CheckIsRequiredForUser(colRequireList_Escort,EscortModel);
                        if(!returnInfo.contains("無需提示"))
                        {
                            hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByPLCId(plcHik.getPlcId());
                            hcWorkOrderAuthMapper.insertHcWorkOrderAuth(HCAuthModel);

                            resultBody.setMsg("押運員信息不全 請補全押運員信息");
                            return resultBody;
                        }
                    }
                    //若押運人員信息也齊全,則可以直接進出  其實這個時候不生成組合認證信息也沒關係  直接清除已有數據
                    HCAuthModel.setEscortIdCard(EscortModel.getIdNo());
                    HCAuthModel.setEscortName(EscortModel.getNm());
                    hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByPLCId(plcHik.getPlcId());

                    resultBody.setMsg("認證成功 請通行");
                    resultBody.setOpenDoor(true);
                    resultBody.setIdCard2(EscortModel.getIdNo());
                    return resultBody;

                } else {
                    resultBody.setMsg("人員信息不全 請補充人員信息");
                    return resultBody;
                }
                //endregion
            } else {
                //車牌號驗證流程-要刷人臉邏輯
                //region 判斷該車信息是否齊全
                HcWorkOrderCar car_check = hcWorkOrderCarList_Basic.get(0);
                if(colRequireList_Car.size() > 0){
                    returnInfo =  CheckIsRequiredForCar(colRequireList_Car,car_check);
                    if(!returnInfo.contains("無需提示"))
                    {
                        resultBody.setMsg("車輛信息不全 請補全車輛信息");
                        return resultBody;
                    }
                }

                //此時車輛信息齊全,可以生成待入廠的組合認證對象
                HcWorkOrderAuth HCAuthModel = new HcWorkOrderAuth();
                HCAuthModel.setVhNo(car_check.getVhNo());
                HCAuthModel.setPlcId(plcHik.getPlcId());
                HCAuthModel.setPlcIp(plcEquipment.getIp());
                HCAuthModel.setCarDeviceId(CarDeviceEquipment.getId());
                HCAuthModel.setCarDeviceIp(CarDeviceEquipment.getIp());
                HCAuthModel.setPersonDeviceId(PersonDeviceEquipment.getId());
                HCAuthModel.setPersonDeviceIp(PersonDeviceEquipment.getIp());
                HCAuthModel.setInOutMode(CarDeviceEquipment.getSign() == 1L ? "進" : CarDeviceEquipment.getSign() == 2L ? "出" : "未知方向");
                HCAuthModel.setCarNo(NewCarPlate);
                HCAuthModel.setCarNoHistory(HistoryCarPlate);
                HCAuthModel.setUpdateTime(new Date());
                hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByPLCId(plcHik.getPlcId());
                hcWorkOrderAuthMapper.insertHcWorkOrderAuth(HCAuthModel);

                resultBody.setMsg("請先刷司機人臉");
                return resultBody;
                //endregion
            }
            //endregion


            //endregion 車牌號流程結束
        } else if (Objects.equals(param.getType(), "身份證")) {
            //region 身份證驗證流程-免刷人臉+刷人臉驗證邏輯  目前兩套驗證方式的代碼邏輯一致 可以直接合併成一套
            List<HcWorkOrderAuth> HCAuthModelList = hcWorkOrderAuthMapper.selectHcWorkOrderAuthByPersonDeviceIp(param.getDeviceIp());
            if (HCAuthModelList.size() == 0) {
                resultBody.setMsg("請先刷車牌號");
                return resultBody;
            }
            HcWorkOrderAuth HCAuthModel = HCAuthModelList.get(0);

            resultBody.setFind(true);
            resultBody.setCarPlate(HCAuthModel.getCarNo());
            resultBody.setCarPlateHistory(HCAuthModel.getCarNoHistory());


            //region 判斷人員信息是否齊全
            List<HcWorkOrderUser> hcWorkOrderUserList_Basic = hcWorkOrderUserList.stream().filter(g ->
                    Objects.equals(HCAuthModel.getCarNo().substring(1), g.getLicense().substring(1))
            ).collect(Collectors.toList());
            if(hcWorkOrderUserList_Basic.stream().noneMatch(g-> Objects.equals(g.getIdNo(), param.getContent()))){
                resultBody.setMsg("該通道已被其他物流車輛佔用 請重刷車牌號");
                return resultBody;
            }

            //司機和押運員都免填就可以直接進
            if(ctrl_BrushlessDriver && ctrl_BrushlessEscort){
                resultBody.setMsg("認證成功 請通行");
                resultBody.setOpenDoor(true);
                return resultBody;
            }else if (ctrl_BrushlessDriver){ //司機免填 但是押運員不免填  這個時候就算配置出錯
                //這種情況暫時先不處理
                resultBody.setMsg("物流通道免填配置參數錯誤 請聯繫管理員");
                return resultBody;
            }


            if(!ctrl_BrushlessEscort && hcWorkOrderUserList_Basic.stream().anyMatch(g -> g.getUserType() == 1L)
                    && hcWorkOrderUserList_Basic.stream().anyMatch(g -> g.getUserType() == 2L)
                    ||ctrl_BrushlessEscort&&hcWorkOrderUserList_Basic.stream().anyMatch(g -> g.getUserType() == 1L)){
                //region 判斷當前刷臉人是司機還是押運員
                if (hcWorkOrderUserList_Basic.stream().anyMatch(g -> Objects.equals(g.getIdNo(), param.getContent()) && g.getUserType() == 1L)) {
                    //region 判斷司機
                    HcWorkOrderUser DriverModel = hcWorkOrderUserList_Basic.stream().filter(g -> Objects.equals(g.getIdNo(), param.getContent()) && g.getUserType() == 1L).collect(Collectors.toList()).get(0);
                    if(colRequireList_Driver.size() > 0){
                        returnInfo =  CheckIsRequiredForUser(colRequireList_Driver,DriverModel);
                        if(!returnInfo.contains("無需提示"))
                        {
                            resultBody.setMsg("司機信息不全 請補全司機信息");
                            return resultBody;
                        }
                    }


                    if (!Objects.equals(DriverModel.getLicense(), HCAuthModel.getCarNo())) {
                        resultBody.setMsg("司機信息與車輛信息不匹配 請重刷車牌號");
                        return resultBody;
                    } else {
                        HCAuthModel.setDriverIdCard(DriverModel.getIdNo());
                        HCAuthModel.setDriverName(DriverModel.getNm());
                        resultBody.setIdCard(DriverModel.getIdNo());

                        //判斷是否免刷押運員
                        if(ctrl_BrushlessEscort){
                            hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByPLCId(plcHik.getPlcId());
                            resultBody.setMsg("認證成功 請通行");
                            resultBody.setOpenDoor(true);
                            return resultBody;
                        }else{
                            hcWorkOrderAuthMapper.updateHcWorkOrderAuth(HCAuthModel);

                            resultBody.setMsg("請刷押運員人臉");
                            return resultBody;
                        }
                    }

                    //endregion
                } else if (hcWorkOrderUserList_Basic.stream().anyMatch(g -> Objects.equals(g.getIdNo(), param.getContent()) && g.getUserType() == 2L)) {
                    //如果沒刷司機要直接提示
                    if(Objects.equals(HCAuthModel.getDriverIdCard(), "") ||HCAuthModel.getDriverIdCard()==null)
                    {
                        resultBody.setMsg("請先刷司機人臉");
                        return resultBody;
                    }
                    //region 判斷押運員
                    HcWorkOrderUser EscortModel = hcWorkOrderUserList_Basic.stream().filter(g -> Objects.equals(g.getIdNo(), param.getContent()) && g.getUserType() == 2L).collect(Collectors.toList()).get(0);
                    if(colRequireList_Escort.size() > 0){
                        returnInfo =  CheckIsRequiredForUser(colRequireList_Escort,EscortModel);
                        if(!returnInfo.contains("無需提示"))
                        {
                            resultBody.setMsg("押運員信息不全 請補全押運員信息");
                            return resultBody;
                        }
                    }

                    if (!Objects.equals(EscortModel.getLicense(), HCAuthModel.getCarNo())) {
                        resultBody.setMsg("押運員信息與車輛信息不匹配 請重刷車牌號");
                        return resultBody;
                    }
                    //若押運人員信息也齊全,則可以直接進出  其實這個時候不生成組合認證信息也沒關係  直接清除已有數據
                    HCAuthModel.setEscortIdCard(EscortModel.getIdNo());
                    HCAuthModel.setEscortName(EscortModel.getNm());
                    hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByPLCId(plcHik.getPlcId());

                    resultBody.setMsg("認證成功 請通行");
                    resultBody.setOpenDoor(true);
                    resultBody.setIdCard(HCAuthModel.getDriverIdCard());
                    resultBody.setIdCard2(EscortModel.getIdNo());

                    return resultBody;

                    //endregion
                } else {
                    resultBody.setMsg("人員信息未録入");
                    resultBody.setFind(true);
                    return resultBody;
                }
                //endregion
            } else {
                resultBody.setMsg("人員信息不全 請補充人員信息");
                return resultBody;
            }
            //endregion

            //endregion 身份證驗證流程-免刷人臉+刷人臉驗證邏輯  目前兩套驗證方式的代碼邏輯一致 可以直接合併成一套
        } else {
            resultBody.setMsg("認證方式異常");
            return resultBody;
        }
    }



    /**
     * 驗證危化車輛必填項
     * @param checkList 必填項列表
     * @param hcWorkOrderCar 人員信息
     * @return 提示內容
     */
    public String CheckIsRequiredForCar(List<SysColRequire> checkList,HcWorkOrderCar hcWorkOrderCar){
        String Content = "";
        for (SysColRequire g:checkList) {
            switch (g.getColName()){
                case "carColor":
                    if(hcWorkOrderCar.getCarColor() ==null|| Objects.equals(hcWorkOrderCar.getCarColor(), ""))
                        Content = String.format("%s車輛顏色 ", Content);
                    break;
                case "carPhoto":
                    if(hcWorkOrderCar.getCarPhoto() ==null|| Objects.equals(hcWorkOrderCar.getCarPhoto(), ""))
                        Content = String.format("%s車輛照片 ", Content);
                    break;
                case "carType":
                    if(hcWorkOrderCar.getCarType() ==null)
                        Content = String.format("%s車輛類型 ", Content);
                    break;
                case "vehicleLic":
                    if(hcWorkOrderCar.getVehicleLic() ==null|| Objects.equals(hcWorkOrderCar.getVehicleLic(), ""))
                        Content = String.format("%s行駛證照片 ", Content);
                    break;
                case "hcTransportCertNo":
                    if(hcWorkOrderCar.getHcTransportCertNo() ==null|| Objects.equals(hcWorkOrderCar.getHcTransportCertNo(), ""))
                        Content = String.format("%s危化品運輸證編號 ", Content);
                    break;
                case "emisStandard":
                    if(hcWorkOrderCar.getEmisStandard() ==null)
                        Content = String.format("%s車輛環保類型 ", Content);
                    break;
                case "envSign":
                    if(hcWorkOrderCar.getEnvSign() ==null|| Objects.equals(hcWorkOrderCar.getEnvSign(), ""))
                        Content = String.format("%s車輛環保標誌 ", Content);
                    break;
            }
        }
        if(Content.length()>0){
            Content = String.format("%s不得為空 ", Content);
            return Content;
        }
        else
            return "無需提示";
    }


    /**
     * 驗證危化人員必填項
     * @param checkList 必填項列表
     * @param hcWorkOrderUser 人員信息
     * @return 提示內容
     */
    public String CheckIsRequiredForUser(List<SysColRequire> checkList,HcWorkOrderUser hcWorkOrderUser){
        String Content = "";
        for (SysColRequire g:checkList) {
            switch (g.getColName()){
                case "idNo":
                    if(hcWorkOrderUser.getIdNo() ==null|| Objects.equals(hcWorkOrderUser.getIdNo(), ""))
                        Content = String.format("%s身份證號 ", Content);
                    break;
                case "nm":
                    if(hcWorkOrderUser.getNm() ==null|| Objects.equals(hcWorkOrderUser.getNm(), ""))
                        Content = String.format("%s姓名 ", Content);
                    break;
                case "ipltLic":
                    if(hcWorkOrderUser.getIpltLic() ==null|| Objects.equals(hcWorkOrderUser.getIpltLic(), ""))
                        Content = String.format("%s合約卡號 ", Content);
                    break;
                case "face":
                    if(hcWorkOrderUser.getFace() ==null|| Objects.equals(hcWorkOrderUser.getFace(), ""))
                        Content = String.format("%s人臉照片 ", Content);
                    break;
                case "sex":
                    if(hcWorkOrderUser.getSex() ==null)
                        Content = String.format("%s性別 ", Content);
                    break;
                case "phone":
                    if(hcWorkOrderUser.getPhone() ==null|| Objects.equals(hcWorkOrderUser.getPhone(), ""))
                        Content = String.format("%s電話 ", Content);
                    break;
                case "address":
                    if(hcWorkOrderUser.getAddress() ==null|| Objects.equals(hcWorkOrderUser.getAddress(), ""))
                        Content = String.format("%s地址 ", Content);
                    break;
                case "birthday":
                    if(hcWorkOrderUser.getBirthday() ==null)
                        Content = String.format("%s生日 ", Content);
                    break;
                case "company":
                    if(hcWorkOrderUser.getCompany() ==null|| Objects.equals(hcWorkOrderUser.getCompany(), ""))
                        Content = String.format("%s公司 ", Content);
                    break;
                case "driverLicense":
                    if(hcWorkOrderUser.getDriverLicense() ==null|| Objects.equals(hcWorkOrderUser.getDriverLicense(), ""))
                        Content = String.format("%s駕駛證照片 ", Content);
                    break;
                case "escortLicense":
                    if(hcWorkOrderUser.getEscortLicense() ==null|| Objects.equals(hcWorkOrderUser.getEscortLicense(), ""))
                        Content = String.format("%s押運員許可證 ", Content);
                    break;
            }
        }
        if(Content.length()>0){
            Content = String.format("%s不得為空 ", Content);
            return Content;
        }
        else
            return "無需提示";
    }
}

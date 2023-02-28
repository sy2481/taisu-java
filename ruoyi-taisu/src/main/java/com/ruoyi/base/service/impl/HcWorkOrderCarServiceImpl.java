package com.ruoyi.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.base.bo.HcWorkOrderCarForPermitBo;
import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.enums.HcUserType;
import com.ruoyi.base.enums.SexType;
import com.ruoyi.base.enums.VndIdNoType;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.service.IHcCarService;
import com.ruoyi.base.service.IHcUserService;
import com.ruoyi.base.service.IHcWorkOrderCarService;
import com.ruoyi.base.service.IHcWorkOrderUserService;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.utils.ZJFConverter;
import com.ruoyi.base.vo.CarVO;
import com.ruoyi.base.vo.PersonVO;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.BatisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.slj.SljSyncUtils;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 危化施工單車輛Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcWorkOrderCarServiceImpl implements IHcWorkOrderCarService {
    private static final Logger log = LoggerFactory.getLogger(HcWorkOrderCarServiceImpl.class);

    @Autowired
    private HcWorkOrderCarMapper hcWorkOrderCarMapper;
    @Autowired
    private HcCarMapper hcCarMapper;
    @Autowired
    private IHcCarService hcCarService;
    @Autowired
    private IHcUserService hcUserService;
    @Autowired
    private PlateSendService plateSendService;
    @Autowired
    private PersonSendService personSendService;
    @Autowired
    private HcWorkOrderUserMapper hcWorkOrderUserMapper;
    @Autowired
    private HikEquipmentMapper hikEquipmentMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private IHcWorkOrderUserService hcWorkOrderUserService;
    @Autowired
    private HcWorkOrderMapper hcWorkOrderMapper;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private ThreadPoolConfig pool;

    @Value("${server.port}")
    private String port;
    /**
     * 厂区编号
     */
    @Value("${factoryCode}")
    private String factoryCode;

    /**
     * 查询危化施工單車輛
     *
     * @param id 危化施工單車輛主键
     * @return 危化施工單車輛
     */
    @Override
    public HcWorkOrderCar selectHcWorkOrderCarById(Long id) {
        return hcWorkOrderCarMapper.selectHcWorkOrderCarById(id);
    }

    /**
     * 查询危化施工單車輛列表
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 危化施工單車輛
     */
    @Override
    public List<HcWorkOrderCar> selectHcWorkOrderCarList(HcWorkOrderCar hcWorkOrderCar) {
        return hcWorkOrderCarMapper.selectHcWorkOrderCarList(hcWorkOrderCar);
    }

    /**
     * 新增危化施工單車輛
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 结果
     */
    @Override
    @Transactional
    public int insertHcWorkOrderCar(HcWorkOrderCar hcWorkOrderCar) {
        int result = 0;

        this.redun(hcWorkOrderCar, "add");
        hcWorkOrderCar.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
        hcWorkOrderCar.setCreateTime(DateUtils.getNowDate());
        result += hcWorkOrderCarMapper.insertHcWorkOrderCar(hcWorkOrderCar);

        return result;
    }

    /**
     * 修改危化施工單車輛
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 结果
     */
    @Override
    @Transactional
    public int updateHcWorkOrderCar(HcWorkOrderCar hcWorkOrderCar) {
        int result = 0;

        this.redun(hcWorkOrderCar, "edit");

        //同時保存基礎表
        hcCarService.buildCar(hcWorkOrderCar);
        hcWorkOrderCar.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
        hcWorkOrderCar.setUpdateTime(DateUtils.getNowDate());
        result += hcWorkOrderCarMapper.updateHcWorkOrderCar(hcWorkOrderCar);

        return result;
    }

    private void redun(HcWorkOrderCar entity, String oper) {
        entity.setFacDorNm(factoryCode);
        SysDept plant = sysDeptMapper.selectDeptByDeptNo(factoryCode);
        entity.setFacNm(plant.getDeptName());

        if ("add".equals(oper)) {
            //默認設定一個工單號
            entity.setVhNo("HC000001");
            //一道門入廠時間，默認為數據增加時間
            try {
                //轉為到毫秒的，否則數據查詢會錯誤。
                Date ipltTm=DateUtils.parseDate(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",DateUtils.getNowDate())
                        ,"yyyy-MM-dd HH:mm:ss");
                entity.setIpltTm(ipltTm);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            entity.setIssued("N");
        }


    }

    /**
     * 批量删除危化施工單車輛
     *
     * @param ids 需要删除的危化施工單車輛主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderCarByIds(Long[] ids) {
        return hcWorkOrderCarMapper.deleteHcWorkOrderCarByIds(ids);
    }

    /**
     * 删除危化施工單車輛信息
     *
     * @param id 危化施工單車輛主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderCarById(Long id) {
        return hcWorkOrderCarMapper.deleteHcWorkOrderCarById(id);
    }

    @Override
    public List<HcWorkOrderCar> selectHcWorkOrderCarByIdNo(String idNo) {
        if (!StringUtils.isEmpty(idNo)) {
            idNo = idNo.trim();
            idNo = ZJFConverter.SimToTra(idNo);
        }
        return hcWorkOrderCarMapper.selectHcWorkOrderCarByIdNo(idNo);
    }

    /**
     * 從ERP獲取數據
     *
     * @param boList
     * @return
     */
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant, String addOrUpdate) {
        int result = 0;
        List<HcWorkOrderCar> addList = new ArrayList<>();
        List<HcWorkOrderCar> updateList = new ArrayList<>();

        if (boList.size() == 0) {
            return result;
        }

        //只導入車輛
        List<V0NBRKX5Bo> tempBoList = new ArrayList<>();
        for (V0NBRKX5Bo bo : boList) {
            VndIdNoType vndIdNoType = hcUserService.checkIdNoType(bo.getIdno());
            if (!VndIdNoType.MAN.equals(vndIdNoType)) {
                tempBoList.add(bo);
            }
        }
        boList = tempBoList;

        //開始導入
        List<String> vhNoList = boList.stream().map(V0NBRKX5Bo::getVhno).distinct().collect(Collectors.toList());
        Map<String, HcWorkOrderCar> entityMap = this.getEntityMap(vhNoList);

        List<HcWorkOrderCar> newList = new ArrayList<>();
        HcWorkOrderCar vo = null;
        for (V0NBRKX5Bo item : boList) {
            vo = item.toHcWorkOrderCar();
            vo.setFacDorNm(plant.getDeptNo());
            vo.setFacNm(plant.getDeptName());
            vo.setUpdateTime(DateUtils.getNowDate());
            newList.add(vo);
        }

        //根據工單+車牌+入廠時間導入數據，因為存在同工單、同車牌、不同入廠時間的數據
        List<HcWorkOrderCar> oldList = entityMap.values().stream().collect(Collectors.toList());
        List<String> keyFieldNames = Arrays.asList("vhNo", "idNo", "ipltTm");
        String primaryFieldName = "id";
        List<String> syncFieldNames = Arrays.asList("vhNo", "nm", "idNo", "ipltLic", "dpId", "ipltCarNo", "dataFrom", "ipltTm", "opltTm");

        Map<String, List<HcWorkOrderCar>> oprMap = SljSyncUtils.getOprMap(newList, oldList, keyFieldNames, primaryFieldName, syncFieldNames,
                null,
                x -> addDealFunc(x),
                null);
        if (addOrUpdate.equals("Y")) {
            addList = oprMap.get("addList");
        } else if (addOrUpdate.equals("N")) {
            updateList = oprMap.get("updateList");
        }

        log.info("危化ERP同步的addList->>>>>>>" + addList.toString());
        log.info("危化ERP同步的updateList->>>>>>>" + updateList.toString());


        //批量新增
        if (addList.size() > 0) {
            List<List<HcWorkOrderCar>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcWorkOrderCar> ls : lists) {
                result += hcWorkOrderCarMapper.batchInsertHcWorkOrderCar(ls);
            }
        }
        //批量修改
        if (updateList.size() > 0) {
            List<List<HcWorkOrderCar>> lists = BatisUtils.splitList(updateList, 40);
            for (List<HcWorkOrderCar> ls : lists) {
                result += hcWorkOrderCarMapper.batchUpdateHcWorkOrderCar(ls);
            }
        }

        return result;

    }

    /**
     * 新增時的操作
     *
     * @param entity
     */
    private void addDealFunc(HcWorkOrderCar entity) {
        entity.setIssued("N");
    }

    /**
     * 獲取實體map
     *
     * @return
     */
    @Override
    public Map<String, HcWorkOrderCar> getEntityMap(List<String> vhNoList) {
        Map<String, HcWorkOrderCar> result = new HashMap<>();
        List<HcWorkOrderCar> list = new ArrayList<>();
        if (vhNoList == null) {
            list = hcWorkOrderCarMapper.selectHcWorkOrderCarList(null);
        } else {
            if (vhNoList.size() > 0) {
                list = hcWorkOrderCarMapper.selectHcWorkOrderCarListByVhNos(vhNoList.toArray(new String[0]));
            }
        }

        for (HcWorkOrderCar item : list) {
            result.put(item.getVhNo() + "_" + item.getIdNo() + "_" + item.getIpltTm(), item);
        }
        return result;
    }

    /**
     * 下發
     *
     * @param hcWorkOrderCarForPermitBo
     * @return
     */
    @Override
    @Transactional
    public int permit(HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo) {
        //重新获取车辆信息
        List<Long> hcCarIdList = hcWorkOrderCarForPermitBo.getHcCarList().stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        List<HcWorkOrderCar> hcCarList = hcWorkOrderCarMapper.selectHcWorkOrderCarListByIds(hcCarIdList.toArray(new Long[0]));
        hcWorkOrderCarForPermitBo.setHcCarList(hcCarList);

        //判斷能否下發
        if (!canPermit(hcWorkOrderCarForPermitBo)) {
            return 0;
        }

        pool.threadPoolTaskExecutor().execute(() ->
                this.permitSync(hcWorkOrderCarForPermitBo)
        );

        return 1;
    }

    //能否下發
    public boolean canPermit(HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo) {
        boolean canPermit = true;

        //获取是否免填押運員信息
        String brushlessDriver = sysConfigService.selectConfigByKey("sys.hc.ctrl_BrushlessDriver");
        String brushlessEscort = sysConfigService.selectConfigByKey("sys.hc.ctrl_BrushlessEscort");

        List<HcWorkOrderCar> hcCarList = hcWorkOrderCarForPermitBo.getHcCarList();
        List<String> vhNoList = hcCarList.stream().map(x -> x.getVhNo())
                .distinct().collect(Collectors.toList());
        //獲取施工單號下所有人員
        List<HcWorkOrderUser> hcWorkOrderUserListAll =
                hcWorkOrderUserMapper.selectHcWorkOrderUserListByVhNos(vhNoList.toArray(new String[0]));

        for (int i = 0; i < hcCarList.size(); i++) {
            HcWorkOrderCar car = hcCarList.get(0);
            long driverCnt = hcWorkOrderUserListAll.stream()
                    .filter(x -> Objects.equals(car.getVhNo(), x.getVhNo())
                            && Objects.equals(car.getIdNo(), x.getLicense())
                            && Objects.equals(car.getIpltTm(), x.getIpltTm())
                            && Objects.equals(HcUserType.DRIVER.getCode(), x.getUserType()))
                    .count();
            long passenderCnt = hcWorkOrderUserListAll.stream()
                    .filter(x -> Objects.equals(car.getVhNo(), x.getVhNo())
                            && Objects.equals(car.getIdNo(), x.getLicense())
                            && Objects.equals(car.getIpltTm(), x.getIpltTm())
                            && Objects.equals(HcUserType.PASSENDER.getCode(), x.getUserType()))
                    .count();

            //不免填司機信息
            if ("False".equals(brushlessDriver)) {
                if (driverCnt != 1) {
                    canPermit = false;
                    throw new ServiceException("車輛【" + car.getIdNo() + "】缺失司機，請檢查");
                }
            }

            //不免填押運員信息
            if ("False".equals(brushlessEscort)) {
                if (passenderCnt != 1) {
                    canPermit = false;
                    throw new ServiceException("車輛【" + car.getIdNo() + "】缺失押運員，請檢查");
                }
            }
        }

        return canPermit;
    }

    public int permitSync(HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo) {
        //獲取危化設備
        List<HikEquipment> hikEquipmentList = hikEquipmentMapper.selectHikEquipmentListHc(null);

        //車牌下發
        //permitCar(hcWorkOrderCarForPermitBo, hikEquipmentList);
        //人員下發
        permitUser(hcWorkOrderCarForPermitBo, hikEquipmentList);

        return 1;
    }

    /**
     * 車牌下發
     *
     * @param hcWorkOrderCarForPermitBo
     */
    public void permitCar(HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo, List<HikEquipment> hikEquipmentList) {
        List<HcWorkOrderCar> hcWorkOrderCarList = hcWorkOrderCarForPermitBo.getHcCarList();
        List<String> licenseList = hcWorkOrderCarList.stream().map(HcWorkOrderCar::getIdNo)
                .distinct().collect(Collectors.toList());


        //解綁車牌
        this.unBindCar(licenseList);

        //下發車牌
        this.bindCar(hcWorkOrderCarList, hikEquipmentList);
    }


    public void unBindCar(List<String> licenses) {
        for (String license : licenses) {
            plateSendService.unbindPlateNos(license);
        }
    }

    public void bindCar(List<HcWorkOrderCar> hcWorkOrderCarList, List<HikEquipment> hikEquipmentList) {
        for (HcWorkOrderCar item : hcWorkOrderCarList) {
            CarVO carVO = hcWorkOrderCarToCarVO(item, hikEquipmentList);
            plateSendService.downSendCarMsg(carVO);
        }
    }

    private CarVO hcWorkOrderCarToCarVO(HcWorkOrderCar item, List<HikEquipment> hikEquipmentList) {
        CarVO carVO = new CarVO();
        //导入判断车辆类型
        carVO.setCarType(2);//0=員工，1=普通廠商，2=危化
        carVO.setCarSn(item.getVhNo());
        carVO.setCarNumber(item.getIdNo());
        //权限
        List<String> indexNos = hikEquipmentList.stream().map(HikEquipment::getIndexCode)
                .collect(Collectors.toList());
        carVO.setAuths(indexNos);
        carVO.setAuthIsAll(false);
        return carVO;
    }

    /**
     * 人員下發
     *
     * @param hcWorkOrderCarForPermitBo
     */
    public void permitUser(HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo, List<HikEquipment> hikEquipmentList) {
        List<HcWorkOrderCar> hcWorkOrderCarList = hcWorkOrderCarForPermitBo.getHcCarList();
        List<String> vhNoList = hcWorkOrderCarList.stream().map(HcWorkOrderCar::getVhNo)
                .distinct().collect(Collectors.toList());
        //獲取施工單號下所有人員
        List<HcWorkOrderUser> hcWorkOrderUserListAll =
                hcWorkOrderUserMapper.selectHcWorkOrderUserListByVhNos(vhNoList.toArray(new String[0]));
        for (HcWorkOrderCar itemCar : hcWorkOrderCarList) {
            //篩選出人
            List<HcWorkOrderUser> userList = hcWorkOrderUserListAll.stream()
                    .filter(x -> itemCar.getVhNo().equals(x.getVhNo())
                            && itemCar.getIdNo().equals(x.getLicense())
                            && itemCar.getIpltTm().equals(x.getIpltTm()))
                    .collect(Collectors.toList());
            String issued = "N";
            if (userList.size() > 0) {
                issued = "Y";
                for (HcWorkOrderUser itemUser : userList) {
                    if (StringUtils.isEmpty(itemUser.getFace())) {
                        issued = "N";
                        continue;
                    }
                    //身份证号、照片都有了，组装信息，然后下发
                    PersonVO personVO = hcWorkOrderUsertoPersonVO(itemUser, hikEquipmentList);
                    log.info("saveToHik的personVO：" + personVO.toString());
                    Integer resultCode = personSendService.saveToHik(personVO);
                    if (200 != resultCode) {
                        issued = "N";
                    }
                }
            }
            //修改車輛下發狀態
            itemCar.setIssued(issued);
            this.updateIssued(itemCar);
        }
    }

    private PersonVO hcWorkOrderUsertoPersonVO(HcWorkOrderUser itemUser, List<HikEquipment> hikEquipmentList) {
        PersonVO requestVo = new PersonVO();
        requestVo.setAuthIsAll(false);
        //先拿到工单，工单里面有ip,再根据ip拿plc，plc下面有人脸设备
        List<String> indexNos = hikEquipmentList.stream().map(HikEquipment::getIndexCode)
                .collect(Collectors.toList());
        requestVo.setDeviceNos(indexNos);
        String appl = !StringUtils.isEmpty(RuoYiConfig.getAppl()) ? ("/" + RuoYiConfig.getAppl()) : "";
        log.info("appl->>>>>>>" + appl);
        String faceBase64 = HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port + appl + itemUser.getFace());
        requestVo.setFaceBase64Str(faceBase64);
        requestVo.setJobNo(null);
        requestVo.setOrderSn(itemUser.getVhNo());
        requestVo.setPersonId(itemUser.getIdNo());
        requestVo.setPersonName(itemUser.getNm());
        requestVo.setPersonType(2);
        requestVo.setPhoneNo(itemUser.getPhone());
        return requestVo;

    }


    /**
     * 設置是否下發成功
     *
     * @param hcWorkOrderCar
     * @return
     */
    public int updateIssued(HcWorkOrderCar hcWorkOrderCar) {
        HcWorkOrderCar param = new HcWorkOrderCar();
        param.setId(hcWorkOrderCar.getId());
        param.setIssued(hcWorkOrderCar.getIssued());
        return hcWorkOrderCarMapper.updateHcWorkOrderCar(param);
    }

    /**
     * 從HcCar獲取數據
     *
     * @return
     */
    public void syncFromHcCar() {
        List<HcWorkOrderCar> boList = new ArrayList<>();
        int pageNum = 1;
        int pageSize = 1000;
        String orderBy = "";
        do {
            PageHelper.startPage(pageNum++, pageSize, orderBy).setReasonable(true);
            boList = hcWorkOrderCarMapper.selectHcWorkOrderCarList(new HcWorkOrderCar());
            this.syncFromHcCarByPage(boList);
        } while (boList.size() >= pageSize);

    }

    /**
     * 從HcCar獲取數據
     *
     * @return
     */
    public int syncFromHcCarByPage(List<HcWorkOrderCar> boList) {
        int result = 0;
        List<HcWorkOrderCar> updateList = new ArrayList<>();

        List<HcWorkOrderCar> oldList = boList;
        HcWorkOrderCar entity = null;

        //獲取基礎表
        List<String> idNos = oldList.stream().map(HcWorkOrderCar::getIdNo).collect(Collectors.toList());
        List<HcCar> hcCarList = new ArrayList<>();
        if (idNos.size() > 0) {
            hcCarList = hcCarMapper.selectHcCarListByIdNos(idNos.toArray(new String[0]));
        }
        List<HcWorkOrderCar> newList = new ArrayList<>();
        for (HcWorkOrderCar item : oldList) {
            HcCar hcCarVo = null;
            List<HcCar> tempHcCarList = hcCarList.stream()
                    .filter(x -> StringUtils.nvl(x.getIdNo(), "").equals(StringUtils.nvl(item.getIdNo(), "")))
                    .collect(Collectors.toList());
            if (tempHcCarList.size() > 0) {
                hcCarVo = tempHcCarList.get(0);
            }
            if (hcCarVo != null) {
                entity = hcCarVo.toHcWorkOrderCar();
                newList.add(entity);
            }
        }
        List<String> keyFieldNames = Arrays.asList("idNo");
        String primaryFieldName = "id";
        List<String> syncFieldNames = Arrays.asList("nm", "idNo", "carPhoto", "carColor", "carType", "carTypeName",
                "vehicleLic", "hcTransportCertNo", "emisStandard", "EmisStandardName", "EnvSign");

        Map<String, List<HcWorkOrderCar>> oprMap = SljSyncUtils.getOprMap(newList, oldList, keyFieldNames, primaryFieldName, syncFieldNames,
                null,
                null,
                null);
        //addList = oprMap.get("addList");
        updateList = oprMap.get("updateList");

        //批量修改
        if (updateList.size() > 0) {
            List<List<HcWorkOrderCar>> lists = BatisUtils.splitList(updateList, 40);
            for (List<HcWorkOrderCar> ls : lists) {
                result += hcWorkOrderCarMapper.batchUpdateHcWorkOrderCar(ls);
            }
        }

        return result;


    }

    /**
     * 查询危化施工單車輛
     *
     * @return 危化施工單車輛
     */
    @Override
    public List<HcWorkOrderCar> selectHcWorkOrderCarListByVhNoIdNo(String vhNo, String idNo) {
        return hcWorkOrderCarMapper.selectHcWorkOrderCarListByVhNoIdNo(vhNo, idNo);
    }

    /**
     * 修改危化施工單車輛
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveHcWorkOrderCarForH5(HcWorkOrderCar hcWorkOrderCar) {
        int result = 0;

        if (StringUtils.isNotEmpty(hcWorkOrderCar.getIdNo())) {
            String idNo= BatisUtils.dealLicense(hcWorkOrderCar.getIdNo());
            hcWorkOrderCar.setIdNo(idNo);
        }

        if (StringUtils.isNotEmpty(hcWorkOrderCar.getCarColor())) {
            hcWorkOrderCar.setCarColor(ZJFConverter.SimToTra(hcWorkOrderCar.getCarColor()));
        }

        //獲取司機信息
        if (hcWorkOrderCar.getHcWorkOrderUsers() != null) {
            for (HcWorkOrderUser hcWorkOrderUser : hcWorkOrderCar.getHcWorkOrderUsers()) {
                if (hcWorkOrderUser == null) {
                    continue;
                }
                //如果沒有身份證、工單或者車牌，則不保存
                if (StringUtils.isEmpty(hcWorkOrderUser.getIdNo())
                ) {
                    continue;
                }
                if (Objects.equals(hcWorkOrderUser.getUserType(), HcUserType.DRIVER.getCode())) {
                    hcWorkOrderCar.setDirverIdNo(hcWorkOrderUser.getIdNo());
                    hcWorkOrderCar.setDriverNm(hcWorkOrderUser.getNm());
                }

            }
        }

        result += this.saveHcWorkOrderCar(hcWorkOrderCar);
        if (hcWorkOrderCar.getHcWorkOrderUsers() != null) {
            for (HcWorkOrderUser hcWorkOrderUser : hcWorkOrderCar.getHcWorkOrderUsers()) {
                if (hcWorkOrderUser == null) {
                    continue;
                }
                //如果沒有身份證、工單或者車牌，則不保存
                if (StringUtils.isEmpty(hcWorkOrderUser.getIdNo())
                ) {
                    continue;
                }

                //防止未提交車牌和一道門入廠時間，直接從危化車輛獲取一次
                hcWorkOrderUser.setVhNo(hcWorkOrderCar.getVhNo());
                hcWorkOrderUser.setIpltTm(hcWorkOrderCar.getIpltTm());
                hcWorkOrderUser.setLicense(hcWorkOrderCar.getIdNo());

                //主鍵賦值
                hcWorkOrderUser.setLicense(hcWorkOrderCar.getIdNo());
                hcWorkOrderUser.setIpltTm(hcWorkOrderCar.getIpltTm());

                if (StringUtils.isNotEmpty(hcWorkOrderUser.getNm())) {
                    hcWorkOrderUser.setNm(ZJFConverter.SimToTra(hcWorkOrderUser.getNm()));
                }
                if (StringUtils.isNotEmpty(hcWorkOrderUser.getAddress())) {
                    hcWorkOrderUser.setAddress(ZJFConverter.SimToTra(hcWorkOrderUser.getAddress()));
                }

                //從身份證獲取生日和性別
                hcWorkOrderUser.setBirthday(BatisUtils.getBirthFromIdNo(hcWorkOrderUser.getIdNo()));
                hcWorkOrderUser.setSex(BatisUtils.getSexFromIdNo(hcWorkOrderUser.getIdNo()));


                //获取老数据
                if (hcWorkOrderUser.getId() == null) {
                    result += hcWorkOrderUserService.insertHcWorkOrderUser(hcWorkOrderUser);
                } else {
                    result += hcWorkOrderUserService.updateHcWorkOrderUser(hcWorkOrderUser);
                }
            }
        }
        if (hcWorkOrderCar.getHcWorkOrderUsers() != null && hcWorkOrderCar.getHcWorkOrderUsers().size() > 0) {
            HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo = new HcWorkOrderCarForPermitBo();
            List<HcWorkOrderCar> hcCarList = new ArrayList<>();
            hcCarList.add(hcWorkOrderCar);
            hcWorkOrderCarForPermitBo.setHcCarList(hcCarList);
            permit(hcWorkOrderCarForPermitBo);
        }
        return result;
    }

    /**
     * 危化施工單押运员
     *
     * @param hcWorkOrderUser 危化施工單押运员
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateHcWorkOrderUserForH5(HcWorkOrderUser hcWorkOrderUser) {
        int result = 0;

        if (hcWorkOrderUser != null) {

            //如果沒有身份證、工單或者車牌，則不保存
            if (StringUtils.isEmpty(hcWorkOrderUser.getIdNo()) || StringUtils.isEmpty(hcWorkOrderUser.getVhNo())
                    || StringUtils.isEmpty(hcWorkOrderUser.getLicense())
            ) {
                throw new ServiceException("請輸入身份證 工單 或者車牌");
            }

            if (StringUtils.isNotEmpty(hcWorkOrderUser.getNm())) {
                hcWorkOrderUser.setNm(ZJFConverter.SimToTra(hcWorkOrderUser.getNm()));
            }
            if (StringUtils.isNotEmpty(hcWorkOrderUser.getAddress())) {
                hcWorkOrderUser.setAddress(ZJFConverter.SimToTra(hcWorkOrderUser.getAddress()));
            }

            //获取老数据
            if (hcWorkOrderUser.getId() == null) {
                result += hcWorkOrderUserService.insertHcWorkOrderUser(hcWorkOrderUser);
            } else {
                result += hcWorkOrderUserService.updateHcWorkOrderUser(hcWorkOrderUser);
            }
        }
        if (result > 0) {
            HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo = new HcWorkOrderCarForPermitBo();
            List<HcWorkOrderCar> hcCarList = new ArrayList<>();
            HcWorkOrderCar hcWorkOrderCar = hcWorkOrderCarMapper.selectHcWorkOrderCarByVhNoIdNo(hcWorkOrderUser.getVhNo(), hcWorkOrderUser.getLicense(), hcWorkOrderUser.getIpltTm());
            hcCarList.add(hcWorkOrderCar);
            hcWorkOrderCarForPermitBo.setHcCarList(hcCarList);
            permit(hcWorkOrderCarForPermitBo);
        }
        return result;
    }

    @Override
    public JSONObject hcQueryDetails(Long id) {
        //根據車牌號查詢
        HcWorkOrderCar hcWorkOrderCar = hcWorkOrderCarMapper.selectHcWorkOrderCarById(id);
        List<HcWorkOrderUser> hcWorkOrderUsers = new ArrayList<>();
        HcWorkOrderUser hcWorkOrderUser = new HcWorkOrderUser();
        if (hcWorkOrderCar != null && StringUtils.isNotEmpty(hcWorkOrderCar.getVhNo())
                && StringUtils.isNotEmpty(hcWorkOrderCar.getIdNo()) && hcWorkOrderCar.getIpltTm() != null) {
            hcWorkOrderUser.setVhNo(hcWorkOrderCar.getVhNo());
            hcWorkOrderUser.setLicense(hcWorkOrderCar.getIdNo());
            hcWorkOrderUser.setIpltTm(hcWorkOrderCar.getIpltTm());
            log.info("hcQueryDetails-hcWorkOrderUser->>>>>>"+hcWorkOrderUser.toString());
            hcWorkOrderUsers = hcWorkOrderUserService.selectHcWorkOrderUserList(hcWorkOrderUser);
            log.info("hcQueryDetails-hcWorkOrderUsers-1->>>>>>"+hcWorkOrderUsers.toString());
            for (HcWorkOrderUser user : hcWorkOrderUsers) {
                if (user.getSex() == null) {
                    user.setSex(3L);
                }
                user.setSexName(SexType.getNameByCode(user.getSex()));
            }
        }

        JSONObject back = new JSONObject();
        back.put("carDetail", hcWorkOrderCar);
        back.put("userDetail", hcWorkOrderUsers);

        return back;
    }

    /**
     * 保存車輛數據
     *
     * @param hcWorkOrderCar
     * @return
     */
    public int saveHcWorkOrderCar(HcWorkOrderCar hcWorkOrderCar) {
        int result = 0;
        //查找當前車牌有效的數據
        List<HcWorkOrderCar> hcCars = hcWorkOrderCarMapper.selectHcWorkOrderCarByIdNoEnable(hcWorkOrderCar.getIdNo(), DateUtils.getToDayBeginTime(), DateUtils.getToDayEndTime());
        if (hcCars.size() > 0) {
            hcWorkOrderCar.setId(hcCars.get(0).getId());
            result += this.updateHcWorkOrderCar(hcWorkOrderCar);
        } else {
            result += this.insertHcWorkOrderCar(hcWorkOrderCar);
        }

        return result;
    }

    /**
     * 查询危化施工單車輛
     *
     * @param idNo 危化施工單車牌號
     * @return 危化施工單車輛
     */
    public JSONObject hcQueryDetailByIdNo(String idNo) {
        idNo= BatisUtils.dealLicense(idNo);

        List<HcWorkOrderCar> hcCars = hcWorkOrderCarMapper.selectHcWorkOrderCarByIdNoEnable(idNo, DateUtils.getToDayBeginTime(), DateUtils.getToDayEndTime());
        if (hcCars.size() > 0) {
            return this.hcQueryDetails(hcCars.get(0).getId());
        } else {
            JSONObject back = new JSONObject();
            back.put("carDetail", null);
            back.put("userDetail", null);
            return back;
        }
    }


}

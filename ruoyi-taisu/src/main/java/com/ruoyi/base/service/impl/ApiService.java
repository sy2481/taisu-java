package com.ruoyi.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.PersonMsgBO;
import com.ruoyi.base.bo.TcInOutLog;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.interact.LocationCardSendService;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.interact.UserJurisdiction;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.service.IPersonBindService;
import com.ruoyi.base.service.SafetycarService;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.utils.OldIpMap;
import com.ruoyi.base.utils.PlcRedisUtils;
import com.ruoyi.base.utils.ZJFConverter;
import com.ruoyi.base.vo.CardBindVO;
import com.ruoyi.base.vo.PersonVO;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiva   2022/3/7 17:38
 */

@Service
public class ApiService {

    private  final Logger logger = LoggerFactory.getLogger(ManFactoryServiceImpl.class);

    @Value("${server.port}")
    private String port;

    @Value("${factoryCode}")
    private String factoryCode;

    @Autowired
    private LocationCardSendService locationCardSendService;
    @Autowired
    private PersonSendService personSendService;

    @Autowired
    private ManFactoryMapper factoryMapper;
    @Autowired
    private ManWorkMapper workMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private LocateCardMapper locateCardMapper;
    @Autowired
    private CardRecordMapper cardRecordMapper;
    @Autowired
    private InOutLogMapper inOutLogMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private PlcRedisUtils plcRedisUtils;
    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IPersonBindService personBindService;

    @Autowired
    private UserJurisdiction userJurisdiction;

    @Autowired
    private ISysPostService postService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private PlcEquipmentMapper plcEquipmentMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SafetycarService safetycarService;

    @Autowired
    private SysUserMapper sysUserMapper;

    /* 人员查询相关方法 *********************************************************************************************************************/


    /**
     * 根據車卡，查詢人員信息
     * 先查询员工表，再查询厂商；
     */
    public List<PersonMsgBO> queryPersonByCarCard(String carCardNo) throws Exception {
        List<PersonMsgBO> result = new ArrayList<>();
        List<SysUser> userList = userMapper.getByCommonParams(null, null, carCardNo, null);
        for (SysUser item : userList) {
            PersonMsgBO personMsgBO = new PersonMsgBO();
            personMsgBO.setPersonType("0");
            copyFromUser2Person(item, personMsgBO);
            result.add(personMsgBO);
        }
        List<ManFactory> factoryList = factoryMapper.getByCommonParams(null, null, carCardNo, null);
        for (ManFactory item : factoryList) {
            PersonMsgBO personMsgBO = new PersonMsgBO();
            personMsgBO.setPersonType("1");
            copyFromFactory2Person(item, personMsgBO);
            result.add(personMsgBO);
        }
        return result;
    }

    /**
     * 根據車牌號，查詢人員信息
     * 先查询员工表，没有的话，再查询厂商
     */
    public List<PersonMsgBO> queryPersonByPlateNo(String plateNo) {
        List<PersonMsgBO> result = new ArrayList<>();
        List<SysUser> userList = userMapper.getByCommonParams(null, null, null, plateNo);
        for (SysUser item : userList) {
            PersonMsgBO personMsgBO = new PersonMsgBO();
            personMsgBO.setPersonType("0");
            copyFromUser2Person(item, personMsgBO);
            result.add(personMsgBO);
        }
        List<ManFactory> factoryList = factoryMapper.getByCommonParams(null, null, null, plateNo);
        for (ManFactory item : factoryList) {
            PersonMsgBO personMsgBO = new PersonMsgBO();
            personMsgBO.setPersonType("1");
            copyFromFactory2Person(item, personMsgBO);
            result.add(personMsgBO);
        }
        return result;
    }

    /**
     * 根据⾝份证号查询⼈员信息
     * 海康可以拿到⾝份证，根据⾝份证，根据⾝份证号查询⼈员信息
     */
    public PersonMsgBO queryPersonByIdcardNo(String idCard) {
        PersonMsgBO result = new PersonMsgBO();
        List<SysUser> userList = userMapper.getByCommonParams(idCard, null, null, null);
        if (userList.size() > 0) {
            //存在，开始整理数据
            result.setPersonType("0");
            SysUser sysUser = userList.get(0);
            copyFromUser2Person(sysUser, result);
            return result;
        }
        List<ManFactory> factoryList = factoryMapper.getByCommonParams(idCard, null, null, null);
        if (factoryList.size() > 0) {
            //存在，开始整理数据
            result.setPersonType("1");
            ManFactory factory = factoryList.get(0);
            copyFromFactory2Person(factory, result);
            return result;
        }
        return null;
    }

    /**
     * 根据定位卡编号查询⼈员信息
     * 根据定位卡编号，返回⼈员信息
     */
    public PersonMsgBO queryPersonByLocationCardNo(String locationCardNo) {
        PersonMsgBO result = new PersonMsgBO();
        List<SysUser> userList = userMapper.getByCommonParams(null, locationCardNo, null, null);
        if (userList.size() > 0) {
            //存在，开始整理数据
            result.setPersonType("0");
            SysUser sysUser = userList.get(0);
            copyFromUser2Person(sysUser, result);
            return result;
        }
        List<ManFactory> factoryList = factoryMapper.getByCommonParams(null, locationCardNo, null, null);
        if (factoryList.size() > 0) {
            //存在，开始整理数据
            result.setPersonType("1");
            ManFactory factory = factoryList.get(0);
            copyFromFactory2Person(factory, result);
            return result;
        }
        return null;
    }

    // 从员工复制
    private void copyFromUser2Person(SysUser user, PersonMsgBO person) {
        person.setIdCard(user.getIdCard());
        person.setName(user.getNickName());
        person.setMobileNo(user.getPhonenumber());
        person.setFace(user.getFace());
        person.setLocationCardNo(user.getPositionCardNo());
        person.setPlateNo(user.getCarId());
        person.setCarCardNo(user.getCarCard());
    }

    // 从厂商复制
    private void copyFromFactory2Person(ManFactory factory, PersonMsgBO person) {
        person.setIdCard(factory.getIdCard());
        person.setName(factory.getName());
        person.setMobileNo(factory.getPhone());
        person.setFace(factory.getFace());
        person.setLocationCardNo(factory.getLocationCard());
        person.setPlateNo(factory.getLcensePlate());
        person.setCarCardNo(factory.getCarCardNo());
    }

    /* 定位卡绑定相关方法 *********************************************************************************************************************/

    //1.查询 人员 和 定位卡 是否都存在
    //2.如果 人员 或 定位卡，其中有一个已经被绑定了，那就直接返回错误
    //3.如果都没有被绑定，那就把人员 和 定位卡，绑定起来，互相插入字段
    //4.插入日志
    @Transactional(readOnly = false)
    public void bind(String idCardNo, String locationCardNo) throws Exception {
        // 第一步，
        LocateCard locateCard = locateCardMapper.getByLocationCardNo(locationCardNo);
        if (locateCard == null) {
            throw new Exception("定位卡不存在！");
        }
        if ("1".equals(locateCard.getCardLocateStatus())) {
            throw new Exception("定位卡已被绑定！");
        }
        //接着开始查询人员信息是否存在
        List<SysUser> userList = userMapper.getByCommonParams(idCardNo, null, null, null);
        if (userList.size() > 0) {
            // 查到了人员，直接可以下一步了
            SysUser bindUser = userList.get(0);
            // 拿到员工用户绑定的定位卡编号
            String bindLocationCardNo = getBindOptFromUser(bindUser, null);
            // 如果定位卡编号存在，表示已经绑定了，不能绑定2张
            if (!StringUtils.isBlank(bindLocationCardNo)) {
                throw new Exception("人员已绑定其他定位卡");
            }
            //验证通过，可以绑定了
            bindUser(bindUser, locateCard);
            addUserPersonBind(bindUser, locateCard);
        } else {
            List<ManFactory> factoryList = factoryMapper.getByCommonParams(idCardNo, null, null, null);
            if (factoryList.size() == 0) {
                throw new Exception("该身份证号人员不存在！");
            }
            //厂商人员是存在的，可以开始下一步了
            ManFactory bindFactory = factoryList.get(0);
            // 拿到厂商人员绑定的定位卡编号
            String bindLocationCardNo = getBindOptFromUser(null, bindFactory);
            // 如果定位卡编号存在，表示已经绑定了，不能绑定2张
            if (!StringUtils.isBlank(bindLocationCardNo)) {
                throw new Exception("人员已绑定其他定位卡");
            }
            //验证通过，可以绑定了
            bindFactory(bindFactory, locateCard);
            addFactoryPersonBind(bindFactory, locateCard);
        }
    }


    //中间库存入员工
    private void addUserPersonBind(SysUser bindUser, LocateCard locateCard) {
        PersonBind personBind = new PersonBind();
        personBind.setPersonType(0);
        personBind.setSn(locateCard.getSnNum());
        personBind.setIdCard(bindUser.getIdCard());
        personBind.setName(ZJFConverter.SimToTra(bindUser.getNickName()));
        logger.info("ZJFConverter.SimToTrabindUse"+bindUser.getNickName());
        personBind.setMobile(bindUser.getPhonenumber());
        personBind.setSex(bindUser.getSex());
        personBind.setFace(bindUser.getFace());
        personBind.setAddress(bindUser.getFamilyAddress());
        personBind.setPlateNo(bindUser.getCarId());
        if (StringUtils.isNotBlank(bindUser.getFactoryId())) {
            personBind.setFactoryArea(bindUser.getFactoryId());
        }
        //员工编号
        personBind.setEmpNo(bindUser.getEmpNo());
        personBind.setJoinDate(bindUser.getJoinDate());
        if (StringUtils.isNotNull(bindUser.getDeptId())) {
            SysDept sysDept = deptService.selectDeptById(bindUser.getDeptId());
            if (StringUtils.isNotNull(sysDept)) {
                if (StringUtils.isNotBlank(sysDept.getDeptName())) {
                    personBind.setDeptName(sysDept.getDeptName());
                }

            }
        }
        personBind.setEventType(0);
        personBind.setUserPost("普通员工");

        List<PersonBind> personBinds = personBindService.selectByIdCard(bindUser.getIdCard(), 0);
        if (personBinds.size() > 0) {
            personBindService.updateByIdCard(personBind);
        } else {
            personBind.setCreateTime(new Date());
            personBindService.insertPersonBind(personBind);
        }

    }

    //中间库存厂商
    private void addFactoryPersonBind(ManFactory bindFactory, LocateCard locateCard) {
        PersonBind personBind = new PersonBind();
        personBind.setPersonType(1);
        personBind.setSn(locateCard.getSnNum());
        personBind.setIdCard(bindFactory.getIdCard());
        personBind.setName(ZJFConverter.TraToSim(bindFactory.getName()));
        personBind.setMobile(bindFactory.getPhone());
        if (bindFactory.getSex() != null) {
            personBind.setSex(bindFactory.getSex().toString());
        }
        personBind.setFace(bindFactory.getFace());
        personBind.setAddress(bindFactory.getAddress());
        personBind.setPlateNo(bindFactory.getLcensePlate());
        if (bindFactory.getDeptId() != null) {
            personBind.setFactoryArea(bindFactory.getDeptId().toString());
        }
        personBind.setWorkNo(bindFactory.getWorkNo());
        personBind.setProjectNo(bindFactory.getThisNumber());
        personBind.setFactoryName(ZJFConverter.TraToSim(bindFactory.getFactoryName()));
        personBind.setEventType(0);
        personBind.setUserPost("厂商人员");
        if (StringUtils.isNotBlank(bindFactory.getIpLtLic())) {
            personBind.setEmpNo(bindFactory.getIpLtLic());
        }
        List<PersonBind> personBinds = personBindService.selectByIdCard(bindFactory.getIdCard(), 1);
        if (personBinds.size() > 0) {
            personBindService.updateByIdCard(personBind);
        } else {
            personBind.setCreateTime(new Date());
            personBindService.insertPersonBind(personBind);
        }
    }


    // 获得用户的当前绑定定位卡编号
    // 两个参数只有一个生效
    private String getBindOptFromUser(SysUser user, ManFactory factory) {
        if (user != null) {
            return user.getPositionCardNo();
        }
        if (factory != null) {
            return factory.getLocationCard();
        }
        return null;
    }

    // 绑定人员和卡
    private void bindUser(SysUser user, LocateCard locateCard) {
        //向user表加入定位卡
        user.setSnNum(locateCard.getSnNum());
        user.setPositionCardNo(locateCard.getCardLocateNo());
        userMapper.updateUser(user);
        //向定位卡表插入用户，修改状态
        locateCard.setCardLocateStatus("1");
        locateCard.setCardLocateUse("0");
        locateCard.setBindUserCode(user.getEmpNo());
        locateCard.setBindUserName(user.getNickName());
        locateCard.setBindUserIdCard(user.getIdCard());
        locateCard.setLeadTime(new Date());
        locateCardMapper.updateLocateCard(locateCard);
        //插入日志
        cardRecordMapper.insertCardRecord(competeCardRecord("1",
                locateCard.getCardLocateId(), locateCard.getCardLocateNo(), "0", user.getNickName()));
    }

    // 绑定人员和卡
    private void bindFactory(ManFactory factory, LocateCard locateCard) {
        //表加入定位卡
        factory.setSnNum(locateCard.getSnNum());
        factory.setLocationCard(locateCard.getCardLocateNo());
        factoryMapper.updateManFactory(factory);
        //向定位卡表插入用户，修改状态
        locateCard.setCardLocateStatus("1");
        locateCard.setCardLocateUse("1");
        locateCard.setBindUserCode(factory.getWorkNo());
        locateCard.setBindUserName(factory.getName());
        locateCard.setBindUserIdCard(factory.getIdCard());
        locateCard.setLeadTime(new Date());
        locateCardMapper.updateLocateCard(locateCard);
        //插入日志
        cardRecordMapper.insertCardRecord(competeCardRecord("1",
                locateCard.getCardLocateId(), locateCard.getCardLocateNo(), "0", factory.getName()));
    }


    //1.查询 定位卡 是否存在
    //2.存在这查到对应的绑定人员
    //3.把人员 和 定位卡解绑，互相删除字段
    //4.插入日志
    @Transactional(readOnly = false)
    public int unbind(String locationCardNo) throws Exception {
        // 第一步，
        LocateCard locateCard = locateCardMapper.getByLocationCardNo(locationCardNo);
        if (locateCard == null) {
            throw new Exception("定位卡不存在！");
        }

        // 直接拿到对应绑定的人
        List<SysUser> userList = userMapper.getByCommonParams(null, locationCardNo, null, null);
        if (userList.size() > 0) {
            /**
             * 中间库操作
             */
            personBindService.relieveByIdCard(userList.get(0).getIdCard());
            unbindUser(userList.get(0), locateCard);
            return 1;
        }
        List<ManFactory> factoryList = factoryMapper.getByCommonParams(null, locationCardNo, null, null);
        if (factoryList.size() > 0) {
            personBindService.relieveByIdCard(factoryList.get(0).getIdCard());
            unbindFactory(factoryList.get(0), locateCard);
            return 1;
        }
        //最后添加保险步骤，防止定位卡已经被绑定，但是没有人员
        resetLocateCard(locateCard);
        locateCardMapper.updateLocateCard(locateCard);

        return 2;


    }

    // 绑定人员和卡
    private void unbindUser(SysUser user, LocateCard locateCard) {
        //删除字段
        user.setPositionCardNo("");
        user.setSnNum("");
        userMapper.updateUser(user);
        //重置清空定位卡
        resetLocateCard(locateCard);
        locateCardMapper.updateLocateCard(locateCard);
        //插入日志
        cardRecordMapper.insertCardRecord(competeCardRecord("1",
                locateCard.getCardLocateId(), locateCard.getCardLocateNo(), "1", user.getNickName()));
    }

    // 绑定人员和卡
    private void unbindFactory(ManFactory factory, LocateCard locateCard) {
        //表加入定位卡
        factory.setLocationCard("");
        factory.setSnNum("");
        factoryMapper.updateManFactory(factory);
        //重置清空定位卡
        resetLocateCard(locateCard);
        locateCard.setReturnName(factory.getName());
        locateCardMapper.updateLocateCard(locateCard);
        //插入日志
        cardRecordMapper.insertCardRecord(competeCardRecord("1",
                locateCard.getCardLocateId(), locateCard.getCardLocateNo(), "1", factory.getName()));
    }

    public void resetLocateCard(LocateCard locateCard) {
        locateCard.setCardLocateStatus("0");
        locateCard.setCardLocateUse(null);
        locateCard.setBindUserCode(null);
        locateCard.setBindUserName(null);
        locateCard.setBindUserIdCard(null);
        locateCard.setLeadTime(null);
        locateCard.setReturnTime(new Date());
    }


    /**
     * @param cardType    卡类型。0-车卡。1-定位卡
     * @param cardId      卡ID
     * @param cardNo      卡编号
     * @param operateType 操作方式(0=領用，1=歸還)
     * @param person      操作对象
     */
    public CardRecord competeCardRecord(String cardType, Long cardId, String cardNo, String operateType, String person) {
        CardRecord cardRecord = new CardRecord();
        cardRecord.setCardType(cardType);
        cardRecord.setCardId(cardId);
        cardRecord.setCardNo(cardNo);
        cardRecord.setCardRecordOperate(operateType);
        cardRecord.setCardRecordObject(person);
        cardRecord.setCardRecordTime(new Date());
        return cardRecord;
    }

    /* 进出日志插入 ***************************************************************************************************************/

    /**
     * @param logType        0-入场，1-离场
     * @param idCardNo       身份证号
     * @param locationCardNo 定位卡编号
     * @param equipmentIp    设备IP
     * @param carParam       車牌號或車卡
     */
    @Transactional(readOnly = false)
    public void inOutLogInsert(String idCardNo, String locationCardNo, String equipmentIp, String logType, String carParam) {
        //预备拿一下回写数据库的编号
        String idNo = "";
        InOutLog log = new InOutLog();
        log.setLocationCardNo(locationCardNo);
        log.setIp(equipmentIp);
        log.setPlateNo(carParam);
        //道路類型
        log.setLoadType(carParam == null ? "0" : "1");

        //1.查到人 ：工程、工单、名称、人员信息
        //2.查到对应设备 ：厂区、人/车道、
        List<SysUser> userList = userMapper.getByCommonParams(idCardNo, null, null, null);
        if (userList.size() > 0) {
            // 员工进入
            competeLogFromUser(log, userList.get(0));
            idNo = userList.get(0).getEmpNo();
        } else {
            List<ManFactory> factoryList = factoryMapper.getByCommonParams(idCardNo, null, null, null);
            if (factoryList.size() == 0) {
                return;
            }
            //厂商人员进入
            competeLogFromFactory(log, factoryList.get(0));
            idNo = factoryList.get(0).getIpLtLic();
        }
        competeLogFromEquipment(log, equipmentIp);
        // 第一位，出入，0-入场，1-离场
        // 第二位，0-員工、1-廠商、2-車輛
        // 先判斷是不是車輛
        String secType = StringUtils.isBlank(carParam) ? "" : "2";
        //不是車輛的話，就拿人員類型
        secType = StringUtils.isBlank(secType) ? log.getPersonType() : secType;
        //最後再拼成兩位字符串
        log.setLogType(logType + secType);
        inOutLogMapper.insertInOutLog(log);

        try {
            //获取厂区
            String fctDorNm = "";//厂区编号
            //获取海康设备
            HikEquipment equipment = equipmentMapper.findByIp(equipmentIp);
            //根据ip获取设备
            if (equipment.getFrontIp() != null) {
                PlcEquipment plcEquipment = plcRedisUtils.getPlcEquipment(equipment.getFrontIp());
                //根据设备获取厂区编号
                if (plcEquipment != null) {
                    SysDept dept = sysDeptMapper.selectDeptById(plcEquipment.getPlantAreaId());
                    fctDorNm = dept != null ? dept.getDeptNo() : "";
                }
            }

            //然后还要往旧数据库插入数据；
            TcInOutLog tcInOutLog = getTcInOutLog(equipmentIp, logType + secType, idNo, factoryCode);

            switch (factoryCode) {
                case "PPC2A01"://AE廠
                    HttpUtils.sendJsonPost("http://127.0.0.1:36670/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPF2A01"://SAP厰
                    HttpUtils.sendJsonPost("http://127.0.0.1:36680/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPC1A01"://PVC廠
                    HttpUtils.sendJsonPost("http://127.0.0.1:36650/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPC8601"://EVA廠
                    HttpUtils.sendJsonPost("http://127.0.0.1:36690/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPCP101"://PP廠
                    HttpUtils.sendJsonPost("http://127.0.0.1:36660/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PMA1001"://港務公司
                    HttpUtils.sendJsonPost("http://127.0.0.1:36700/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPZ1101"://台塑三井
                    HttpUtils.sendJsonPost("http://127.0.0.1:36710/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
            }

            //HttpUtils.sendJsonPost("http://127.0.0.1:8080/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param logType        0-入场，1-离场
     * @param idCardNo       身份证号
     * @param locationCardNo 定位卡编号
     * @param equipmentIp    设备IP
     * @param carParam       車牌號或車卡
     */
    @Transactional(readOnly = false)
    public void inOutLogInsertCar(String idCardNo, String locationCardNo, String equipmentIp, String logType, String carParam) {
        //预备拿一下回写数据库的编号
        String idNo = "";
        InOutLog log = new InOutLog();
        log.setLocationCardNo(locationCardNo);
        log.setIp(equipmentIp);
        log.setPlateNo(carParam);
        //道路類型
        log.setLoadType(carParam == null ? "0" : "1");

        //1.查到人 ：工程、工单、名称、人员信息
        //2.查到对应设备 ：厂区、人/车道、
        List<SysUser> userList = userMapper.getByCommonParams(idCardNo, null, null, null);
        if (userList.size() > 0) {
            // 员工进入
            competeLogFromUser(log, userList.get(0));
            idNo = userList.get(0).getEmpNo();
        } else {
            List<ManFactory> factoryList = factoryMapper.getByCommonParams(idCardNo, null, null, null);
            if (factoryList.size() == 0) {
                return;
            }
            //厂商人员进入
            competeLogFromFactory(log, factoryList.get(0));
            idNo = factoryList.get(0).getIpLtLic();
        }
        competeLogFromEquipment(log, equipmentIp);
        // 第一位，出入，0-入场，1-离场
        // 第二位，0-員工、1-廠商、2-車輛
        // 先判斷是不是車輛
        String secType = StringUtils.isBlank(carParam) ? "" : "2";
        //不是車輛的話，就拿人員類型
        secType = StringUtils.isBlank(secType) ? log.getPersonType() : secType;
        //最後再拼成兩位字符串
        log.setLogType(logType + secType);
        inOutLogMapper.insertInOutLog(log);

        try {
            //获取厂区
            //String fctDorNm = "";//厂区编号
            //获取海康设备
//            HikEquipment equipment = equipmentMapper.findByIp(equipmentIp);
            //根据ip获取设备
//            if (equipment.getFrontIp() != null) {
//                PlcEquipment plcEquipment = plcRedisUtils.getPlcEquipment(equipment.getFrontIp());
                //根据设备获取厂区编号
//                if (plcEquipment != null) {
//                    SysDept dept = sysDeptMapper.selectDeptById(plcEquipment.getPlantAreaId());
//                    fctDorNm = dept != null ? dept.getDeptNo() : "";
//                }
//            }

            //TODO  回显信息修改
            //然后还要往旧数据库插入数据；
            //车道信息补全
            TcInOutLog tcInOutLogCar = null;
            logger.info("equipmentIp"+equipmentIp);
            System.out.println("logType : " + logType);
            if (carParam != null) {
                //获取到车辆信息
                List<BaseSafetycar> safetycarlist = safetycarService.getSafetycarByCarno(carParam);
                if (!CollectionUtils.isEmpty(safetycarlist)) {
                    BaseSafetycar baseSafetycar = safetycarlist.get(0);
                    String CarIdNo = baseSafetycar.getIpLtLic() + baseSafetycar.getPz();
                    tcInOutLogCar = getTcInOutLog(equipmentIp, logType + secType, CarIdNo, factoryCode);
                }
            }

            //然后还要往旧数据库插入数据； factoryCode:模拟字段
            String personSecType = log.getPersonType();
            System.out.println("logType + personSecType = " + logType + personSecType);
            TcInOutLog tcInOutLog = getTcInOutLog(equipmentIp, logType + personSecType, idNo, factoryCode);
            System.out.println("tcInOutLog = " + tcInOutLog);


            switch (factoryCode) {
                case "PPC2A01"://AE廠
                    if (tcInOutLogCar != null) {
                        HttpUtils.sendJsonPost("http://127.0.0.1:36670/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36670/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPF2A01"://SAP厰
                    if (tcInOutLogCar != null) {
                        HttpUtils.sendJsonPost("http://127.0.0.1:36680/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36680/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPC1A01"://PVC廠
                    if (tcInOutLogCar != null) {
                        HttpUtils.sendJsonPost("http://127.0.0.1:36650/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36650/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPC8601"://EVA廠
                    if (tcInOutLogCar != null) {
                        HttpUtils.sendJsonPost("http://127.0.0.1:36690/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36690/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPCP101"://PP廠
                    if (tcInOutLogCar != null) {
                        HttpUtils.sendJsonPost("http://127.0.0.1:36660/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36660/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PMA1001"://港務公司
                    if (tcInOutLogCar != null) {
                        HttpUtils.sendJsonPost("http://127.0.0.1:36700/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36700/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPZ1101"://台塑三井
                    if (tcInOutLogCar != null) {
                        HttpUtils.sendJsonPost("http://127.0.0.1:36710/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36710/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
            }
            //HttpUtils.sendJsonPost("http://127.0.0.1:8080/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional(readOnly = false)
    public void inOutLogInsertCarForShipment(String idCardNo, String locationCardNo, String equipmentIp, String logType, String carParam) {
        //预备拿一下回写数据库的编号
        String idNo = "";
        InOutLog log = new InOutLog();
        log.setLocationCardNo(locationCardNo);
        log.setIp(equipmentIp);
        log.setPlateNo(carParam);
        //道路類型
        log.setLoadType(carParam == null ? "0" : "1");

        //1.查到人 ：工程、工单、名称、人员信息
        //2.查到对应设备 ：厂区、人/车道、
        List<SysUser> userList = userMapper.getByCommonParams(idCardNo, null, null, null);
        if (userList.size() > 0) {
            // 员工进入
            competeLogFromUser(log, userList.get(0));
            idNo = userList.get(0).getEmpNo();
        } else {
            List<ManFactory> factoryList = factoryMapper.getByCommonParams(idCardNo, null, null, null);
            if (factoryList.size() == 0) {
                return;
            }
            //厂商人员进入
            competeLogFromFactory(log, factoryList.get(0));
            idNo = factoryList.get(0).getIpLtLic();
        }
        competeLogFromEquipment(log, equipmentIp);
        // 第一位，出入，0-入场，1-离场
        // 第二位，0-員工、1-廠商、2-車輛
        // 先判斷是不是車輛
        String secType = StringUtils.isBlank(carParam) ? "" : "2";
        //不是車輛的話，就拿人員類型
        secType = StringUtils.isBlank(secType) ? log.getPersonType() : secType;
        //最後再拼成兩位字符串
        log.setLogType(logType + secType);
        inOutLogMapper.insertInOutLog(log);

        try {
            //获取厂区
            //String fctDorNm = "";//厂区编号
            //获取海康设备
//            HikEquipment equipment = equipmentMapper.findByIp(equipmentIp);
            //根据ip获取设备
//            if (equipment.getFrontIp() != null) {
//                PlcEquipment plcEquipment = plcRedisUtils.getPlcEquipment(equipment.getFrontIp());
            //根据设备获取厂区编号
//                if (plcEquipment != null) {
//                    SysDept dept = sysDeptMapper.selectDeptById(plcEquipment.getPlantAreaId());
//                    fctDorNm = dept != null ? dept.getDeptNo() : "";
//                }
//            }

            //TODO  回显信息修改
            //然后还要往旧数据库插入数据；
            //车道信息补全
            TcInOutLog tcInOutLogCar = null;
            logger.info("equipmentIp"+equipmentIp);
            System.out.println("logType : " + logType);
            if (carParam != null) {
                //获取到车辆信息
                List<BaseSafetycar> safetycarlist = safetycarService.getSafetycarByCarno(carParam);
                if (!CollectionUtils.isEmpty(safetycarlist)) {
                    BaseSafetycar baseSafetycar = safetycarlist.get(0);
                    String CarIdNo = baseSafetycar.getIpLtLic() + baseSafetycar.getPz();
                    tcInOutLogCar = getTcInOutLog(equipmentIp, logType + secType, CarIdNo, factoryCode);
                }
            }

            //然后还要往旧数据库插入数据； factoryCode:模拟字段
            String personSecType = log.getPersonType();
            System.out.println("logType + personSecType = " + logType + personSecType);
            TcInOutLog tcInOutLog = getTcInOutLog(equipmentIp, logType + personSecType, idNo, factoryCode);
            System.out.println("tcInOutLog = " + tcInOutLog);


            switch (factoryCode) {
                case "PPC2A01"://AE廠
//                    if (tcInOutLogCar != null) {
//                        HttpUtils.sendJsonPost("http://127.0.0.1:36670/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
//                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36670/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPF2A01"://SAP厰
//                    if (tcInOutLogCar != null) {
//                        HttpUtils.sendJsonPost("http://127.0.0.1:36680/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
//                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36680/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPC1A01"://PVC廠
//                    if (tcInOutLogCar != null) {
//                        HttpUtils.sendJsonPost("http://127.0.0.1:36650/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
//                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36650/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPC8601"://EVA廠
//                    if (tcInOutLogCar != null) {
//                        HttpUtils.sendJsonPost("http://127.0.0.1:36690/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
//                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36690/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPCP101"://PP廠
//                    if (tcInOutLogCar != null) {
//                        HttpUtils.sendJsonPost("http://127.0.0.1:36660/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
//                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36660/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PMA1001"://港務公司
//                    if (tcInOutLogCar != null) {
//                        HttpUtils.sendJsonPost("http://127.0.0.1:36700/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
//                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36700/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
                case "PPZ1101"://台塑三井
//                    if (tcInOutLogCar != null) {
//                        HttpUtils.sendJsonPost("http://127.0.0.1:36710/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLogCar));
//                    }
                    HttpUtils.sendJsonPost("http://127.0.0.1:36710/ruoyi-admin/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
                    break;
            }
            //HttpUtils.sendJsonPost("http://127.0.0.1:8080/api/inOutLog/setOutLog", JSONObject.toJSONString(tcInOutLog));
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    //车出去的时候，需要发两条
    private TcInOutLog getTcInOutLog(String ip, String type, String no, String fctDorNm) {
        TcInOutLog result = new TcInOutLog();
        result.setIp(OldIpMap.REWRITE_IP_MAP.get(ip));
        result.setDateTime(DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", new Date()));
        switch (type) {
            case "00":
            case "01":
                //人进
                result.setType("1");
                break;
            case "10":
            case "11":
                //人出
                result.setType("2");
                break;
            case "02"://车进
                result.setType("3");
                break;
            case "12"://车出
                result.setType("4");
                break;
        }
        result.setIdNo(no);
        result.setUploaded(0);

        result.setFctDorNm(fctDorNm);
        return result;
    }

    // 补全日志信息
    private void competeLogFromUser(InOutLog log, SysUser user) {
        log.setCreateTime(new Date());
        log.setPersonType("0");
        log.setUserId(user.getUserId());
        log.setName(user.getNickName());
        log.setIdCard(user.getIdCard());
        log.setUserNo(user.getEmpNo());
        SysDept dept = user.getDept();
        if (dept != null) {
            log.setUserDeptName(dept.getDeptName());
        }
    }

    // 补全日志信息
    private void competeLogFromFactory(InOutLog log, ManFactory factory) {
        log.setCreateTime(new Date());
        log.setPersonType("1");
        log.setFactoryId(factory.getFactoryId());
        log.setProjectNo(factory.getThisNumber());
        log.setProjectName(factory.getEgNm());

        log.setProjectName(factory.getThisNumber());
        log.setWorkNo(factory.getWorkNo());
        log.setFactoryName(factory.getFactoryName());
        log.setName(factory.getName());
        log.setIdCard(factory.getIdCard());
    }

    // 補全設備相關信息，
    private void competeLogFromEquipment(InOutLog log, String equipmentIp) {
        HikEquipment equipment = equipmentMapper.findByIp(equipmentIp);
        if (equipment == null) {
            return;
        }
        //根据出入设备IP获取大门
        log.setGate(equipment.getName().substring(0, 2));
        //廠區
        //前端設備IP(PLC的IP)
        if (equipment.getFrontIp() != null) {
            PlcEquipment plcEquipment = plcRedisUtils.getPlcEquipment(equipment.getFrontIp());
            if (plcEquipment != null && plcEquipment.getPlantAreaId() != null) {
                SysDept sysDept = deptService.selectDeptById(plcEquipment.getPlantAreaId());
                log.setDeptId(sysDept.getDeptId());
                log.setAreaName(sysDept.getDeptName());
            }
        }
    }


    /* 人员信息下发方法、绑定解绑定位卡下发相关 ***************************************************************************************************************/

    /**
     * 厂商人员的人脸信息下发，批量
     * 1. 循环，拿到人员信息
     * 2. 判断信息是否齐全，不齐的不下发
     */
    public String sendFactoryMsgList(Long[] factoryIds) {
        for (int i = 0; i < factoryIds.length; i++) {
            ManFactory factory = factoryMapper.selectManFactoryByFactoryId(factoryIds[i]);
            if (factory == null || StringUtils.isBlank(factory.getWorkNo())) {
                continue;
            }
            if (StringUtils.isNotBlank(factory.getFace()) && StringUtils.isNotBlank(factory.getIdCard()) && 1 != factory.getSended()) {
                try {
                    //身份证号、照片都有了，组装信息，然后下发
                    PersonVO personVO = competeByFactory(factory);
                    Integer resultCode = personSendService.downSendPersonInfoRequest(personVO);
                    if (200 == resultCode) {//回调看结果，是否下发成功
                        factoryMapper.sendBackStatus(factory.getFactoryId(), 1);
                    } else {
                        factoryMapper.sendBackStatus(factory.getFactoryId(), 9);
                    }
                } catch (Exception e) { //偶尔出错没事，继续下发
                    e.printStackTrace();
                }
            } else if (StringUtils.isNotBlank(factory.getFace()) && StringUtils.isNotBlank(factory.getIdCard()) && factory.getSended() == 1) {
                try {
                    //这里是已经下发成功过了，直接更新权限
                    PersonVO personVO = competeByFactoryAuth(factory);
                    personSendService.updateHikAuths(personVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 厂商人员的人脸信息下发，批量
     * 1. 循环，拿到人员信息
     * 2. 判断信息是否齐全，不齐的不下发
     */
    public String sendFactoryMsgListForManFactory(Long[] factoryIds) {
        for (int i = 0; i < factoryIds.length; i++) {
            ManFactory factory = factoryMapper.selectManFactoryByFactoryId(factoryIds[i]);
            if (factory == null || StringUtils.isBlank(factory.getWorkNo())) {
                continue;
            }
            //修改人脸照片并下发
            if (StringUtils.isNotBlank(factory.getFace()) && StringUtils.isNotBlank(factory.getIdCard()) &&  factory.getSended()==8) {
                try {
                    //身份证号、照片都有了，组装信息，然后下发
                    PersonVO personVO = competeByFactory(factory);
                    Integer resultCode = personSendService.downSendPersonInfoRequestForManFactory(personVO);
                    if (200 == resultCode) {//回调看结果，是否下发成功
                        factoryMapper.sendBackStatus(factory.getFactoryId(), 1);
                    } else {
                        factoryMapper.sendBackStatus(factory.getFactoryId(), 9);
                    }
                } catch (Exception e) { //偶尔出错没事，继续下发
                    e.printStackTrace();
                }
            }

            if (StringUtils.isNotBlank(factory.getFace()) && StringUtils.isNotBlank(factory.getIdCard()) && 1 != factory.getSended()) {
                try {
                    //身份证号、照片都有了，组装信息，然后下发
                    PersonVO personVO = competeByFactory(factory);
                    Integer resultCode = personSendService.downSendPersonInfoRequest(personVO);
                    if (200 == resultCode) {//回调看结果，是否下发成功
                        factoryMapper.sendBackStatus(factory.getFactoryId(), 1);
                    } else {
                        factoryMapper.sendBackStatus(factory.getFactoryId(), 9);
                    }
                } catch (Exception e) { //偶尔出错没事，继续下发
                    e.printStackTrace();
                }
            } else if (StringUtils.isNotBlank(factory.getFace()) && StringUtils.isNotBlank(factory.getIdCard()) && factory.getSended() == 1) {
                try {
                    //这里是已经下发成功过了，直接更新权限
                    PersonVO personVO = competeByFactoryAuth(factory);
                    personSendService.updateHikAuths(personVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    //厂商人员下发信息补全，比较麻烦，所以单独抽出来
    private PersonVO competeByFactory(ManFactory factory) throws Exception {
        PersonVO requestVo = new PersonVO();
        requestVo.setAuthIsAll(false);
        //先拿到工单，工单里面有ip,再根据ip拿plc，plc下面有人脸设备
        ManWork manWork = workMapper.selectManWorkByworkNo(factory.getWorkNo());
        if (StringUtils.isBlank(manWork.getIp())) {
            requestVo.setDeviceNos(null);
        } else {
            List<String> indexNos = new ArrayList<>();
            // 有IP，直接拿相关的人脸设备
            String[] splitIp = manWork.getIp().split(",");
            for (String ip : splitIp) {
                indexNos.addAll(equipmentMapper.listPersonEquipmentCode(ip));
            }
            indexNos.addAll(equipmentMapper.findLocationEquipList());
            requestVo.setDeviceNos(indexNos);

        }
        String faceBase64 = HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port + "/ruoyi-admin" + factory.getFace());
        requestVo.setFaceBase64Str(faceBase64);
        requestVo.setJobNo(null);
        requestVo.setOrderSn(factory.getWorkNo());
        requestVo.setPersonId(factory.getIdCard());
        requestVo.setPersonName(factory.getName());
        requestVo.setPersonType(1);
        requestVo.setPhoneNo(factory.getPhone());
        return requestVo;
    }

    //厂商人员下发信息补全，专门用来更新权限的
    private PersonVO competeByFactoryAuth(ManFactory factory) throws Exception {
        PersonVO requestVo = new PersonVO();
        requestVo.setAuthIsAll(false);
        //先拿到工单，工单里面有ip,再根据ip拿plc，plc下面有人脸设备
        ManWork manWork = workMapper.selectManWorkByworkNo(factory.getWorkNo());
        if (StringUtils.isBlank(manWork.getIp())) {
            requestVo.setDeviceNos(null);
        } else {
            List<String> indexNos = new ArrayList<>();
            // 有IP，直接拿相关的人脸设备
            String[] splitIp = manWork.getIp().split(",");
            for (String ip : splitIp) {
                indexNos.addAll(equipmentMapper.listPersonEquipmentCode(ip));
            }
            indexNos.addAll(equipmentMapper.findLocationEquipList());
            requestVo.setDeviceNos(indexNos);
        }
        requestVo.setPersonType(1);
        requestVo.setOrderSn(factory.getWorkNo());
        requestVo.setPersonId(factory.getIdCard());
        return requestVo;
    }

    /**
     * 内部员工下发海康流程
     * 下发信息，成功后再调用绑卡
     * --------------------
     * 2022.04.02，修改逻辑
     * 员工下发信息只需要身份证、照片；成功后再判断是否存在定位卡，如果定位卡存在则绑卡
     * 员工小程序上传人脸也可以触发
     */
    /**
     * 2022.08.09 修改逻辑
     * 原方法注释掉 新逻辑只修改海康权限
     *
     * @param user
     */
//    public void userBindHlk(SysUser user) {
//        //身份证、照片、定位卡号不为空，就可以下发了
//        if (StringUtils.isBlank(user.getFace()) || StringUtils.isBlank(user.getIdCard())) {
//            return;
//        }
//        //拿到人员的角色
//        List<SysRole> roles = sysRoleMapper.selectRolePermissionByUserId(user.getUserId());
//        Integer resultCode;
//        //判断人员信息是不是已经下发过了
//        if (user.getSended() != null && 1 == user.getSended()) {
//            //已经下发过了，需要调用照片更新
//            resultCode = personSendService.updateUserFace(user.getIdCard(), HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port +"/ruoyi-admin"+ user.getFace()));
//            //更新海康权限
//            PersonVO personVO = new PersonVO();
//            personVO.setAuthIsAll(false);
//            personVO.setDeviceNos(userJurisdiction.getCodeByUser(user));
//            personVO.setPersonId(user.getIdCard());
//            //判断是不是存在特殊权限
//            if (roles.size() > 0) {
//                roles.forEach(sysRole -> {
//                    //人道特殊权限-脸
//                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
//                        personVO.setFaceSpecialPersonRoad("facePeopleRoad");
//                    }
//                    //车道特殊权限-脸
//                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
//                        personVO.setFaceSpecialCarRoad("facecCarRoad");
//                    }
//                    //人道特殊权限-卡
//                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
//                        personVO.setCardSpecialPersonRoad("CardPeopleRoad");
//                    }
//                    //车道特殊权限-卡
//                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
//                        personVO.setCardSpecialCarRoad("cardCarRoad");
//                    }
//                });
//            }
//
//            personSendService.updateHikAuths(personVO);
//        } else {
//            //还没下发过，下发新的内部人员
//            //内部员工信息
//            PersonVO requestVo = new PersonVO();
//            requestVo.setAuthIsAll(false);
//            requestVo.setDeviceNos(userJurisdiction.getCodeByUser(user));
//            //拿到人脸Base64编码
//            String faceBase64 = HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port+"/ruoyi-admin" + user.getFace());
//            requestVo.setFaceBase64Str(faceBase64);
//            requestVo.setJobNo(user.getEmpNo());
//            requestVo.setOrderSn(null);
//            requestVo.setPersonId(user.getIdCard());
//            requestVo.setPersonName(user.getNickName());
//            requestVo.setPersonType(0);
//            requestVo.setPhoneNo(user.getPhonenumber());
//            //判断是不是存在特殊权限
//            if (roles.size() > 0) {
//                roles.forEach(sysRole -> {
//                    //人道特殊权限-脸
//                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setFaceSpecialPersonRoad("facePeopleRoad");
//                    }
//                    //车道特殊权限-脸
//                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setFaceSpecialCarRoad("facecCarRoad");
//                    }
//                    //人道特殊权限-卡
//                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setCardSpecialPersonRoad("CardPeopleRoad");
//                    }
//                    //车道特殊权限-卡
//                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setCardSpecialCarRoad("cardCarRoad");
//                    }
//                });
//            }
//
//            //调用下发人员信息接口
//            resultCode = personSendService.downSendPersonInfoRequest(requestVo);
//        }
//        if (resultCode == 200) {
//            userMapper.sendBackStatus(user.getUserId(), 1);
//            //人员信息下发成功，调用绑定定位卡接口
//            if (StringUtils.isNotBlank(user.getPositionCardNo())) {
//                locationCardSendService.bindCardRequest(new CardBindVO(user.getPositionCardNo(), user.getIdCard()));
//            }
//        } else {
//            userMapper.sendBackStatus(user.getUserId(), 9);
//        }
//    }
    public void userBindHlk(SysUser user) {
        //身份证、照片、定位卡号不为空，就可以下发了
        if (StringUtils.isBlank(user.getFace()) || StringUtils.isBlank(user.getIdCard())) {
            return;
        }
        //拿到人员的角色
        List<SysRole> roles = sysRoleMapper.selectRolePermissionByUserId(user.getUserId());
        Integer resultCode = 0;
        //判断人员信息是不是已经下发过了
        if (user.getSended() != null && 1 == user.getSended()) {
            //已经下发过了，需要调用照片更新
            //resultCode = personSendService.updateUserFace(user.getIdCard(), HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port +"/ruoyi-admin"+ user.getFace()));
            //更新海康权限
            PersonVO personVO = new PersonVO();
            personVO.setAuthIsAll(false);
            personVO.setDeviceNos(userJurisdiction.getCodeByUser(user));
            personVO.setPersonId(user.getIdCard());
            personVO.setJobNo(user.getEmpNo());
            personVO.setPersonType(0);
            //判断是不是存在特殊权限
            if (roles.size() > 0) {
                roles.forEach(sysRole -> {
                    //人道特殊权限-脸
                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
                        personVO.setFaceSpecialPersonRoad("facePeopleRoad");
                    }
                    //车道特殊权限-脸
                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
                        personVO.setFaceSpecialCarRoad("facecCarRoad");
                    }
                    //人道特殊权限-卡
                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
                        personVO.setCardSpecialPersonRoad("CardPeopleRoad");
                    }
                    //车道特殊权限-卡
                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
                        personVO.setCardSpecialCarRoad("cardCarRoad");
                    }
                });
            }

            personSendService.updateHikAuths(personVO);
        }
//        else {
//            //还没下发过，下发新的内部人员
//            //内部员工信息
//            PersonVO requestVo = new PersonVO();
//            requestVo.setAuthIsAll(false);
        //requestVo.setDeviceNos(userJurisdiction.getCodeByUser(user));
//            //拿到人脸Base64编码
//            String faceBase64 = HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port+"/ruoyi-admin" + user.getFace());
//            requestVo.setFaceBase64Str(faceBase64);
//            requestVo.setJobNo(user.getEmpNo());
//            requestVo.setOrderSn(null);
//            requestVo.setPersonId(user.getIdCard());
//            requestVo.setPersonName(user.getNickName());
//            requestVo.setPersonType(0);
//            requestVo.setPhoneNo(user.getPhonenumber());
//            //判断是不是存在特殊权限
//            if (roles.size() > 0) {
//                roles.forEach(sysRole -> {
//                    //人道特殊权限-脸
//                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setFaceSpecialPersonRoad("facePeopleRoad");
//                    }
//                    //车道特殊权限-脸
//                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setFaceSpecialCarRoad("facecCarRoad");
//                    }
//                    //人道特殊权限-卡
//                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setCardSpecialPersonRoad("CardPeopleRoad");
//                    }
//                    //车道特殊权限-卡
//                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setCardSpecialCarRoad("cardCarRoad");
//                    }
//                });
//            }
//
//            //调用下发人员信息接口
        //resultCode = personSendService.downSendPersonInfoRequest(requestVo);
//        }
//        if (resultCode == 200) {
//            userMapper.sendBackStatus(user.getUserId(), 1);
//            //人员信息下发成功，调用绑定定位卡接口
//
//        } else {
//            userMapper.sendBackStatus(user.getUserId(), 9);
//        }
        if (StringUtils.isNotBlank(user.getPositionCardNo())) {
            locationCardSendService.bindCardRequest(new CardBindVO(user.getPositionCardNo(), user.getIdCard()));
        }
    }

    public void userBindHlkSubSysUser(SysUser user) {
        //身份证、照片不为空，就可以下发了

        System.out.println(user.getFace()+"---->"+user.getIdCard());
        if (StringUtils.isBlank(user.getFace()) || StringUtils.isBlank(user.getIdCard())) {
            return;
        }
        //拿到人员的角色
        //List<SysRole> roles = sysRoleMapper.selectRolePermissionByUserId(user.getUserId());
        Integer resultCode = 0;


        //判断人员信息是不是已经下发过了
        //8 曾經下發過信息 人臉刪除
        System.out.println("user.getSended()--->"+user.getSended());
        if (user.getSended() != null && 8 == user.getSended()) {
            System.out.println("8 == user.getSended()");
//            //已经下发过了，需要调用照片更新
            resultCode = personSendService.updateUserOnlyFace(user.getIdCard(), HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port +"/ruoyi-admin"+ user.getFace()));
//            //更新海康权限
            PersonVO personVO = new PersonVO();
            personVO.setAuthIsAll(false);
            personVO.setDeviceNos(userJurisdiction.getCodeByUserNew(user));
            personVO.setPersonId(user.getIdCard());
            personVO.setJobNo(user.getEmpNo());
            personVO.setPersonType(0);
//            //判断是不是存在特殊权限
////            if (roles.size() > 0) {
////                roles.forEach(sysRole -> {
////                    //人道特殊权限-脸
////                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
////                        personVO.setFaceSpecialPersonRoad("facePeopleRoad");
////                    }
////                    //车道特殊权限-脸
////                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
////                        personVO.setFaceSpecialCarRoad("facecCarRoad");
////                    }
////                    //人道特殊权限-卡
////                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
////                        personVO.setCardSpecialPersonRoad("CardPeopleRoad");
////                    }
////                    //车道特殊权限-卡
////                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
////                        personVO.setCardSpecialCarRoad("cardCarRoad");
////                    }
////                });
////            }
//
            personSendService.updateHikAuthsSysyUser(personVO);
        }
        else if (user.getSended() != null && 0 == user.getSended()) {
            System.out.println("0 == user.getSended()");
            //还没下发过，下发新的内部人员
            //内部员工信息
            PersonVO requestVo = new PersonVO();
            requestVo.setAuthIsAll(false);
            requestVo.setDeviceNos(userJurisdiction.getCodeByUserNew(user));
            //拿到人脸Base64编码
            String faceBase64 = HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port+"/ruoyi-admin" + user.getFace());
            requestVo.setFaceBase64Str(faceBase64);
            requestVo.setJobNo(user.getEmpNo());
            requestVo.setOrderSn(null);
            requestVo.setPersonId(user.getIdCard());
            requestVo.setPersonName(user.getNickName());
            requestVo.setPersonType(0);
            requestVo.setPhoneNo(user.getPhonenumber());
            //判断是不是存在特殊权限
//            if (roles.size() > 0) {
//                roles.forEach(sysRole -> {
//                    //人道特殊权限-脸
//                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setFaceSpecialPersonRoad("facePeopleRoad");
//                    }
//                    //车道特殊权限-脸
//                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setFaceSpecialCarRoad("facecCarRoad");
//                    }
//                    //人道特殊权限-卡
//                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setCardSpecialPersonRoad("CardPeopleRoad");
//                    }
//                    //车道特殊权限-卡
//                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
//                        requestVo.setCardSpecialCarRoad("cardCarRoad");
//                    }
//                });
//            }

            //调用下发人员信息接口
            resultCode = personSendService.downSendPersonInfoRequestForSubSysUser(requestVo);
        }
        //判断人员信息是不是已经下发过了
       else if (user.getSended() != null && 1 == user.getSended()) {
            //已经下发过了，需要调用照片更新
            System.out.println("1 == user.getSended()");
            //resultCode = personSendService.updateUserFace(user.getIdCard(), HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port +"/ruoyi-admin"+ user.getFace()));
            //更新海康权限
            PersonVO personVO = new PersonVO();
            personVO.setAuthIsAll(false);
            personVO.setDeviceNos(userJurisdiction.getCodeByUserNew(user));
            personVO.setPersonId(user.getIdCard());
            personVO.setJobNo(user.getEmpNo());
            personVO.setPersonType(0);
            System.out.println(userJurisdiction.getCodeByUserNew(user));

            personSendService.updateHikAuthsSysyUser(personVO);
        }

        user.setSended(1L);
        sysUserMapper.updateUser(user);
//        if (resultCode == 200) {
//            userMapper.sendBackStatus(user.getUserId(), 1);
//            //人员信息下发成功，调用绑定定位卡接口
//
//        } else {
//            userMapper.sendBackStatus(user.getUserId(), 9);
//        }
        if (StringUtils.isNotBlank(user.getPositionCardNo())) {
            locationCardSendService.bindCardRequest(new CardBindVO(user.getPositionCardNo(), user.getIdCard()));
        }
    }

    public void userBindHlkSubSysUserUpdate(SysUser user) {
        //身份证、照片不为空，就可以下发了

        System.out.println(user.getFace()+"---->"+user.getIdCard());
//        if (StringUtils.isBlank(user.getFace()) || StringUtils.isBlank(user.getIdCard())) {
//            return;
//        }
        //拿到人员的角色
        //List<SysRole> roles = sysRoleMapper.selectRolePermissionByUserId(user.getUserId());
        Integer resultCode = 0;


        //判断人员信息是不是已经下发过了
        //8 曾經下發過信息 人臉刪除
//        if (user.getSended() != null && 8 == user.getSended()) {
//            System.out.println("8 == user.getSended()");
////            //已经下发过了，需要调用照片更新
//            resultCode = personSendService.updateUserOnlyFace(user.getIdCard(), HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port +"/ruoyi-admin"+ user.getFace()));
////            //更新海康权限
//            PersonVO personVO = new PersonVO();
//            personVO.setAuthIsAll(false);
//            personVO.setDeviceNos(userJurisdiction.getCodeByUserNew(user));
//            personVO.setPersonId(user.getIdCard());
//            personVO.setJobNo(user.getEmpNo());
//            personVO.setPersonType(0);
////            //判断是不是存在特殊权限
//////            if (roles.size() > 0) {
//////                roles.forEach(sysRole -> {
//////                    //人道特殊权限-脸
//////                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
//////                        personVO.setFaceSpecialPersonRoad("facePeopleRoad");
//////                    }
//////                    //车道特殊权限-脸
//////                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
//////                        personVO.setFaceSpecialCarRoad("facecCarRoad");
//////                    }
//////                    //人道特殊权限-卡
//////                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
//////                        personVO.setCardSpecialPersonRoad("CardPeopleRoad");
//////                    }
//////                    //车道特殊权限-卡
//////                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
//////                        personVO.setCardSpecialCarRoad("cardCarRoad");
//////                    }
//////                });
//////            }
////
//            personSendService.updateHikAuthsSysyUser(personVO);
//        }
//        else if (user.getSended() != null && 0 == user.getSended()) {
//            System.out.println("0 == user.getSended()");
//            //还没下发过，下发新的内部人员
//            //内部员工信息
//            PersonVO requestVo = new PersonVO();
//            requestVo.setAuthIsAll(false);
//            requestVo.setDeviceNos(userJurisdiction.getCodeByUserNew(user));
//            //拿到人脸Base64编码
//            String faceBase64 = HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port+"/ruoyi-admin" + user.getFace());
//            requestVo.setFaceBase64Str(faceBase64);
//            requestVo.setJobNo(user.getEmpNo());
//            requestVo.setOrderSn(null);
//            requestVo.setPersonId(user.getIdCard());
//            requestVo.setPersonName(user.getNickName());
//            requestVo.setPersonType(0);
//            requestVo.setPhoneNo(user.getPhonenumber());
//            //判断是不是存在特殊权限
////            if (roles.size() > 0) {
////                roles.forEach(sysRole -> {
////                    //人道特殊权限-脸
////                    if ("facePeopleRoad".equals(sysRole.getRoleKey())) {
////                        requestVo.setFaceSpecialPersonRoad("facePeopleRoad");
////                    }
////                    //车道特殊权限-脸
////                    if ("facecCarRoad".equals(sysRole.getRoleKey())) {
////                        requestVo.setFaceSpecialCarRoad("facecCarRoad");
////                    }
////                    //人道特殊权限-卡
////                    if ("CardPeopleRoad".equals(sysRole.getRoleKey())) {
////                        requestVo.setCardSpecialPersonRoad("CardPeopleRoad");
////                    }
////                    //车道特殊权限-卡
////                    if ("cardCarRoad".equals(sysRole.getRoleKey())) {
////                        requestVo.setCardSpecialCarRoad("cardCarRoad");
////                    }
////                });
////            }
//
//            //调用下发人员信息接口
//            resultCode = personSendService.downSendPersonInfoRequestForSubSysUser(requestVo);
//        }
        //判断人员信息是不是已经下发过了
         if (user.getSended() != null && 1 == user.getSended()) {
            //已经下发过了，需要调用照片更新
            System.out.println("1 == user.getSended()");
            //resultCode = personSendService.updateUserFace(user.getIdCard(), HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port +"/ruoyi-admin"+ user.getFace()));
            //更新海康权限
            PersonVO personVO = new PersonVO();
            personVO.setAuthIsAll(false);
            personVO.setDeviceNos(userJurisdiction.getCodeByUserNew(user));
            personVO.setPersonId(user.getIdCard());
            personVO.setJobNo(user.getEmpNo());
            personVO.setPersonType(0);
            System.out.println(userJurisdiction.getCodeByUserNew(user));

            personSendService.updateHikAuthsSysyUser(personVO);
        }


//        if (resultCode == 200) {
//            userMapper.sendBackStatus(user.getUserId(), 1);
//            //人员信息下发成功，调用绑定定位卡接口
//
//        } else {
//            userMapper.sendBackStatus(user.getUserId(), 9);
//        }
        if (StringUtils.isNotBlank(user.getPositionCardNo())) {
            locationCardSendService.bindCardRequest(new CardBindVO(user.getPositionCardNo(), user.getIdCard()));
        }
    }

    /**
     * 批量下发内部员工
     *
     * @param userId
     */
    public void sendUserIds(Long[] userId) {
        for (int i = 0; i < userId.length; i++) {
            SysUser sysUser = userMapper.selectUserById(userId[i]);
            userBindHlk(sysUser);
        }

    }

    /*  *//**
     * 员工拿到厂区的权限
     *
     * @param sysUser
     * @return
     *//*
    private List<String> getCodeByUser(SysUser sysUser) {

        //查询到员工的厂区(多个)
        SysUser user = userMapper.selectUserById(sysUser.getUserId());
        if (StringUtils.isBlank(user.getFactoryId())) {
            return null;
        }
        String[] split = user.getFactoryId().split(",");
        //根据厂区拿PLC(in)
        List<Long> longList = new ArrayList<>();
        for (String factory : split) {
            longList.add(new Long(factory));
        }
        List<PlcEquipment> plcEquipmentByDept = plcEquipmentService.getPlcEquipmentByDept(longList);
        List<String> indexNos = new ArrayList<>();
        for (PlcEquipment plcEquipment : plcEquipmentByDept) {
            indexNos.addAll(equipmentMapper.listPersonEquipmentCode(plcEquipment.getIp()));
        }


        return indexNos;
    }
*/

    /**
     * TODO 下发危险品的人脸信息(共用厂商的)
     */


}

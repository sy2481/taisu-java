package com.ruoyi.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.interact.CarCardSendService;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.service.IManWorkService;
import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 工单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-03-06
 */
@Service
public class ManWorkServiceImpl implements IManWorkService {
    @Autowired
    private ManWorkMapper manWorkMapper;
    @Autowired
    private ManFactoryMapper manFactoryMapper;
    @Autowired
    private ManWorkMapper workMapper;
    @Autowired
    private CarCardSendService carCardSendService;
    @Autowired
    private CarCardMapper carCardMapper;
    @Autowired
    private CardRecordMapper cardRecordMapper;
    @Autowired
    private ThreadPoolConfig pool;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PlcEquipmentMapper plcEquipmentMapper;

    @Autowired
    private EquipmentMapper equipmentMapper;
    private StringBuffer stringBuffer;

    /**
     * 查询工单
     */
    @Override
    public ManWork selectManWorkByWorkId(Long workId) {
        return manWorkMapper.selectManWorkByWorkId(workId);
    }

    /**
     * 查询工单列表
     */
    @Override
    public List<ManWork> selectManWorkList(ManWork manWork) {
        //查询当前登录人是否有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList != null) {
            manWork.setFactoryId(longList);
        }

        List<ManWork> manWorks = manWorkMapper.selectManWorkList(manWork);
        manWorks.forEach(manWorkItem -> {
            if (manWorkItem.getDeptId() != null) {
                SysDept sysDept = deptService.selectDeptById(manWorkItem.getDeptId());
                if (sysDept != null) {
                    manWorkItem.setDeptName(sysDept.getDeptName() == null ? "" : sysDept.getDeptName());
                }

            }

        });
        return manWorks;
    }

    /**
     * 新增工单
     */
    @Override
    public int insertManWork(ManWork manWork) {
        manWork.setCreateTime(new Date());
        manWork.setUpdateTime(new Date());
        manWork.setXtInNum(0);
        manWork.setComInNum(0);
        return manWorkMapper.insertManWork(manWork);
    }

    /**
     * 修改工单
     */
    @Override
    public int updateManWork(ManWork manWork) {
        manWork.setUpdateTime(new Date());
        return manWorkMapper.updateManWork(manWork);
    }

    /**
     * 批量删除工单
     */
    @Override
    public int deleteManWorkByWorkIds(Long[] workIds) {
        return manWorkMapper.deleteManWorkByWorkIds(workIds);
    }

    /**
     * 删除工单信息
     */
    @Override
    public int deleteManWorkByWorkId(Long workId) {
        return manWorkMapper.deleteManWorkByWorkId(workId);
    }


    @Override
    public void addCarCard(JSONObject jsonObject) {
        //获取工单要添加的车卡
        String carCarNo = (String) jsonObject.get("carCardNo");
        //要添加车卡的工单信息
        ManWork workInfo = workMapper.selectManWorkByworkNo((String) jsonObject.get("workNo"));
        String[] split;
        List<String> list;
        List<String> list1;
        if (!StringUtils.isEmpty(workInfo.getCarCard())) {
            split = workInfo.getCarCard().split(",");
            list = Arrays.asList(split);
            list1 = new ArrayList<>(list);
            list1.add(carCarNo);
        } else {
            //添加车卡
            list1 = new ArrayList<>();
            list1.add(carCarNo);
        }
        StringBuilder carCardlongStr = new StringBuilder();
        //删除后重新拼接员工车卡
        for (int i = 0; i < list1.size(); i++) {
            String cardNo = list1.get(i);
            if (i == 0) {
                carCardlongStr = new StringBuilder(cardNo);
            } else {
                carCardlongStr.append(",").append(cardNo);
            }
        }
        /***************更新车卡绑定信息**********************/
        //更新工单绑定车卡信息
        ManWork manWork = new ManWork();
        manWork.setCarCard(carCardlongStr.toString());
        manWork.setWorkId(workInfo.getWorkId());
        workMapper.updateManWork(manWork);
        //更新工单下的厂商人员车卡信息
        ManFactory manFactory = new ManFactory();
        manFactory.setWorkNo((String) jsonObject.get("workNo"));
        manFactory.setCarCardNo(carCardlongStr.toString());
        manFactoryMapper.updateManFactoryByWorkNo(manFactory);
        /************增加车卡后更新车卡状态**********************/
        //修改车卡状态为已绑定
        CarCard carCard = new CarCard();
        carCard.setCardCarNo(carCarNo);
        carCard.setCardCarStatus("1");
        carCard.setCardCarUse("2");
        carCard.setBindPlateNo(null);
        carCard.setLeadName(workInfo.getProjectName());
        carCard.setLeadTime(new Date());
        carCardMapper.updateCarCardByNo(carCard);
        //插入卡片记录
        //根据添加的的车卡编号拿到车卡信息
        CarCard carCardInfo = carCardMapper.selectCarCardByCardCarNo(carCarNo);
        CardRecord cardRecord = new CardRecord();
        cardRecord.setCardType("0");
        cardRecord.setCardId(carCardInfo.getCardCarId());
        cardRecord.setCardNo(carCarNo);
        cardRecord.setCardRecordOperate("0");//归还操作记录
        cardRecord.setCardRecordObject(workInfo.getProjectName());
        cardRecord.setCardRecordTime(new Date());
        cardRecord.setCardRecordName(SecurityUtils.getLoginUser().getUsername());
        cardRecordMapper.insertCardRecord(cardRecord);
        //最后下发车卡权限
        pool.threadPoolTaskExecutor().execute(() -> carCardSendService.workCarCardDownSend(manWorkMapper.selectManWorkByWorkId(manWork.getWorkId())));
    }

    @Override
    public void delCard(JSONObject jsonObject) {
        //获取要删除的车卡
        String carCarNo = (String) jsonObject.get("carCardNo");
        //删除车卡的工单信息
        ManWork workInfo = workMapper.selectManWorkByworkNo((String) jsonObject.get("workNo"));
        String[] split = workInfo.getCarCard().split(",");
        List<String> list = Arrays.asList(split);
        int carCardIndex = getCarCardIndex(list, carCarNo);
        List<String> list1 = new ArrayList<>(list);
        //删除工单中的车卡
        list1.remove(carCardIndex);
        StringBuilder carCardlongStr = new StringBuilder();
        //删除后重新拼接工单下的车卡
        for (int i = 0; i < list1.size(); i++) {
            String cardNo = list1.get(i);
            if (i == 0) {
                carCardlongStr = new StringBuilder(cardNo);
            } else {
                carCardlongStr.append(",").append(cardNo);
            }
        }
        /***************更新车卡绑定信息**********************/
        //更新工单绑定车卡信息
        ManWork manWork = new ManWork();
        manWork.setCarCard(carCardlongStr.toString());
        manWork.setWorkId(workInfo.getWorkId());
        workMapper.updateManWork(manWork);
        //更新工单下的厂商人员车卡信息
        ManFactory manFactory = new ManFactory();
        manFactory.setWorkNo((String) jsonObject.get("workNo"));
        manFactory.setCarCardNo(carCardlongStr.toString());
        manFactoryMapper.updateManFactoryByWorkNo(manFactory);

        /************解绑后更新车卡状态**********************/
        //修改车卡状态为未绑定
        CarCard carCard = new CarCard();
        carCard.setCardCarNo(carCarNo);
        carCard.setCardCarStatus("0");
        carCard.setCardCarUse("");
        carCard.setBindPlateNo(null);
        carCard.setReturnName(workInfo.getProjectName());
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
        cardRecord.setCardRecordObject(workInfo.getProjectName());
        cardRecord.setCardRecordTime(new Date());
        cardRecord.setCardRecordName(SecurityUtils.getLoginUser().getUsername());
        cardRecordMapper.insertCardRecord(cardRecord);
        // 工单解绑车卡
        pool.threadPoolTaskExecutor().execute(() -> carCardSendService.downSendUnbindCarCard(carCarNo));
    }

    /**
     * 根据厂区获取Plc和下属的设备IP
     *
     * @param deptId
     * @return
     */
    @Override
    public String getIp(Long deptId) {

        StringBuffer dept = new StringBuffer();
        //根据厂区获取PLC
        List<PlcEquipment> plcEquipmentByDeptId = plcEquipmentMapper.getPlcEquipmentByDeptId(deptId);
        for (int i = 0; i < plcEquipmentByDeptId.size(); i++) {
            if (i==0){
                dept.append(plcEquipmentByDeptId.get(i).getIp());
            }else {
                dept.append(","+plcEquipmentByDeptId.get(i).getIp());
            }
        }
        return dept.toString();
    }

    public static int getCarCardIndex(List<String> list, String carCarNo) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(carCarNo)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 每日更新長期單，並刪除中間表
     */
    @Transactional(readOnly = false)
    public void resetLongTimeWork() {
        //先全部重置
        manWorkMapper.resetLongTimeWork(new Date());
        List<Long> ids = manWorkMapper.listLongTimeWork();
        for (Long id : ids) {
            //刪除中間表
            manWorkMapper.deleteMidByWorkId(id);
        }
    }


}

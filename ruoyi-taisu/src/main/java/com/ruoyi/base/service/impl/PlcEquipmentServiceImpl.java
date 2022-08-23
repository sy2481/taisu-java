package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.mapper.PlcEquipmentMapper;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.base.utils.PlcRedisUtils;
import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * PLC 道闸Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-05
 */
@Service
public class PlcEquipmentServiceImpl implements IPlcEquipmentService {
    @Autowired
    private PlcEquipmentMapper plcEquipmentMapper;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private PlcRedisUtils plcRedisUtils;

    @Autowired
    private PersonSendService personSendService;
    @Autowired
    private ThreadPoolConfig pool;

    /**
     * 查询PLC 道闸
     *
     * @param id PLC 道闸主键
     * @return PLC 道闸
     */
    @Override
    public PlcEquipment selectPlcEquipmentById(Long id) {
        PlcEquipment plcEquipment = plcEquipmentMapper.selectPlcEquipmentById(id);
        if (StringUtils.isNotBlank(plcEquipment.getTime1())){
            String[] split = plcEquipment.getTime1().split("-");
            if (split.length==2){
                plcEquipment.setStartTime1(split[0]);
                plcEquipment.setEndTime1(split[1]);
            }
        }
        if (StringUtils.isNotBlank(plcEquipment.getTime2())){
            String[] split = plcEquipment.getTime2().split("-");
            if (split.length==2){
                plcEquipment.setStartTime2(split[0]);
                plcEquipment.setEndTime2(split[1]);
            }
        }
        if (StringUtils.isNotBlank(plcEquipment.getTime3())){
            String[] split = plcEquipment.getTime3().split("-");
            if (split.length==2){
                plcEquipment.setStartTime3(split[0]);
                plcEquipment.setEndTime3(split[1]);
            }
        }
        return plcEquipment;
    }

    /**
     * 查询PLC 道闸列表
     *
     * @param plcEquipment PLC 道闸
     * @return PLC 道闸
     */
    @Override
    public List<PlcEquipment> selectPlcEquipmentList(PlcEquipment plcEquipment) {

        //查询当前登录人是否有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList != null) {
            plcEquipment.setDeptIds(longList);
        }
        List<PlcEquipment> plcEquipments = plcEquipmentMapper.selectPlcEquipmentList(plcEquipment);
        //查询部门名称
        plcEquipments.forEach(plcEquipment1 -> {
            if (plcEquipment1.getPlantAreaId() != null) {
                SysDept sysDept = sysDeptService.selectDeptById(plcEquipment1.getPlantAreaId());
                plcEquipment1.setDeptName(sysDept.getDeptName());
            }

        });
        return plcEquipments;
    }

    /**
     * 新增PLC 道闸
     *
     * @param plcEquipment PLC 道闸
     * @return 结果
     */
    @Override
    public int insertPlcEquipment(PlcEquipment plcEquipment) {
        plcEquipment.setCreateTime(DateUtils.getNowDate());
        int i = plcEquipmentMapper.insertPlcEquipment(plcEquipment);
        if (i > 0) {
            pool.threadPoolTaskExecutor().execute(() -> personSendService.equipmentCache());
        }
        return i;
    }

    /**
     * 修改PLC 道闸
     *
     * @param plcEquipment PLC 道闸
     * @return 结果
     */
    @Override
    public int updatePlcEquipment(PlcEquipment plcEquipment) {
        PlcEquipment equipment = plcEquipmentMapper.selectPlcEquipmentById(plcEquipment.getId());
        PlcEquipment plcEquipment1 = plcRedisUtils.getPlcEquipment(equipment.getIp());
        if (plcEquipment1 != null) {
            plcRedisUtils.delPlcEquipment(equipment.getIp());
        }
        plcEquipment.setUpdateTime(DateUtils.getNowDate());
        int i = plcEquipmentMapper.updatePlcEquipment(plcEquipment);
        if (i > 0) {
            pool.threadPoolTaskExecutor().execute(() -> personSendService.equipmentCache());
        }
        return i;
    }

    /**
     * 批量删除PLC 道闸
     *
     * @param ids 需要删除的PLC 道闸主键
     * @return 结果
     */
    @Override
    public int deletePlcEquipmentByIds(Long[] ids) {
        for (Long id : ids) {
            //缓存中存在进行删除
            PlcEquipment plcEquipment = plcEquipmentMapper.selectPlcEquipmentById(id);
            PlcEquipment plcEquipment1 = plcRedisUtils.getPlcEquipment(plcEquipment.getIp());
            if (plcEquipment1 != null) {
                plcRedisUtils.delPlcEquipment(plcEquipment.getIp());
            }
        }
        int i = plcEquipmentMapper.deletePlcEquipmentByIds(ids);
        if (i > 0) {
            pool.threadPoolTaskExecutor().execute(() -> personSendService.equipmentCache());
        }
        return i;
    }

    /**
     * 删除PLC 道闸信息
     *
     * @param id PLC 道闸主键
     * @return 结果
     */
    @Override
    public int deletePlcEquipmentById(Long id) {

        int i = plcEquipmentMapper.deletePlcEquipmentById(id);
        if (i > 0) {
            pool.threadPoolTaskExecutor().execute(() -> personSendService.equipmentCache());
        }
        return i;
    }

    @Override
    public PlcEquipment findByIp(String ip) {
        return plcEquipmentMapper.findByIp(ip);
    }

    @Override
    public List<PlcEquipment> getPlcEquipmentByDept(List<Long> factoryIdList) {
        return plcEquipmentMapper.getPlcEquipmentByDept(factoryIdList);
    }

    @Override
    public List<PlcEquipment> getPlcEquipmentById(List<Long> factoryIdList) {
        return plcEquipmentMapper.getPlcEquipmentById(factoryIdList);
    }

    @Override
    public List<PlcEquipment> getPlcEquipmentByDeptId(Long plantAreaId) {
        return plcEquipmentMapper.getPlcEquipmentByDeptId(plantAreaId);
    }


}

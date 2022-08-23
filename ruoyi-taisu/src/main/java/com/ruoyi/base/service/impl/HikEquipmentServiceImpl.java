package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.domain.PlcHik;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.mapper.HikEquipmentMapper;
import com.ruoyi.base.mapper.PlcEquipmentMapper;
import com.ruoyi.base.service.IHikEquipmentService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.config.ThreadPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * HIK 海康设备Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-06
 */
@Service
public class HikEquipmentServiceImpl implements IHikEquipmentService {
    @Autowired
    private HikEquipmentMapper hikEquipmentMapper;
    @Autowired
    private PlcEquipmentMapper plcEquipmentMapper;
    @Autowired
    private PersonSendService personSendService;
    @Autowired
    private ThreadPoolConfig pool;

    /**
     * 查询HIK 海康设备
     *
     * @param id HIK 海康设备主键
     * @return HIK 海康设备
     */
    @Override
    public HikEquipment selectHikEquipmentById(Long id) {
        return hikEquipmentMapper.selectHikEquipmentById(id);
    }

    /**
     * 查询HIK 海康设备列表
     *
     * @param hikEquipment HIK 海康设备
     * @return HIK 海康设备
     */
    @Override
    public List<HikEquipment> selectHikEquipmentList(HikEquipment hikEquipment) {
        return hikEquipmentMapper.selectHikEquipmentList(hikEquipment);
    }

    /**
     * 新增HIK 海康设备
     *
     * @param hikEquipment HIK 海康设备
     * @return 结果
     */
    @Override
    public int insertHikEquipment(HikEquipment hikEquipment) {
        hikEquipment.setCreateTime(DateUtils.getNowDate());
        int i = hikEquipmentMapper.insertHikEquipment(hikEquipment);
        if (i>0){
            pool.threadPoolTaskExecutor().execute(() ->  personSendService.equipmentCache());
        }

        return i;
    }

    /**
     * 修改HIK 海康设备
     *
     * @param hikEquipment HIK 海康设备
     * @return 结果
     */
    @Override
    public int updateHikEquipment(HikEquipment hikEquipment) {
        hikEquipment.setUpdateTime(DateUtils.getNowDate());
        int i = hikEquipmentMapper.updateHikEquipment(hikEquipment);
        if (i>0){

            pool.threadPoolTaskExecutor().execute(() ->  personSendService.equipmentCache());
        }
        return hikEquipmentMapper.updateHikEquipment(hikEquipment);
    }

    /**
     * 批量删除HIK 海康设备
     *
     * @param ids 需要删除的HIK 海康设备主键
     * @return 结果
     */
    @Override
    public int deleteHikEquipmentByIds(Long[] ids) {
        int i = hikEquipmentMapper.deleteHikEquipmentByIds(ids);
        if (i>0){
            pool.threadPoolTaskExecutor().execute(() ->  personSendService.equipmentCache());
        }
        return i;
    }

    /**
     * 删除HIK 海康设备信息
     *
     * @param id HIK 海康设备主键
     * @return 结果
     */
    @Override
    public int deleteHikEquipmentById(Long id) {
        int i = hikEquipmentMapper.deleteHikEquipmentById(id);
        if (i>0){
            pool.threadPoolTaskExecutor().execute(() ->  personSendService.equipmentCache());
        }
        return hikEquipmentMapper.deleteHikEquipmentById(id);
    }


    @Override
    public List<PlcHik> listRelationEquip(Long plcId) {
        return hikEquipmentMapper.listRelationEquip(plcId);
    }

    @Override
    public PlcHik getPlcHilRelation(Long id) {
        return hikEquipmentMapper.getPlcHilRelation(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int insertPlcHik(PlcHik plcHik) {
        //插入中間表，還需要去更新設備表裏的一些字段
        plcHik.setCreateTime(DateUtils.getNowDate());
        hikEquipmentMapper.insertPlcHik(plcHik);
        //更新front IP
        PlcEquipment plcEquipment = plcEquipmentMapper.selectPlcEquipmentById(plcHik.getPlcId());
        if (plcEquipment == null) {
            throw new RuntimeException("未查询到PLC设备");
        }
        hikEquipmentMapper.updateFrontIpById(plcHik.getCarDeviceId(), plcEquipment.getIp());
        hikEquipmentMapper.updateFrontIpById(plcHik.getPersonDeviceId(), plcEquipment.getIp());
        //发送设备重置
        pool.threadPoolTaskExecutor().execute(() ->  personSendService.equipmentCache());
        return 1;
    }

    @Override
    @Transactional(readOnly = false)
    public int deletePlcHikById(Long id) {
        //删除中间表
        PlcHik plcHik = hikEquipmentMapper.getPlcHilRelation(id);
        if (plcHik == null) {
            return 0;
        }
        //重置 ip 字段
        hikEquipmentMapper.updateFrontIpById(plcHik.getCarDeviceId(), null);
        hikEquipmentMapper.updateFrontIpById(plcHik.getPersonDeviceId(), null);
        //再删除中间表
        hikEquipmentMapper.deletePlcHikById(id);
        pool.threadPoolTaskExecutor().execute(() ->  personSendService.equipmentCache());
        return 1;
    }

    @Override
    public int updatePlcHik(PlcHik plcHik) {
        //先更新
        hikEquipmentMapper.updatePlcHik(plcHik);
        //更新front IP
        PlcEquipment plcEquipment = plcEquipmentMapper.selectPlcEquipmentById(plcHik.getPlcId());
        if (plcEquipment == null) {
            throw new RuntimeException("未查询到PLC设备");
        }
        hikEquipmentMapper.updateFrontIpById(plcHik.getCarDeviceId(), plcEquipment.getIp());
        hikEquipmentMapper.updateFrontIpById(plcHik.getPersonDeviceId(), plcEquipment.getIp());
        pool.threadPoolTaskExecutor().execute(() ->  personSendService.equipmentCache());
        return 1;
    }

    @Override
    public List<HikEquipment> findByIp(String ip) {
        return hikEquipmentMapper.findByIp(ip);
    }

    /**
     * 查询HIK 未綁定的海康設備
     *
     * @param hikEquipment HIK 海康設備
     * @return HIK 海康設備
     */
    @Override
    public List<HikEquipment> selectHikEquipmentNotBindList(HikEquipment hikEquipment) {
        return hikEquipmentMapper.selectHikEquipmentNotBindList(hikEquipment);
    }
}

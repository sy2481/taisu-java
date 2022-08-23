package com.ruoyi.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.base.domain.*;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.service.IBasePassagewayOperationService;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SysDeptMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.service.IBasePassagewayService;

/**
 * 通道Service業務層處理
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
@Service
public class BasePassagewayServiceImpl implements IBasePassagewayService 
{
    @Autowired
    private BasePassagewayMapper basePassagewayMapper;

    @Autowired
    private BaseDoorMapper baseDoorMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private HikEquipmentMapper hikEquipmentMapper;

    @Autowired
    private BasePassagewayEquipmentMapper basePassagewayEquipmentMapper;

    @Autowired
    private BasePassagewayOperationMapper basePassagewayOperationMapper;

    @Autowired
    private IBasePassagewayOperationService basePassagewayOperationService;

    /**
     * 查詢通道
     * 
     * @param id 通道主鍵
     * @return 通道
     */
    @Override
    public BasePassageway selectBasePassagewayById(Long id)
    {
        BasePassageway basePassageway = basePassagewayMapper.selectBasePassagewayById(id);

        BasePassagewayOperation basePassagewayOperation = new BasePassagewayOperation();
        basePassagewayOperation.setPassagewayId(id);
        List<BasePassagewayOperation> basePassagewayOperationList = basePassagewayOperationService.selectBasePassagewayOperationList(basePassagewayOperation);
        basePassageway.setBasePassagewayOperationList(basePassagewayOperationList);

        BasePassagewayEquipment basePassagewayEquipment = new BasePassagewayEquipment();
        basePassagewayEquipment.setPassagewayId(id);
        List<BasePassagewayEquipment> basePassagewayEquipmentList = basePassagewayEquipmentMapper.selectBasePassagewayEquipmentList(basePassagewayEquipment);
        List<Long> hikEquipmentIds = basePassagewayEquipmentList.stream().map(o->o.getHikEquipmentId()).collect(Collectors.toList());
//        List<String> hikEquipmentNames = new ArrayList<>();
        List<HikEquipment> hikEquipmentList = new ArrayList<>();
        for (Long hikEquipmentId : hikEquipmentIds){
            HikEquipment hikEquipment = hikEquipmentMapper.selectHikEquipmentById(hikEquipmentId);
            hikEquipmentList.add(hikEquipment);
        }
        basePassageway.setHikEquipmentList(hikEquipmentList);
        return basePassageway;
    }

    /**
     * 查詢通道列表
     * 
     * @param basePassageway 通道
     * @return 通道
     */
    @Override
    public List<BasePassageway> selectBasePassagewayList(BasePassageway basePassageway)
    {
        return basePassagewayMapper.selectBasePassagewayList(basePassageway);
    }

    /**
     * 查詢通道詳情列表
     *
     * @param basePassageway 通道
     * @return 通道
     */
    @Override
    public List<BasePassageway> selectBasePassagewayDetailList(BasePassageway basePassageway)
    {
        List<BasePassageway> basePassagewayList = basePassagewayMapper.selectBasePassagewayList(basePassageway);

        List<BasePassageway> newBasePassagewayList = new ArrayList<>();

        for (BasePassageway passageway : basePassagewayList){
            passageway = selectBasePassagewayById(passageway.getPassagewayId());
            newBasePassagewayList.add(passageway);
        }

        return newBasePassagewayList;
    }


    /**
     * 新增通道
     * 
     * @param basePassageway 通道
     * @return 結果
     */
    @Override
    public int insertBasePassageway(BasePassageway basePassageway)
    {
        int result = 0;
        basePassageway.setCreateTime(DateUtils.getNowDate());

        BaseDoor baseDoor = baseDoorMapper.selectBaseDoorById(basePassageway.getDoorId());
        if(baseDoor == null){
            return result;
        }
        SysDept sysDept = sysDeptMapper.selectDeptById(baseDoor.getPlantAreaId());

        basePassageway.setPlantAreaName(sysDept.getDeptName());
        basePassageway.setType(baseDoor.getType());
        basePassageway.setEnable(0L);
        result += basePassagewayMapper.insertBasePassageway(basePassageway);

        return result;
    }

    /**
     * 修改通道
     * 
     * @param basePassageway 通道
     * @return 結果
     */
    @Override
    public int updateBasePassageway(BasePassageway basePassageway)
    {
        basePassageway.setUpdateTime(DateUtils.getNowDate());
        return basePassagewayMapper.updateBasePassageway(basePassageway);
    }

    /**
     * 批量刪除通道
     * 
     * @param ids 需要刪除的通道主鍵
     * @return 結果
     */
    @Override
    public int deleteBasePassagewayByIds(Long[] ids)
    {
        for (Long id : ids){
            basePassagewayEquipmentMapper.deleteBasePassagewayEquipmentById(id);

            BasePassagewayOperation basePassagewayOperation = new BasePassagewayOperation();
            basePassagewayOperation.setPassagewayId(id);
            List<BasePassagewayOperation> basePassagewayOperationList = basePassagewayOperationMapper.selectBasePassagewayOperationList(basePassagewayOperation);
            List<Long> operationIds = basePassagewayOperationList.stream().map(o->o.getOperationId()).collect(Collectors.toList());
            for(Long operationId : operationIds){
                basePassagewayOperationMapper.deleteBasePassagewayOperationByOperationId(operationId);
            }

        }
        return basePassagewayMapper.deleteBasePassagewayByIds(ids);
    }

    /**
     * 刪除通道信息
     * 
     * @param id 通道主鍵
     * @return 結果
     */
    @Override
    public int deleteBasePassagewayById(Long id)
    {
        return basePassagewayMapper.deleteBasePassagewayById(id);
    }

    /**
     * 批量綁定設備
     *
     * @param bindPassagewayEquipment 通道綁定設備
     * @return 結果
     */
    @Override
    public int bindHikEquipmentById(BindPassagewayEquipment bindPassagewayEquipment)
    {
        int result = 0;

        List<BasePassagewayEquipment> basePassagewayEquipments = new ArrayList<>();

        BasePassageway basePassageway = basePassagewayMapper.selectBasePassagewayById(bindPassagewayEquipment.getPassagewayId());

        BasePassagewayEquipment basePassagewayEquipmentParam = new BasePassagewayEquipment();
        basePassagewayEquipmentParam.setPassagewayId(bindPassagewayEquipment.getPassagewayId());
        List<BasePassagewayEquipment> oldPassagewayEquipment = basePassagewayEquipmentMapper.selectBasePassagewayEquipmentList(basePassagewayEquipmentParam);

        if(oldPassagewayEquipment.size()>0){
            for (BasePassagewayEquipment basePassagewayEquipment1 : oldPassagewayEquipment){
                basePassagewayEquipmentMapper.deleteBasePassagewayEquipmentById(basePassagewayEquipment1.getPassagewayId());
            }
        }

        if(bindPassagewayEquipment.getHikEquipmentIds()!=null){
            for(Long hikEquipmentId : bindPassagewayEquipment.getHikEquipmentIds()) {
                HikEquipment hikEquipment = hikEquipmentMapper.selectHikEquipmentById(hikEquipmentId);
                if(hikEquipment==null){
                    continue;
                }
                if(hikEquipment.getSign().equals(1L)){
                    if(!basePassageway.getInOutMode().equals(BasePassageway.inOutModeEnum.入.toString())){
                        continue;
                    }
                } else if(hikEquipment.getSign().equals(2L)){
                    if(!basePassageway.getInOutMode().equals(BasePassageway.inOutModeEnum.出.toString())){
                        continue;
                    }
                }
                BasePassagewayEquipment basePassagewayEquipment = basePassagewayEquipmentMapper.selectBasePassagewayEquipmentByHikEquipmentId(hikEquipmentId);
                if(basePassagewayEquipment == null){
                    BasePassagewayEquipment newBasePassagewayEquipment = new BasePassagewayEquipment();
                    newBasePassagewayEquipment.setPassagewayId(basePassageway.getPassagewayId());
                    newBasePassagewayEquipment.setHikEquipmentId(hikEquipmentId);

                    basePassagewayEquipments.add(newBasePassagewayEquipment);
                }
            }
        }

        if(basePassagewayEquipments.size() >0 ){
            if(basePassageway.getMode().equals(BasePassageway.modeEnum.設備控制.toString())){
                basePassageway.setEnable(1L);
            }
            result += basePassagewayEquipmentMapper.batchInsertBasePassagewayEquipment(basePassagewayEquipments);
            result += basePassagewayMapper.updateBasePassageway(basePassageway);
        }
        else{
            basePassageway.setEnable(0L);
            result += basePassagewayMapper.updateBasePassageway(basePassageway);
        }

        return result;

    }

    /**
     * 查詢通道下綁定的設備
     *
     * @param id 通道主鍵
     * @return 結果
     */
    @Override
    public List<HikEquipment> selectPassageEquipment(Long id)
    {
        List<HikEquipment> hikEquipments = new ArrayList<>();
        BasePassageway basePassageway = basePassagewayMapper.selectBasePassagewayById(id);

        BasePassagewayEquipment basePassagewayEquipment = new BasePassagewayEquipment();
        basePassagewayEquipment.setPassagewayId(id);
        List<BasePassagewayEquipment> basePassagewayEquipments = basePassagewayEquipmentMapper.selectBasePassagewayEquipmentList(basePassagewayEquipment);
        List<Long> equipmentIds = basePassagewayEquipments.stream().map(o->o.getHikEquipmentId()).collect(Collectors.toList());
        for (Long equipmentId : equipmentIds){
            HikEquipment hikEquipment = hikEquipmentMapper.selectHikEquipmentById(equipmentId);
            hikEquipments.add(hikEquipment);
        }
        return hikEquipments;
    }

    /**
     * 查詢模式
     *
     * @return 結果
     */
    @Override
    public List<String> selectModeEnum() {
        List<String> list = new ArrayList<>();
        for (BasePassageway.modeEnum e : BasePassageway.modeEnum.values()) {
            list.add(e.toString());
        }
        return list;
    }

    /**
     * 查詢方向
     *
     * @return 結果
     */
    @Override
    public List<String> selectDirectionEnum() {
        List<String> list = new ArrayList<>();
        for (BasePassageway.inOutModeEnum e : BasePassageway.inOutModeEnum.values()) {
            list.add(e.toString());
        }
        return list;
    }
}

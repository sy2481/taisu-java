package com.ruoyi.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.base.domain.*;
import com.ruoyi.base.mapper.*;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.service.IBaseDoorService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 門Service業務層處理
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
@Service
public class BaseDoorServiceImpl implements IBaseDoorService 
{
    @Autowired
    private BaseDoorMapper baseDoorMapper;

    @Autowired
    private BasePassagewayMapper basePassagewayMapper;

    @Autowired
    private BasePassagewayEquipmentMapper basePassagewayEquipmentMapper;

    @Autowired
    private BasePassagewayOperationMapper basePassagewayOperationMapper;

    /**
     * 查詢門
     * 
     * @param id 門主鍵
     * @return 門
     */
    @Override
    public BaseDoor selectBaseDoorById(Long id)
    {
        return baseDoorMapper.selectBaseDoorById(id);
    }

    /**
     * 查詢門列表
     * 
     * @param baseDoor 門
     * @return 門
     */
    @Override
    public List<BaseDoor> selectBaseDoorList(BaseDoor baseDoor)
    {
        return baseDoorMapper.selectBaseDoorList(baseDoor);
    }

    /**
     * 新增門
     * 
     * @param baseDoor 門
     * @return 結果
     */
    @Override
    public int insertBaseDoor(BaseDoor baseDoor)
    {
        baseDoor.setCreateTime(DateUtils.getNowDate());
        return baseDoorMapper.insertBaseDoor(baseDoor);
    }

    /**
     * 修改門
     * 
     * @param baseDoor 門
     * @return 結果
     */
    @Override
    public int updateBaseDoor(BaseDoor baseDoor)
    {
        baseDoor.setUpdateTime(DateUtils.getNowDate());
        return baseDoorMapper.updateBaseDoor(baseDoor);
    }

    /**
     * 批量刪除門
     * 
     * @param ids 需要刪除門的主鍵
     * @return 結果
     */
    @Override
    public int deleteBaseDoorByIds(Long[] ids)
    {
        for (Long id : ids){
            BaseDoor baseDoor = baseDoorMapper.selectBaseDoorById(id);
            BasePassageway basePassagewayParam = new BasePassageway();
            basePassagewayParam.setDoorId(baseDoor.getDoorId());
            List<BasePassageway> basePassagewayList = basePassagewayMapper.selectBasePassagewayList(basePassagewayParam);
            for (BasePassageway basePassageway : basePassagewayList){
                basePassagewayEquipmentMapper.deleteBasePassagewayEquipmentById(basePassageway.getPassagewayId());
                BasePassagewayOperation basePassagewayOperation = new BasePassagewayOperation();
                basePassagewayOperation.setPassagewayId(basePassageway.getPassagewayId());
                List<BasePassagewayOperation> basePassagewayOperationList = basePassagewayOperationMapper.selectBasePassagewayOperationList(basePassagewayOperation);
                List<Long> operationIds = basePassagewayOperationList.stream().map(o->o.getOperationId()).collect(Collectors.toList());
                for(Long operationId : operationIds){
                    basePassagewayOperationMapper.deleteBasePassagewayOperationByOperationId(operationId);
                }
                basePassagewayMapper.deleteBasePassagewayById(basePassageway.getPassagewayId());
            }
        }
        return baseDoorMapper.deleteBaseDoorByIds(ids);
    }

    /**
     * 刪除門信息
     * 
     * @param id 門主鍵
     * @return 結果
     */
    @Override
    public int deleteBaseDoorById(Long id)
    {
        return baseDoorMapper.deleteBaseDoorById(id);
    }

    /**
     * 查詢類型
     *
     * @return 結果
     */
    @Override
    public List<String> selectTypeEnum() {
        List<String> list = new ArrayList<>();
        for (BaseDoor.typeEnum e : BaseDoor.typeEnum.values()) {
            list.add(e.toString());
        }
        return list;
    }
}

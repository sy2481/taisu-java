package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.base.domain.*;
import com.ruoyi.base.mapper.PlcCommandMapper;
import com.ruoyi.base.mapper.PlcCommandOperationSortMapper;
import com.ruoyi.base.mapper.PlcEquipmentMapper;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.plc.plcClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BasePassagewayOperationMapper;
import com.ruoyi.base.service.IBasePassagewayOperationService;

/**
 * 通道操作Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
@Service
public class BasePassagewayOperationServiceImpl implements IBasePassagewayOperationService 
{
    @Autowired
    private BasePassagewayOperationMapper basePassagewayOperationMapper;

    @Autowired
    private PlcCommandOperationSortMapper plcCommandOperationSortMapper;

    @Autowired
    private PlcCommandMapper plcCommandMapper;

    @Autowired
    private PlcEquipmentMapper plcEquipmentMapper;

    /**
     * 查询通道操作
     * 
     * @param operationId 通道操作主键
     * @return 通道操作
     */
    @Override
    public BasePassagewayOperation selectBasePassagewayOperationByOperationId(Long operationId)
    {
        return basePassagewayOperationMapper.selectBasePassagewayOperationByOperationId(operationId);
    }

    /**
     * 查询通道操作列表
     * 
     * @param basePassagewayOperation 通道操作
     * @return 通道操作
     */
    @Override
    public List<BasePassagewayOperation> selectBasePassagewayOperationList(BasePassagewayOperation basePassagewayOperation)
    {
        List<BasePassagewayOperation> basePassagewayOperationList = basePassagewayOperationMapper.selectBasePassagewayOperationList(basePassagewayOperation);
        for(BasePassagewayOperation passagewayOperation : basePassagewayOperationList){
            PlcCommandOperationSort plcCommandOperationSort = new PlcCommandOperationSort();
            plcCommandOperationSort.setOperationId(passagewayOperation.getOperationId());
            List<PlcCommandOperationSort> plcCommandOperationSortList = plcCommandOperationSortMapper.selectPlcCommandOperationSortList(plcCommandOperationSort);
            passagewayOperation.setPlcCommandOperationSortList(plcCommandOperationSortList);
        }
        return basePassagewayOperationList;
    }

    /**
     * 新增通道操作
     * 
     * @param basePassagewayOperation 通道操作
     * @return 结果
     */
    @Override
    public int insertBasePassagewayOperation(BasePassagewayOperation basePassagewayOperation)
    {
        basePassagewayOperation.setCreateTime(DateUtils.getNowDate());
        return basePassagewayOperationMapper.insertBasePassagewayOperation(basePassagewayOperation);
    }

    /**
     * 修改通道操作
     * 
     * @param basePassagewayOperation 通道操作
     * @return 结果
     */
    @Override
    public int updateBasePassagewayOperation(BasePassagewayOperation basePassagewayOperation)
    {
        basePassagewayOperation.setUpdateTime(DateUtils.getNowDate());
        return basePassagewayOperationMapper.updateBasePassagewayOperation(basePassagewayOperation);
    }

    /**
     * 批量删除通道操作
     * 
     * @param operationIds 需要删除的通道操作主键
     * @return 结果
     */
    @Override
    public int deleteBasePassagewayOperationByOperationIds(Long[] operationIds)
    {
        return basePassagewayOperationMapper.deleteBasePassagewayOperationByOperationIds(operationIds);
    }

    /**
     * 删除通道操作信息
     * 
     * @param operationId 通道操作主键
     * @return 结果
     */
    @Override
    public int deleteBasePassagewayOperationByOperationId(Long operationId)
    {
        return basePassagewayOperationMapper.deleteBasePassagewayOperationByOperationId(operationId);
    }

    /**
     * 发送PLC指令
     *
     * @param operationId 通道操作主键
     * @return 结果
     */
    @Override
    public int sendPLCCommand(Long operationId)
    {
        int result = 0;
        PlcCommandOperationSort plcCommandOperationSortParam = new PlcCommandOperationSort();
        plcCommandOperationSortParam.setOperationId(operationId);
        List<PlcCommandOperationSort> plcCommandOperationSortList = plcCommandOperationSortMapper.selectPlcCommandOperationSortList(plcCommandOperationSortParam);
        if(plcCommandOperationSortList.size() == 0){
            return result;
        }
        plcClientUtils plcClientUtils = new plcClientUtils();
        Long plcId = plcCommandOperationSortList.get(0).getPlcId();
        PlcEquipment plcEquipment = plcEquipmentMapper.selectPlcEquipmentById(plcId);
        String ip = plcEquipment.getIp();
        Integer port = Convert.toInt(plcEquipment.getPort());
        //连接plc
        result = plcClientUtils.initConnect(ip,port);
        if(result == 1){
            for(PlcCommandOperationSort plcCommandOperationSort : plcCommandOperationSortList){
                PlcCommand plcCommand = plcCommandMapper.selectPlcCommandById(plcCommandOperationSort.getCommandId());
                //调用发送方法
                result = plcClientUtils.send(plcEquipment.getIp(),plcCommand.getCommand());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(result == 1){
            plcClientUtils.destroyChannel();
        }
        return result;
    }

    public int testSend(SendPlcCommandParam sendPlcCommandParam)
    {
        int result = 0;
        String ip = sendPlcCommandParam.getIp();
        String command = sendPlcCommandParam.getCommand();
        Integer port = sendPlcCommandParam.getPort();
        plcClientUtils plcClientUtils = new plcClientUtils();
        //连接plc
        result = plcClientUtils.initConnect(ip,port);
        if(result == 1){
            //调用发送方法
            result = plcClientUtils.send(ip,command);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(result == 1){
                plcClientUtils.destroyChannel();
            }
        }
        return result;
    }
}

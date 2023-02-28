package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.HcHrWorkOrderMapper;
import com.ruoyi.base.domain.HcHrWorkOrder;
import com.ruoyi.base.service.IHcHrWorkOrderService;

/**
 * 歷史危化施工單Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcHrWorkOrderServiceImpl implements IHcHrWorkOrderService 
{
    @Autowired
    private HcHrWorkOrderMapper hcHrWorkOrderMapper;

    /**
     * 查询歷史危化施工單
     * 
     * @param id 歷史危化施工單主键
     * @return 歷史危化施工單
     */
    @Override
    public HcHrWorkOrder selectHcHrWorkOrderById(Long id)
    {
        return hcHrWorkOrderMapper.selectHcHrWorkOrderById(id);
    }

    /**
     * 查询歷史危化施工單列表
     * 
     * @param hcHrWorkOrder 歷史危化施工單
     * @return 歷史危化施工單
     */
    @Override
    public List<HcHrWorkOrder> selectHcHrWorkOrderList(HcHrWorkOrder hcHrWorkOrder)
    {
        return hcHrWorkOrderMapper.selectHcHrWorkOrderList(hcHrWorkOrder);
    }

    /**
     * 新增歷史危化施工單
     * 
     * @param hcHrWorkOrder 歷史危化施工單
     * @return 结果
     */
    @Override
    public int insertHcHrWorkOrder(HcHrWorkOrder hcHrWorkOrder)
    {
        hcHrWorkOrder.setCreateTime(DateUtils.getNowDate());
        return hcHrWorkOrderMapper.insertHcHrWorkOrder(hcHrWorkOrder);
    }

    /**
     * 修改歷史危化施工單
     * 
     * @param hcHrWorkOrder 歷史危化施工單
     * @return 结果
     */
    @Override
    public int updateHcHrWorkOrder(HcHrWorkOrder hcHrWorkOrder)
    {
        hcHrWorkOrder.setUpdateTime(DateUtils.getNowDate());
        return hcHrWorkOrderMapper.updateHcHrWorkOrder(hcHrWorkOrder);
    }

    /**
     * 批量删除歷史危化施工單
     * 
     * @param ids 需要删除的歷史危化施工單主键
     * @return 结果
     */
    @Override
    public int deleteHcHrWorkOrderByIds(Long[] ids)
    {
        return hcHrWorkOrderMapper.deleteHcHrWorkOrderByIds(ids);
    }

    /**
     * 删除歷史危化施工單信息
     * 
     * @param id 歷史危化施工單主键
     * @return 结果
     */
    @Override
    public int deleteHcHrWorkOrderById(Long id)
    {
        return hcHrWorkOrderMapper.deleteHcHrWorkOrderById(id);
    }


}

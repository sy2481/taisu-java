package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.HcHrWorkOrder;

/**
 * 歷史危化施工單Service接口
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcHrWorkOrderService 
{
    /**
     * 查询歷史危化施工單
     * 
     * @param id 歷史危化施工單主键
     * @return 歷史危化施工單
     */
    public HcHrWorkOrder selectHcHrWorkOrderById(Long id);

    /**
     * 查询歷史危化施工單列表
     * 
     * @param hcHrWorkOrder 歷史危化施工單
     * @return 歷史危化施工單集合
     */
    public List<HcHrWorkOrder> selectHcHrWorkOrderList(HcHrWorkOrder hcHrWorkOrder);

    /**
     * 新增歷史危化施工單
     * 
     * @param hcHrWorkOrder 歷史危化施工單
     * @return 结果
     */
    public int insertHcHrWorkOrder(HcHrWorkOrder hcHrWorkOrder);

    /**
     * 修改歷史危化施工單
     * 
     * @param hcHrWorkOrder 歷史危化施工單
     * @return 结果
     */
    public int updateHcHrWorkOrder(HcHrWorkOrder hcHrWorkOrder);

    /**
     * 批量删除歷史危化施工單
     * 
     * @param ids 需要删除的歷史危化施工單主键集合
     * @return 结果
     */
    public int deleteHcHrWorkOrderByIds(Long[] ids);

    /**
     * 删除歷史危化施工單信息
     * 
     * @param id 歷史危化施工單主键
     * @return 结果
     */
    public int deleteHcHrWorkOrderById(Long id);
}

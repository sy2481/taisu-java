package com.ruoyi.base.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcWorkOrder;
import com.ruoyi.common.core.domain.entity.SysDept;

/**
 * 危化施工單Service接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcWorkOrderService
{
    /**
     * 查询危化施工單
     *
     * @param id 危化施工單主键
     * @return 危化施工單
     */
    public HcWorkOrder selectHcWorkOrderById(Long id);

    /**
     * 查询危化施工單列表
     *
     * @param hcWorkOrder 危化施工單
     * @return 危化施工單集合
     */
    public List<HcWorkOrder> selectHcWorkOrderList(HcWorkOrder hcWorkOrder);

    /**
     * 新增危化施工單
     *
     * @param hcWorkOrder 危化施工單
     * @return 结果
     */
    public int insertHcWorkOrder(HcWorkOrder hcWorkOrder);

    /**
     * 修改危化施工單
     *
     * @param hcWorkOrder 危化施工單
     * @return 结果
     */
    public int updateHcWorkOrder(HcWorkOrder hcWorkOrder);

    /**
     * 批量删除危化施工單
     *
     * @param ids 需要删除的危化施工單主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderByIds(Long[] ids);

    /**
     * 删除危化施工單信息
     *
     * @param id 危化施工單主键
     * @return 结果
     */
    public int deleteHcWorkOrderById(Long id);

    /**
     * 從ERP同步數據
     *
     * @param boList
     * @return
     */
    public int syncHcErp(List<V0NBRKX5Bo> boList,String addOrUpdate);

    /**
     * 從ERP同步數據
     *
     * @param boList
     * @return
     */
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant);

    /**
     * 獲取實體map
     *
     * @return
     */
    public Map<String, HcWorkOrder> getEntityMap(List<String> vhNoList);

    /**
     * 查询危化施工單
     *
     * @param vhNo 危化工单
     * @return 危化施工單
     */
    public HcWorkOrder selectHcWorkOrderByVhNo(String vhNo);

    /**
     * 轉入歷史表
     * @return
     */
    public int toHr();

}

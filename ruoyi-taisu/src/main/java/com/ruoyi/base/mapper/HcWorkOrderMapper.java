package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.HcWorkOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 危化施工單Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcWorkOrderMapper
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
     * 删除危化施工單
     *
     * @param id 危化施工單主键
     * @return 结果
     */
    public int deleteHcWorkOrderById(Long id);

    /**
     * 批量删除危化施工單
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderByIds(Long[] ids);

    /**
     * 查詢危化施工單
     * @param vhNos
     * @return
     */
    public List<HcWorkOrder> selectHcWorkOrderListByVhNos(String[] vhNos);

    /**
     * 批量新增
     *
     * @param list 車
     * @return 结果
     */
    public int batchInsertHcWorkOrder(@Param("list") List<HcWorkOrder> list);

    /**
     * 批量修改
     *
     * @param list 車
     * @return 结果
     */
    public int batchUpdateHcWorkOrder(@Param("list") List<HcWorkOrder> list);

    /**
     * 查詢危化施工單
     * @param vhNo
     * @return
     */
    public HcWorkOrder selectHcWorkOrderByVhNo(String vhNo);
}

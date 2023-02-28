package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.HcHrWorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 歷史危化施工單Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcHrWorkOrderMapper {
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
     * 删除歷史危化施工單
     *
     * @param id 歷史危化施工單主键
     * @return 结果
     */
    public int deleteHcHrWorkOrderById(Long id);

    /**
     * 批量删除歷史危化施工單
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcHrWorkOrderByIds(Long[] ids);

    /**
     * 查詢歷史危化施工單
     *
     * @param vhNos
     * @return
     */
    public List<HcHrWorkOrder> selectHcHrWorkOrderListByVhNos(String[] vhNos);

    /**
     * 批量新增
     *
     * @param list 車
     * @return 结果
     */
    public int batchInsertHcHrWorkOrder(@Param("list") List<HcHrWorkOrder> list);


}

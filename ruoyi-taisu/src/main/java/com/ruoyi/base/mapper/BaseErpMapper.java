package com.ruoyi.base.mapper;


import com.ruoyi.base.domain.BaseErp;

import java.util.List;

/**
 * erp工单Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-16
 */
public interface BaseErpMapper {
    /**
     * 查询erp工单
     *
     * @param vhNo erp工单主键
     * @return erp工单
     */
    public BaseErp selectBaseErpByVhNo(String vhNo);

    /**
     * 查询erp工单列表
     *
     * @param baseErp erp工单
     * @return erp工单集合
     */
    public List<BaseErp> selectBaseErpList(BaseErp baseErp);

    /**
     * 新增erp工单
     *
     * @param baseErp erp工单
     * @return 结果
     */
    public int insertBaseErp(BaseErp baseErp);

    /**
     * 修改erp工单
     *
     * @param baseErp erp工单
     * @return 结果
     */
    public int updateBaseErp(BaseErp baseErp);

    /**
     * 删除erp工单
     *
     * @param vhNo erp工单主键
     * @return 结果
     */
    public int deleteBaseErpByVhNo();

    /**
     * 批量删除erp工单
     *
     * @param vhNos 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseErpByVhNos(String[] vhNos);

    public int selectCount();
}



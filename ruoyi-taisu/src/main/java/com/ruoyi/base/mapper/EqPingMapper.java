package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.EqPing;
import org.apache.ibatis.annotations.Param;

/**
 * 設備檢測記錄Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public interface EqPingMapper 
{
    /**
     * 查询設備檢測記錄
     * 
     * @param id 設備檢測記錄主键
     * @return 設備檢測記錄
     */
    public EqPing selectEqPingById(Long id);

    /**
     * 查询設備檢測記錄列表
     * 
     * @param eqPing 設備檢測記錄
     * @return 設備檢測記錄集合
     */
    public List<EqPing> selectEqPingList(EqPing eqPing);

    /**
     * 新增設備檢測記錄
     * 
     * @param eqPing 設備檢測記錄
     * @return 结果
     */
    public int insertEqPing(EqPing eqPing);

    /**
     * 修改設備檢測記錄
     * 
     * @param eqPing 設備檢測記錄
     * @return 结果
     */
    public int updateEqPing(EqPing eqPing);

    /**
     * 删除設備檢測記錄
     * 
     * @param id 設備檢測記錄主键
     * @return 结果
     */
    public int deleteEqPingById(Long id);

    /**
     * 批量删除設備檢測記錄
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEqPingByIds(Long[] ids);

    /**
     * 那句設備查询設備檢測記錄
     *
     * @return 設備檢測記錄
     */
    public EqPing selectEqPingByEqDevice(@Param("eqType") String eqType, @Param("eqId") Long eqId);
}

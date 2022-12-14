package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.EqStateRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 設備在線記錄Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public interface EqStateRecordMapper 
{
    /**
     * 查询設備在線記錄
     * 
     * @param id 設備在線記錄主键
     * @return 設備在線記錄
     */
    public EqStateRecord selectEqStateRecordById(Long id);

    /**
     * 查询設備在線記錄列表
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 設備在線記錄集合
     */
    public List<EqStateRecord> selectEqStateRecordList(EqStateRecord eqStateRecord);

    /**
     * 新增設備在線記錄
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 结果
     */
    public int insertEqStateRecord(EqStateRecord eqStateRecord);

    /**
     * 修改設備在線記錄
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 结果
     */
    public int updateEqStateRecord(EqStateRecord eqStateRecord);

    /**
     * 删除設備在線記錄
     * 
     * @param id 設備在線記錄主键
     * @return 结果
     */
    public int deleteEqStateRecordById(Long id);

    /**
     * 批量删除設備在線記錄
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEqStateRecordByIds(Long[] ids);

    public List<EqStateRecord> selectEqStateRecordListDescByEqDevice(@Param("eqType") String eqType, @Param("eqId") Long eqId);


}

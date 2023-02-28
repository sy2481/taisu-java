package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysColRequire;

/**
 * 字段必填配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-20
 */
public interface SysColRequireMapper 
{
    /**
     * 查询字段必填配置
     * 
     * @param id 字段必填配置主键
     * @return 字段必填配置
     */
    public SysColRequire selectSysColRequireById(Long id);

    /**
     * 查询字段必填配置列表
     * 
     * @param sysColRequire 字段必填配置
     * @return 字段必填配置集合
     */
    public List<SysColRequire> selectSysColRequireList(SysColRequire sysColRequire);

    /**
     * 新增字段必填配置
     * 
     * @param sysColRequire 字段必填配置
     * @return 结果
     */
    public int insertSysColRequire(SysColRequire sysColRequire);

    /**
     * 修改字段必填配置
     * 
     * @param sysColRequire 字段必填配置
     * @return 结果
     */
    public int updateSysColRequire(SysColRequire sysColRequire);

    /**
     * 删除字段必填配置
     * 
     * @param id 字段必填配置主键
     * @return 结果
     */
    public int deleteSysColRequireById(Long id);

    /**
     * 批量删除字段必填配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysColRequireByIds(Long[] ids);

    /**
     * 根據配置名獲取數據
     * @param tableNames
     * @return
     */
    public List<SysColRequire> selectSysColRequireListByTableNames(String[] tableNames);

    /**
     * 是否存在判斷
     * @param sysColRequire
     * @return
     */
    public List<SysColRequire> selectSysColRequireByTableCol(SysColRequire sysColRequire);
}

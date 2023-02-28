package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.SysColRequire;

/**
 * 字段必填配置Service接口
 * 
 * @author ruoyi
 * @date 2023-02-20
 */
public interface ISysColRequireService 
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
     * 批量删除字段必填配置
     * 
     * @param ids 需要删除的字段必填配置主键集合
     * @return 结果
     */
    public int deleteSysColRequireByIds(Long[] ids);

    /**
     * 删除字段必填配置信息
     * 
     * @param id 字段必填配置主键
     * @return 结果
     */
    public int deleteSysColRequireById(Long id);

    /**
     * 查询字段必填配置
     *
     * @param tableNames 配置名稱
     * @return 字段必填配置
     */
    public List<SysColRequire> selectSysColRequireListByTableNames(String[] tableNames);

    /**
     * 獲取字段必填配置
     * @param tableNames
     * @return
     */
    public Map<String, Map> selectSysColRequireMap(String[] tableNames);
}

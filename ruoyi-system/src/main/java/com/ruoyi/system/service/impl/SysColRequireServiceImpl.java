package com.ruoyi.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import org.apache.poi.xddf.usermodel.chart.Grouping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysColRequireMapper;
import com.ruoyi.system.domain.SysColRequire;
import com.ruoyi.system.service.ISysColRequireService;

/**
 * 字段必填配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-20
 */
@Service
public class SysColRequireServiceImpl implements ISysColRequireService 
{
    @Autowired
    private SysColRequireMapper sysColRequireMapper;

    /**
     * 查询字段必填配置
     * 
     * @param id 字段必填配置主键
     * @return 字段必填配置
     */
    @Override
    public SysColRequire selectSysColRequireById(Long id)
    {
        return sysColRequireMapper.selectSysColRequireById(id);
    }

    /**
     * 查询字段必填配置列表
     * 
     * @param sysColRequire 字段必填配置
     * @return 字段必填配置
     */
    @Override
    public List<SysColRequire> selectSysColRequireList(SysColRequire sysColRequire)
    {
        return sysColRequireMapper.selectSysColRequireList(sysColRequire);
    }

    /**
     * 新增字段必填配置
     * 
     * @param sysColRequire 字段必填配置
     * @return 结果
     */
    @Override
    public int insertSysColRequire(SysColRequire sysColRequire)
    {
        canAddOrEdit(sysColRequire);
        return sysColRequireMapper.insertSysColRequire(sysColRequire);
    }

    /**
     * 修改字段必填配置
     * 
     * @param sysColRequire 字段必填配置
     * @return 结果
     */
    @Override
    public int updateSysColRequire(SysColRequire sysColRequire)
    {
        canAddOrEdit(sysColRequire);
        return sysColRequireMapper.updateSysColRequire(sysColRequire);
    }

    private void canAddOrEdit(SysColRequire sysColRequire){
        List<SysColRequire> voList= sysColRequireMapper.selectSysColRequireByTableCol(sysColRequire);
        if(voList.size()>0){
            throw new ServiceException("已存在配置名為"+sysColRequire.getTableName()+" ，列名為"+sysColRequire.getColName()+"的數據，操作失敗。");
        }

    }

    /**
     * 批量删除字段必填配置
     * 
     * @param ids 需要删除的字段必填配置主键
     * @return 结果
     */
    @Override
    public int deleteSysColRequireByIds(Long[] ids)
    {
        return sysColRequireMapper.deleteSysColRequireByIds(ids);
    }

    /**
     * 删除字段必填配置信息
     * 
     * @param id 字段必填配置主键
     * @return 结果
     */
    @Override
    public int deleteSysColRequireById(Long id)
    {
        return sysColRequireMapper.deleteSysColRequireById(id);
    }

    /**
     * 查询字段必填配置
     *
     * @param tableNames 配置名稱
     * @return 字段必填配置
     */
    @Override
    public List<SysColRequire> selectSysColRequireListByTableNames(String[] tableNames){
        return sysColRequireMapper.selectSysColRequireListByTableNames(tableNames);
    }

    /**
     * 獲取字段必填配置
     * @param tableNames
     * @return
     */
    public Map<String, Map> selectSysColRequireMap(String[] tableNames){
        List<SysColRequire> list=sysColRequireMapper.selectSysColRequireListByTableNames(tableNames);
        Map<String,Map> result=new HashMap<>();
        for(String tableName:tableNames){
            Map<String,Boolean> map= list.stream()
                    .filter(x-> Objects.equals(tableName,x.getTableName()))
                    .collect(Collectors.toMap(SysColRequire::getColName,obj->"Y".equals(obj.getIsRequire())?true:false));
            result.put(tableName,map);
        }
        return result;
    }
}

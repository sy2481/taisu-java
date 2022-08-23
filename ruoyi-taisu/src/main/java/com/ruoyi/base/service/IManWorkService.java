package com.ruoyi.base.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.WorkBo;
import com.ruoyi.base.domain.ManWork;

import java.util.List;


/**
 * 工单Service接口
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
public interface IManWorkService 
{
    /**
     * 查询工单
     * 
     * @param workId 工单主键
     * @return 工单
     */
    public ManWork selectManWorkByWorkId(Long workId);

    /**
     * 查询工单列表
     * 
     * @param manWork 工单
     * @return 工单集合
     */
    public List<ManWork> selectManWorkList(ManWork manWork);

    /**
     * 新增工单
     * 
     * @param manWork 工单
     * @return 结果
     */
    public int insertManWork(ManWork manWork);

    /**
     * 修改工单
     * 
     * @param manWork 工单
     * @return 结果
     */
    public int updateManWork(ManWork manWork);

    /**
     * 批量删除工单
     * 
     * @param workIds 需要删除的工单主键集合
     * @return 结果
     */
    public int deleteManWorkByWorkIds(Long[] workIds);

    /**
     * 删除工单信息
     * 
     * @param workId 工单主键
     * @return 结果
     */
    public int deleteManWorkByWorkId(Long workId);

    void addCarCard(JSONObject jsonObject);

    void delCard(JSONObject jsonObject);

    /**
     * 根据厂区获取IP
     */

     String getIp(Long deptId);
}

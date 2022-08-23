package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.BasicsMatchingRules;

/**
 * 车牌近似规则配置Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-15
 */
public interface BasicsMatchingRulesMapper 
{
    /**
     * 查询车牌近似规则配置
     * 
     * @param configId 车牌近似规则配置主键
     * @return 车牌近似规则配置
     */
    public BasicsMatchingRules selectBasicsMatchingRulesByConfigId(Long configId);

    /**
     * 查询车牌近似规则配置列表
     * 
     * @param basicsMatchingRules 车牌近似规则配置
     * @return 车牌近似规则配置集合
     */
    public List<BasicsMatchingRules> selectBasicsMatchingRulesList(BasicsMatchingRules basicsMatchingRules);

    /**
     * 新增车牌近似规则配置
     * 
     * @param basicsMatchingRules 车牌近似规则配置
     * @return 结果
     */
    public int insertBasicsMatchingRules(BasicsMatchingRules basicsMatchingRules);

    /**
     * 修改车牌近似规则配置
     * 
     * @param basicsMatchingRules 车牌近似规则配置
     * @return 结果
     */
    public int updateBasicsMatchingRules(BasicsMatchingRules basicsMatchingRules);

    /**
     * 删除车牌近似规则配置
     * 
     * @param configId 车牌近似规则配置主键
     * @return 结果
     */
    public int deleteBasicsMatchingRulesByConfigId(Long configId);

    /**
     * 批量删除车牌近似规则配置
     * 
     * @param configIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasicsMatchingRulesByConfigIds(Long[] configIds);
}

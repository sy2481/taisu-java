package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.BasicsMatchingRules;

/**
 * 车牌近似规则配置Service接口
 * 
 * @author ruoyi
 * @date 2022-07-15
 */
public interface IBasicsMatchingRulesService 
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
     * 批量删除车牌近似规则配置
     * 
     * @param configIds 需要删除的车牌近似规则配置主键集合
     * @return 结果
     */
    public int deleteBasicsMatchingRulesByConfigIds(Long[] configIds);

    /**
     * 删除车牌近似规则配置信息
     * 
     * @param configId 车牌近似规则配置主键
     * @return 结果
     */
    public int deleteBasicsMatchingRulesByConfigId(Long configId);

    public List<String> ReturnLicensePlate(String licenseplate);
}

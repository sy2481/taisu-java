package com.ruoyi.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BasicsMatchingRulesMapper;
import com.ruoyi.base.domain.BasicsMatchingRules;
import com.ruoyi.base.service.IBasicsMatchingRulesService;

/**
 * 车牌近似规则配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-15
 */
@Service
public class BasicsMatchingRulesServiceImpl implements IBasicsMatchingRulesService 
{
    @Autowired
    private BasicsMatchingRulesMapper basicsMatchingRulesMapper;

    /**
     * 查询车牌近似规则配置
     * 
     * @param configId 车牌近似规则配置主键
     * @return 车牌近似规则配置
     */
    @Override
    public BasicsMatchingRules selectBasicsMatchingRulesByConfigId(Long configId)
    {
        return basicsMatchingRulesMapper.selectBasicsMatchingRulesByConfigId(configId);
    }

    /**
     * 查询车牌近似规则配置列表
     * 
     * @param basicsMatchingRules 车牌近似规则配置
     * @return 车牌近似规则配置
     */
    @Override
    public List<BasicsMatchingRules> selectBasicsMatchingRulesList(BasicsMatchingRules basicsMatchingRules)
    {
        return basicsMatchingRulesMapper.selectBasicsMatchingRulesList(basicsMatchingRules);
    }

    /**
     * 新增车牌近似规则配置
     * 
     * @param basicsMatchingRules 车牌近似规则配置
     * @return 结果
     */
    @Override
    public int insertBasicsMatchingRules(BasicsMatchingRules basicsMatchingRules)
    {
        basicsMatchingRules.setCreateTime(DateUtils.getNowDate());
        return basicsMatchingRulesMapper.insertBasicsMatchingRules(basicsMatchingRules);
    }

    /**
     * 修改车牌近似规则配置
     * 
     * @param basicsMatchingRules 车牌近似规则配置
     * @return 结果
     */
    @Override
    public int updateBasicsMatchingRules(BasicsMatchingRules basicsMatchingRules)
    {
        basicsMatchingRules.setUpdateTime(DateUtils.getNowDate());
        return basicsMatchingRulesMapper.updateBasicsMatchingRules(basicsMatchingRules);
    }

    /**
     * 批量删除车牌近似规则配置
     * 
     * @param configIds 需要删除的车牌近似规则配置主键
     * @return 结果
     */
    @Override
    public int deleteBasicsMatchingRulesByConfigIds(Long[] configIds)
    {
        return basicsMatchingRulesMapper.deleteBasicsMatchingRulesByConfigIds(configIds);
    }

    /**
     * 删除车牌近似规则配置信息
     * 
     * @param configId 车牌近似规则配置主键
     * @return 结果
     */
    @Override
    public int deleteBasicsMatchingRulesByConfigId(Long configId)
    {
        return basicsMatchingRulesMapper.deleteBasicsMatchingRulesByConfigId(configId);
    }

    /**
     * 根据规则配置生成出相似车牌
     *  @param licenseplate 车牌
     * @return 结果
     * */
    public List<String> ReturnLicensePlate(String licenseplate)
    {
        List<String> a  = new ArrayList<>();
        List<char[]> chars = new ArrayList<>();
        List<String> charsnew = new ArrayList<>();
        char C[] =licenseplate.toCharArray();
        a.add(licenseplate);
        for (int i=1;i<C.length;i++)
        {
            List<char[]> chars1 = new ArrayList<>();
            BasicsMatchingRules basicsMatchingRules = new BasicsMatchingRules();
            basicsMatchingRules.setConfigName(String.valueOf(C[i]));
            List<String> ConfigDisplaceList=  basicsMatchingRulesMapper.selectBasicsMatchingRulesList(basicsMatchingRules).stream().map(o->o.getConfigDisplace()).collect(Collectors.toList());
            if(ConfigDisplaceList.size()>0)
            {
                for (String ConfigDisplace :ConfigDisplaceList)
                {
                    char p[]=licenseplate.toCharArray();
                    p[i] =ConfigDisplace.charAt(0);
                    chars1.add(p);
                }
            }
            int ii=i+1;
            if(ii<C.length)
            {
                for (char []  n  : chars1) {
                    BasicsMatchingRules newbasicsMatchingRules = new BasicsMatchingRules();
                    newbasicsMatchingRules.setConfigName(String.valueOf(n[ii]));
                    List<String> newConfigDisplaceList=  basicsMatchingRulesMapper.selectBasicsMatchingRulesList(newbasicsMatchingRules).stream().map(o->o.getConfigDisplace()).collect(Collectors.toList());

                    for (String ConfigDisplace :newConfigDisplaceList) {
                        char [] nn = n;
                        nn[i+1] = ConfigDisplace.charAt(0);

                        charsnew.add(String.valueOf(nn));
                    }

                }

            }

            chars.addAll(chars1);

        }

        for (char[] l: chars)
        {
            String t= String.valueOf(l);
            a.add(t);
        }
        a.addAll(charsnew);

           return  a.stream().distinct().collect(Collectors.toList());
    }
}

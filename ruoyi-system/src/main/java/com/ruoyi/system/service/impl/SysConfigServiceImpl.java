package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysConfigMapper;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 参数配置 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService
{
    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init()
    {
        loadingConfigCache();
    }

    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    @DataSource(DataSourceType.MASTER)
    public SysConfig selectConfigById(Long configId)
    {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 查询危化参数配置信息對象集
     *
     * @return 参数键值
     */
    public List<SysConfig> selectHCConfigList() {
        List<SysConfig> sysConfigList = configMapper.selectHCConfigList();
        if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessFace")) ||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_ParkingSpaceCount")) ||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_StartBusiness")) ||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.num_ParkingSpaceCount"))||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessEscort"))||
                sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessDriver"))) {
            //如果没有就生成
            if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessFace"))) {
                SysConfig add_ctrl_BrushlessFace =new SysConfig();
                add_ctrl_BrushlessFace.setConfigName("是否免刷人臉");
                add_ctrl_BrushlessFace.setConfigKey("sys.hc.ctrl_BrushlessFace");
                add_ctrl_BrushlessFace.setConfigValue("True");
                add_ctrl_BrushlessFace.setConfigType("N");
                add_ctrl_BrushlessFace.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
                add_ctrl_BrushlessFace.setCreateTime(DateUtils.getNowDate());
                configMapper.insertConfig(add_ctrl_BrushlessFace);
            }
             if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_ParkingSpaceCount"))) {
                 SysConfig add_ctrl_ParkingSpaceCount =new SysConfig();
                 add_ctrl_ParkingSpaceCount.setConfigName("物流通道車位上限數");
                 add_ctrl_ParkingSpaceCount.setConfigKey("sys.hc.ctrl_ParkingSpaceCount");
                 add_ctrl_ParkingSpaceCount.setConfigValue("True");
                 add_ctrl_ParkingSpaceCount.setConfigType("N");
                 add_ctrl_ParkingSpaceCount.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
                 add_ctrl_ParkingSpaceCount.setCreateTime(DateUtils.getNowDate());
                 configMapper.insertConfig(add_ctrl_ParkingSpaceCount);
             }

             if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_StartBusiness")))
             {
                 SysConfig add_ctrl_StartBusiness =new SysConfig();
                 add_ctrl_StartBusiness.setConfigName("是否開始營業");
                 add_ctrl_StartBusiness.setConfigKey("sys.hc.ctrl_StartBusiness");
                 add_ctrl_StartBusiness.setConfigValue("True");
                 add_ctrl_StartBusiness.setConfigType("N");
                 add_ctrl_StartBusiness.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
                 add_ctrl_StartBusiness.setCreateTime(DateUtils.getNowDate());
                 configMapper.insertConfig(add_ctrl_StartBusiness);
             }

             if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.num_ParkingSpaceCount")))
             {
                 SysConfig add_num_ParkingSpaceCount =new SysConfig();
                 add_num_ParkingSpaceCount.setConfigName("物流通道車位上限數");
                 add_num_ParkingSpaceCount.setConfigKey("sys.hc.num_ParkingSpaceCount");
                 add_num_ParkingSpaceCount.setConfigValue("50");
                 add_num_ParkingSpaceCount.setConfigType("N");
                 add_num_ParkingSpaceCount.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
                 add_num_ParkingSpaceCount.setCreateTime(DateUtils.getNowDate());
                 configMapper.insertConfig(add_num_ParkingSpaceCount);
             }

             if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessEscort")))
             {
                 SysConfig add_ctrl_BrushlessEscort =new SysConfig();
                 add_ctrl_BrushlessEscort.setConfigName("是否免填押運員信息");
                 add_ctrl_BrushlessEscort.setConfigKey("sys.hc.ctrl_BrushlessEscort");
                 add_ctrl_BrushlessEscort.setConfigValue("False");
                 add_ctrl_BrushlessEscort.setConfigType("N");
                 add_ctrl_BrushlessEscort.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
                 add_ctrl_BrushlessEscort.setCreateTime(DateUtils.getNowDate());
                 configMapper.insertConfig(add_ctrl_BrushlessEscort);
             }

            if (sysConfigList.stream().noneMatch(g -> Objects.equals(g.getConfigKey(), "sys.hc.ctrl_BrushlessDriver")))
            {
                SysConfig add_ctrl_BrushlessDriver =new SysConfig();
                add_ctrl_BrushlessDriver.setConfigName("是否免填司機信息");
                add_ctrl_BrushlessDriver.setConfigKey("sys.hc.ctrl_BrushlessDriver");
                add_ctrl_BrushlessDriver.setConfigValue("False");
                add_ctrl_BrushlessDriver.setConfigType("N");
                add_ctrl_BrushlessDriver.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
                add_ctrl_BrushlessDriver.setCreateTime(DateUtils.getNowDate());
                configMapper.insertConfig(add_ctrl_BrushlessDriver);
            }

            sysConfigList = configMapper.selectHCConfigList();
        }
        return sysConfigList;
    }


    /**
     * 获取验证码开关
     * 
     * @return true开启，false关闭
     */
    @Override
    public boolean selectCaptchaOnOff()
    {
        String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
        if (StringUtils.isEmpty(captchaOnOff))
        {
            return true;
        }
        return Convert.toBool(captchaOnOff);
    }

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config)
    {
        int row = configMapper.insertConfig(config);
        if (row > 0)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config)
    {
        int row = configMapper.updateConfig(config);
        if (row > 0)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     */
    @Override
    public void deleteConfigByIds(Long[] configIds)
    {
        for (Long configId : configIds)
        {
            SysConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
            {
                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            configMapper.deleteConfigById(configId);
            redisCache.deleteObject(getCacheKey(config.getConfigKey()));
        }
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache()
    {
        List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache()
    {
        Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache()
    {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config)
    {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 设置cache key
     * 
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    public SysConfig selectSysConfigByKey(String configKey) {
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        return retConfig;
    }

    @Override
    public List<SysConfig> selectSysConfigByKeys(String[] configKey) {
        return configMapper.selectConfigs(configKey);
    }

}

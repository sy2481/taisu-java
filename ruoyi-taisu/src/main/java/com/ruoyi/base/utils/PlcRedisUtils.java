package com.ruoyi.base.utils;

import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 兴跃
 */
@Component
public class PlcRedisUtils {

    private static final String PRE_CACHE = "plc-ip:";

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IPlcEquipmentService plcEquipmentService;

    /**
     * 根据Ip取缓存
     */
    public PlcEquipment getPlcEquipment(String ip) {
        Object cacheObject = redisCache.getCacheObject(PRE_CACHE + ip);
        //字符串工具类转换类型
        PlcEquipment plcEquipment = StringUtils.cast(cacheObject);
        if (plcEquipment == null) {
            // 如果没查到，那就查数据库，再保存
            plcEquipment = plcEquipmentService.findByIp(ip);
            //没查到直接返回 null
            if (plcEquipment == null) {
                return null;
            }
            // 查到了，那就放到缓存
            setPlcEquipment(plcEquipment);
        }
        return plcEquipment;
    }

    /**
     * 添加缓存
     */
    public void setPlcEquipment(PlcEquipment plcEquipment) {
        redisCache.setCacheObject(PRE_CACHE + plcEquipment.getIp(), plcEquipment);
    }

    /**
     * 根据IP删除缓存
     */
    public void delPlcEquipment(String ip) {
        redisCache.deleteObject(PRE_CACHE + ip);
    }


}

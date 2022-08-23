package com.ruoyi.common.shardbatis.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.shardbatis.strategy.ShardStrategy;
import com.ruoyi.common.utils.StringUtils;
import com.sun.javafx.collections.MappingChange;

import java.util.HashMap;
import java.util.Map;

public class TsShardStrategy implements ShardStrategy {

    @Override
    public String getTargetTableName(String baseTableName, Object params, String mapperId, String factoryCode) {

        String result = baseTableName;
        if (!StringUtils.isEmpty(factoryCode)) {
            result = factoryCode + "_" + result;
        }
        return result;
    }
}

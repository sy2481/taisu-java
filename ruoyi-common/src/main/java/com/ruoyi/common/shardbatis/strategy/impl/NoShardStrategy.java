package com.ruoyi.common.shardbatis.strategy.impl;

import com.ruoyi.common.shardbatis.strategy.ShardStrategy;

public class NoShardStrategy implements ShardStrategy {
    public NoShardStrategy() {
    }

    @Override
    public String getTargetTableName(String baseTableName, Object params, String mapperId,String factoryCode) {
        return baseTableName;
    }
}

package com.ruoyi.common.shardbatis.strategy;

public interface ShardStrategy {
    String getTargetTableName(String var1, Object var2, String var3,String var4);
}

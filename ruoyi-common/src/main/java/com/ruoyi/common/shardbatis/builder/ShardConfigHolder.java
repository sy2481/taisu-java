package com.ruoyi.common.shardbatis.builder;


import com.ruoyi.common.shardbatis.strategy.ShardStrategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShardConfigHolder {
    private static final ShardConfigHolder instance = new ShardConfigHolder();
    private Map<String, ShardStrategy> strategyRegister = new HashMap();
    private Set<String> ignoreSet;
    private Set<String> parseSet;
    private String factoryCode;

    public static ShardConfigHolder getInstance() {
        return instance;
    }

    private ShardConfigHolder() {
    }

    public void register(String table, ShardStrategy strategy) {
        this.strategyRegister.put(table.toLowerCase(), strategy);
    }

    public ShardStrategy getStrategy(String table) {
        return (ShardStrategy) this.strategyRegister.get(table.toLowerCase());
    }

    public synchronized void addIgnoreId(String id) {
        if (this.ignoreSet == null) {
            this.ignoreSet = new HashSet();
        }

        this.ignoreSet.add(id);
    }

    public synchronized void addParseId(String id) {
        if (this.parseSet == null) {
            this.parseSet = new HashSet();
        }

        this.parseSet.add(id);
    }

    public boolean isConfigParseId() {
        return this.parseSet != null;
    }

    public boolean isParseId(String id) {
        id = id.substring(0, id.lastIndexOf("."));

        return this.parseSet != null && this.parseSet.contains(id);
    }

    public boolean isIgnoreId(String id) {
        return this.ignoreSet != null && this.ignoreSet.contains(id);
    }


    public Map<String, ShardStrategy> getStrategyRegister() {
        return strategyRegister;
    }

    public void setStrategyRegister(Map<String, ShardStrategy> strategyRegister) {
        this.strategyRegister = strategyRegister;
    }

    public synchronized void setFactoryCode(String id) {
        this.factoryCode = id;
    }
    public String getFactoryCode(){
        return this.factoryCode;
    }


}

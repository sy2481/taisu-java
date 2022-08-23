package com.ruoyi.base.domain;

import java.io.Serializable;

/**
 * OA系统最大ID表 OA_MAX_INFO
 *
 * @author sunlj
 * @date 2022-07-18
 */
public class OaMaxInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String factoryCode;
    private String tableName;
    private Long maxId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }
}

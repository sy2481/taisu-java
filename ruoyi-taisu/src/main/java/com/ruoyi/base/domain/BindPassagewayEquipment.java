package com.ruoyi.base.domain;

import java.util.List;

public class BindPassagewayEquipment {
    /**
     * 通道id
     */
    private Long id;

    /**
     * 設備ids
     */
    private List<Long> hikEquipmentIds;

    public void setPassagewayId(Long id) {
        this.id = id;
    }

    public Long getPassagewayId() {
        return id;
    }

    public void setHikEquipmentIds(List<Long> hikEquipmentIds) {
        this.hikEquipmentIds = hikEquipmentIds;
    }

    public List<Long> getHikEquipmentIds() {
        return hikEquipmentIds;
    }
}

package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通道對象 base_passageway_equipment
 *
 * @author ruoyi
 * @date 2022-07-16
 */
public class BasePassagewayEquipment {
    /** passagewayId */
    private Long passagewayId;

    /** hikEquipmentId */
    private Long hikEquipmentId;

    public Long getPassagewayId() {
        return passagewayId;
    }

    public void setPassagewayId(Long passagewayId) {
        this.passagewayId = passagewayId;
    }

    public Long getHikEquipmentId() {
        return hikEquipmentId;
    }

    public void setHikEquipmentId(Long hikEquipmentId) {
        this.hikEquipmentId = hikEquipmentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("passagewayId", getPassagewayId())
                .append("hikEquipmentId",getHikEquipmentId())
                .toString();
    }
}

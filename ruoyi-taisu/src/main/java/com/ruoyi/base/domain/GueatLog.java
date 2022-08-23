package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 来宾卡操作记录对象 base_gueat_log
 *
 * @author xxy
 * @date 2022-06-05
 */
public class GueatLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 操作人
     */
    @Excel(name = "操作人")
    private String operationUser;

    /**
     * 操作内容
     */
    @Excel(name = "操作内容")
    private String operationInfo;

    /**
     * 刪除標志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 卡片ID
     */
    private Long cardId;
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo;
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("operationUser", getOperationUser())
                .append("operationInfo", getOperationInfo())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}

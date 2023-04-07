package com.ruoyi.base.service;

import com.ruoyi.base.domain.InOutUserStatus;

import java.util.List;

/**
 * 內部人員進出Service接口
 *
 * @author ruoyi
 * @date 2023-03-22
 */
public interface InOutUserStatusService
{
    /**
     * 查询內部人員進出
     *
     * @param idNo 內部人員進出主键
     * @return 內部人員進出
     */
    public InOutUserStatus selectInOutUserStatusByIdNo(String idNo);

    /**
     * 查询內部人員進出列表
     *
     * @param inOutUserStatus 內部人員進出
     * @return 內部人員進出集合
     */
    public List<InOutUserStatus> selectInOutUserStatusList(InOutUserStatus inOutUserStatus);

    /**
     * 新增內部人員進出
     *
     * @param inOutUserStatus 內部人員進出
     * @return 结果
     */
    public int insertInOutUserStatus(InOutUserStatus inOutUserStatus);

    /**
     * 修改內部人員進出
     *
     * @param inOutUserStatus 內部人員進出
     * @return 结果
     */
    public int updateInOutUserStatus(InOutUserStatus inOutUserStatus);

    /**
     * 批量删除內部人員進出
     *
     * @param idNos 需要删除的內部人員進出主键集合
     * @return 结果
     */
    public int deleteInOutUserStatusByIdNos(String[] idNos);

    /**
     * 删除內部人員進出信息
     *
     * @param idNo 內部人員進出主键
     * @return 结果
     */
    public int deleteInOutUserStatusByIdNo(String idNo);


    int selectUserInCount();
}


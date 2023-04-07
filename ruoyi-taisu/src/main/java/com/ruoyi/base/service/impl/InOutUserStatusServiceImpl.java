package com.ruoyi.base.service.impl;


import java.util.List;

import com.ruoyi.base.domain.InOutUserStatus;
import com.ruoyi.base.mapper.InOutUserStatusMapper;
import com.ruoyi.base.service.InOutUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 內部人員進出Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-22
 */
@Service
public class InOutUserStatusServiceImpl implements InOutUserStatusService {
    @Autowired
    private InOutUserStatusMapper inOutUserStatusMapper;

    /**
     * 查询內部人員進出
     *
     * @param idNo 內部人員進出主键
     * @return 內部人員進出
     */
    @Override
    public InOutUserStatus selectInOutUserStatusByIdNo(String idNo) {
        return inOutUserStatusMapper.selectInOutUserStatusByIdNo(idNo);
    }

    /**
     * 查询內部人員進出列表
     *
     * @param inOutUserStatus 內部人員進出
     * @return 內部人員進出
     */
    @Override
    public List<InOutUserStatus> selectInOutUserStatusList(InOutUserStatus inOutUserStatus) {
        return inOutUserStatusMapper.selectInOutUserStatusList(inOutUserStatus);
    }

    /**
     * 新增內部人員進出
     *
     * @param inOutUserStatus 內部人員進出
     * @return 结果
     */
    @Override
    public int insertInOutUserStatus(InOutUserStatus inOutUserStatus) {
        return inOutUserStatusMapper.insertInOutUserStatus(inOutUserStatus);
    }

    /**
     * 修改內部人員進出
     *
     * @param inOutUserStatus 內部人員進出
     * @return 结果
     */
    @Override
    public int updateInOutUserStatus(InOutUserStatus inOutUserStatus) {
        return inOutUserStatusMapper.updateInOutUserStatus(inOutUserStatus);
    }

    /**
     * 批量删除內部人員進出
     *
     * @param idNos 需要删除的內部人員進出主键
     * @return 结果
     */
    @Override
    public int deleteInOutUserStatusByIdNos(String[] idNos) {
        return inOutUserStatusMapper.deleteInOutUserStatusByIdNos(idNos);
    }

    /**
     * 删除內部人員進出信息
     *
     * @param idNo 內部人員進出主键
     * @return 结果
     */
    @Override
    public int deleteInOutUserStatusByIdNo(String idNo) {
        return inOutUserStatusMapper.deleteInOutUserStatusByIdNo(idNo);
    }

    @Override
    public int selectUserInCount() {
        return inOutUserStatusMapper.selectInCount();
    }

}

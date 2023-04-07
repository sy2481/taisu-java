package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.BaseErp;
import com.ruoyi.base.mapper.BaseErpMapper;
import com.ruoyi.base.service.BaseErpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseErpServiceImpl implements BaseErpService {
    @Autowired
    private BaseErpMapper baseErpMapper;

    /**
     * 查询erp工单
     *
     * @param vhNo erp工单主键
     * @return erp工单
     */
    @Override
    public BaseErp selectBaseErpByVhNo(String vhNo) {
        return baseErpMapper.selectBaseErpByVhNo(vhNo);
    }

    /**
     * 查询erp工单列表
     *
     * @param baseErp erp工单
     * @return erp工单
     */
    @Override
    public List<BaseErp> selectBaseErpList(BaseErp baseErp) {
        return baseErpMapper.selectBaseErpList(baseErp);
    }

    /**
     * 新增erp工单
     *
     * @param baseErp erp工单
     * @return 结果
     */
    @Override
    public int insertBaseErp(BaseErp baseErp) {
        return baseErpMapper.insertBaseErp(baseErp);
    }

    /**
     * 修改erp工单
     *
     * @param baseErp erp工单
     * @return 结果
     */
    @Override
    public int updateBaseErp(BaseErp baseErp) {
        return baseErpMapper.updateBaseErp(baseErp);
    }

    /**
     * 批量删除erp工单
     *
     * @param vhNos 需要删除的erp工单主键
     * @return 结果
     */
    @Override
    public int deleteBaseErpByVhNos(String[] vhNos) {
        return baseErpMapper.deleteBaseErpByVhNos(vhNos);
    }

    /**
     * 删除erp工单信息
     *
     * @param vhNo erp工单主键
     * @return 结果
     */
    @Override
    public int deleteBaseErpByVhNo() {
        return baseErpMapper.deleteBaseErpByVhNo();
    }

    @Override
    public int selectCount() {
        return baseErpMapper.selectCount();
    }

}

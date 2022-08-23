package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.InOutLogPvc;
import com.ruoyi.base.mapper.InOutLogPvcMapper;
import com.ruoyi.base.service.IInOutLogPvcService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * pvc厂进出记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-11
 */
@Service
public class InOutLogPvcServiceImpl implements IInOutLogPvcService {
    @Autowired
    private InOutLogPvcMapper inOutLogPvcMapper;

    /**
     * 查询pvc厂进出记录
     *
     * @param workTime pvc厂进出记录主键
     * @return pvc厂进出记录
     */
    @Override
    public InOutLogPvc selectInOutLogPvcByWorkTime(String workTime) {
        return inOutLogPvcMapper.selectInOutLogPvcByWorkTime(workTime);
    }

    @Override
    public Integer selectInOutLogPvcByAid(Integer aid) {
        return inOutLogPvcMapper.selectInOutLogPvcByAid(aid);
    }


    /**
     * 查询pvc厂进出记录列表
     *
     * @param inOutLogPvc pvc厂进出记录
     * @return pvc厂进出记录
     */
    @Override
    public List<InOutLogPvc> selectInOutLogPvcList(InOutLogPvc inOutLogPvc) {
        return inOutLogPvcMapper.selectInOutLogPvcList(inOutLogPvc);
    }

    /**
     * 新增pvc厂进出记录
     *
     * @param inOutLogPvc pvc厂进出记录
     * @return 结果
     */
    @Override
    public int insertInOutLogPvc(InOutLogPvc inOutLogPvc) {
        inOutLogPvc.setCreateTime(DateUtils.getNowDate());
        return inOutLogPvcMapper.insertInOutLogPvc(inOutLogPvc);
    }

    /**
     * 修改pvc厂进出记录
     *
     * @param inOutLogPvc pvc厂进出记录
     * @return 结果
     */
    @Override
    public int updateInOutLogPvc(InOutLogPvc inOutLogPvc) {
        inOutLogPvc.setUpdateTime(DateUtils.getNowDate());
        return inOutLogPvcMapper.updateInOutLogPvc(inOutLogPvc);
    }

    /**
     * 批量删除pvc厂进出记录
     *
     * @param workTimes 需要删除的pvc厂进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogPvcByWorkTimes(String[] workTimes) {
        return inOutLogPvcMapper.deleteInOutLogPvcByWorkTimes(workTimes);
    }

    /**
     * 删除pvc厂进出记录信息
     *
     * @param workTime pvc厂进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogPvcByWorkTime(String workTime) {
        return inOutLogPvcMapper.deleteInOutLogPvcByWorkTime(workTime);
    }

    @Override
    public void  processInOutLogPvcData(){

    }
}

package com.ruoyi.base.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.InOutLogBasicDataMapper;
import com.ruoyi.base.domain.InOutLogBasicData;
import com.ruoyi.base.service.InOutLogBasicDataService;

/**
 * pvc厂进出记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-13
 */
@Service
public class InOutLogBasicDataServiceImpl implements InOutLogBasicDataService {
    @Autowired
    private InOutLogBasicDataMapper inOutLogBasicDataMapper;

    /**
     * 查询pvc厂进出记录
     *
     * @param aid pvc厂进出记录主键
     * @return pvc厂进出记录
     */
    @Override
    public InOutLogBasicData selectInOutLogBasicDataByAid(Long aid) {
        return inOutLogBasicDataMapper.selectInOutLogBasicDataByAid(aid);
    }

    /**
     * 查询pvc厂进出记录列表
     *
     * @param inOutLogBasicData pvc厂进出记录
     * @return pvc厂进出记录
     */
    @Override
    public List<InOutLogBasicData> selectInOutLogBasicDataList(InOutLogBasicData inOutLogBasicData) {
        return inOutLogBasicDataMapper.selectInOutLogBasicDataList(inOutLogBasicData);
    }

    public List<InOutLogBasicData> selectInOutLogBasicDataListByIdcardAndWorkTime(String idNo, String workTime)
    {
        return inOutLogBasicDataMapper.selectInOutLogBasicDataListByIdcardAndWorkTime(idNo,workTime);
    }

    /**
     * 新增pvc厂进出记录
     *
     * @param inOutLogBasicData pvc厂进出记录
     * @return 结果
     */
    @Override
    public int insertInOutLogBasicData(InOutLogBasicData inOutLogBasicData) {

        return inOutLogBasicDataMapper.insertInOutLogBasicData(inOutLogBasicData);
    }

    /**
     * 修改pvc厂进出记录
     *
     * @param inOutLogBasicData pvc厂进出记录
     * @return 结果
     */
    @Override
    public int updateInOutLogBasicData(InOutLogBasicData inOutLogBasicData) {
        //inOutLogBasicData.setUpdateTime(DateUtils.getNowDate());
        return inOutLogBasicDataMapper.updateInOutLogBasicData(inOutLogBasicData);
    }

    @Override
    public int updateInOutLogBasicDataByWorkTime(InOutLogBasicData inOutLogBasicData) {
        //inOutLogBasicData.setUpdateTime(DateUtils.getNowDate());
        return inOutLogBasicDataMapper.updateInOutLogBasicDataByWorkTime(inOutLogBasicData);
    }

    /**
     * 批量删除pvc厂进出记录
     *
     * @param aids 需要删除的pvc厂进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogBasicDataByAids(Long[] aids) {
        return inOutLogBasicDataMapper.deleteInOutLogBasicDataByAids(aids);
    }

    /**
     * 删除pvc厂进出记录信息
     *
     * @param aid pvc厂进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogBasicDataByAid(Long aid) {
        return inOutLogBasicDataMapper.deleteInOutLogBasicDataByAid(aid);
    }

    @Override
    public int selectPersonInNum() {
        return inOutLogBasicDataMapper.selectPersonInNum();
    }

    @Override
    public int selectCarInNum() {
        return inOutLogBasicDataMapper.selectCarInNum();
    }

    @Override
    public List<InOutLogBasicData> selectCarInOutLogBasicDataList(String ipltLic,String dateTime) {
        return inOutLogBasicDataMapper.selectCarInOutLogBasicDataList(ipltLic,dateTime);
    }

    @Override
    public List<Map<String, Object>> selectAllAreaInOutLogBasicDataList(String workTime){
        return inOutLogBasicDataMapper.selectAllAreaInOutLogBasicDataList(workTime);
    }

    public List<Map<String, Object>>  selectPersonAreaInOutLogBasicDataListByArea(String workTime,String vnho){
        return inOutLogBasicDataMapper.selectPersonAreaInOutLogBasicDataListByArea(workTime,vnho);
    }
    public List<Map<String, Object>>  selectCarAreaInOutLogBasicDataListByArea(String workTime,String vnho){
        return inOutLogBasicDataMapper.selectCarAreaInOutLogBasicDataListByArea(workTime,vnho);
    }

    @Override
    public List<InOutLogBasicData> selectInOutLogBasicDataListByArea(String workTime,String area) {
        return inOutLogBasicDataMapper.selectInOutLogBasicDataListByArea(workTime,area);
    }

    @Override
    public List<InOutLogBasicData> selectTodayInOutLogBasicDataList(String workTime) {
        return inOutLogBasicDataMapper.selectTodayInOutLogBasicDataList(workTime);
    }


    @Override
    public List<InOutLogBasicData> selectInOutLogBasicDataList(String workTime) {
        return inOutLogBasicDataMapper.selectInOutLogBasicDataList(workTime);
    }

    @Override
    public List<InOutLogBasicData> selectCarInOutLogBasicDataListByArea(String workTime,String area) {
        return inOutLogBasicDataMapper.selectCarInOutLogBasicDataListByArea(workTime,area);
    }
}

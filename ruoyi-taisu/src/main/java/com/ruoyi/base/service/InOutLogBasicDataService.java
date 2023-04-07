package com.ruoyi.base.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.base.domain.InOutLogBasicData;

/**
 * pvc厂进出记录Service接口
 *
 * @author ruoyi
 * @date 2023-03-13
 */

public interface InOutLogBasicDataService {
    /**
     * 查询pvc厂进出记录
     *
     * @param aid pvc厂进出记录主键
     * @return pvc厂进出记录
     */
    public InOutLogBasicData selectInOutLogBasicDataByAid(Long aid);

    /**
     * 查询pvc厂进出记录列表
     *
     * @param inOutLogBasicData pvc厂进出记录
     * @return pvc厂进出记录集合
     */
    public List<InOutLogBasicData> selectInOutLogBasicDataList(InOutLogBasicData inOutLogBasicData);

    public List<InOutLogBasicData> selectInOutLogBasicDataListByIdcardAndWorkTime(String idNo, String workTime);

    /**
     * 新增pvc厂进出记录
     *
     * @param inOutLogBasicData pvc厂进出记录
     * @return 结果
     */
    public int insertInOutLogBasicData(InOutLogBasicData inOutLogBasicData);

    /**
     * 修改pvc厂进出记录
     *
     * @param inOutLogBasicData pvc厂进出记录
     * @return 结果
     */
    public int updateInOutLogBasicData(InOutLogBasicData inOutLogBasicData);


    public int updateInOutLogBasicDataByWorkTime(InOutLogBasicData inOutLogBasicData);

    /**
     * 批量删除pvc厂进出记录
     *
     * @param aids 需要删除的pvc厂进出记录主键集合
     * @return 结果
     */
    public int deleteInOutLogBasicDataByAids(Long[] aids);

    /**
     * 删除pvc厂进出记录信息
     *
     * @param aid pvc厂进出记录主键
     * @return 结果
     */
    public int deleteInOutLogBasicDataByAid(Long aid);


    int selectPersonInNum();


    int selectCarInNum();

    List<InOutLogBasicData> selectCarInOutLogBasicDataList(String ipltLic, String dateTime);

    List<Map<String, Object>> selectAllAreaInOutLogBasicDataList(String workTime);

    List<Map<String, Object>> selectPersonAreaInOutLogBasicDataListByArea(String workTime, String vh_no);

    List<Map<String, Object>> selectCarAreaInOutLogBasicDataListByArea(String workTime, String vnho);

    List<InOutLogBasicData> selectInOutLogBasicDataListByArea(String workTime, String area);

    List<InOutLogBasicData> selectTodayInOutLogBasicDataList(String workTime);

    List<InOutLogBasicData> selectCarInOutLogBasicDataListByArea(String workTime, String area);
    List<InOutLogBasicData> selectInOutLogBasicDataList(String workTime);

}

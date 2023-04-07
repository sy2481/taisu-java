package com.ruoyi.base.mapper;


import java.util.List;
import java.util.Map;

import com.ruoyi.base.domain.InOutLogBasicData;
import org.apache.ibatis.annotations.Param;

/**
 * 进出记录Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-13
 */
public interface InOutLogBasicDataMapper {
    /**
     * 查询进出记录
     *
     * @param aid 进出记录主键
     * @return 进出记录
     */
    public InOutLogBasicData selectInOutLogBasicDataByAid(Long aid);

    /**
     * 查询进出记录列表
     *
     * @param inOutLogBasicData 进出记录
     * @return 进出记录集合
     */
    public List<InOutLogBasicData> selectInOutLogBasicDataList(InOutLogBasicData inOutLogBasicData);

    public List<InOutLogBasicData> selectInOutLogBasicDataListByIdcardAndWorkTime(@Param("idno") String idNo, @Param("workTime") String workTime);

    /**
     * 新增进出记录
     *
     * @param inOutLogBasicData 进出记录
     * @return 结果
     */
    public int insertInOutLogBasicData(InOutLogBasicData inOutLogBasicData);

    /**
     * 修改进出记录
     *
     * @param inOutLogBasicData 进出记录
     * @return 结果
     */
    public int updateInOutLogBasicData(InOutLogBasicData inOutLogBasicData);


    public int updateInOutLogBasicDataByWorkTime(InOutLogBasicData inOutLogBasicData);

    /**
     * 删除进出记录
     *
     * @param aid 进出记录主键
     * @return 结果
     */
    public int deleteInOutLogBasicDataByAid(Long aid);

    /**
     * 批量删除进出记录
     *
     * @param aids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInOutLogBasicDataByAids(Long[] aids);

    int selectPersonInNum();

    int selectCarInNum();

    List<InOutLogBasicData> selectCarInOutLogBasicDataList(@Param("ipltLic") String ipltLic, @Param("workTime") String workTime);

    List<InOutLogBasicData> selectInOutLogBasicDataListByArea(@Param("workTime") String workTime, @Param("area") String area);

    List<InOutLogBasicData> selectTodayInOutLogBasicDataList(@Param("workTime") String workTime);

    List<InOutLogBasicData> selectInOutLogBasicDataList(@Param("workTime") String workTime);

    List<InOutLogBasicData> selectCarInOutLogBasicDataListByArea(@Param("workTime") String workTime, @Param("area") String area);

    List<Map<String, Object>> selectAllAreaInOutLogBasicDataList(@Param("workTime") String workTime);

    List<Map<String, Object>> selectPersonAreaInOutLogBasicDataListByArea(@Param("workTime") String workTime, @Param("vnho") String vnho);

    List<Map<String, Object>> selectCarAreaInOutLogBasicDataListByArea(@Param("workTime") String workTime, @Param("vnho") String vnho);

    List<Map<String, Object>> selectPersonAreaInOutLogBasicDataList(@Param("workTime") String workTime);

    List<Map<String, Object>> selectCarAreaInOutLogBasicDataList(@Param("workTime") String workTime);


}

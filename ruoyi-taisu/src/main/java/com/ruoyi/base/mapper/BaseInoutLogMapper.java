package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.BaseBoardWarn;
import com.ruoyi.base.domain.BaseInoutLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 進出記錄Mapper接口
 *
 * @author ruoyi
 * @date 2022-08-26
 */
public interface BaseInoutLogMapper {
    /**
     * 查询進出記錄
     *
     * @param id 進出記錄主键
     * @return 進出記錄
     */
    public BaseInoutLog selectBaseInoutLogById(Long id);

    /**
     * 查询進出記錄
     *
     * @param IdNo 身份证/车牌号
     * @return 進出記錄
     */
    public BaseInoutLog selectTop1BaseInoutLogByIdCard(@Param("IdNo") String IdNo);

    /**
     * 查询進出記錄
     *
     * @param IcNo 卡號
     * @return 進出記錄
     */
    public BaseInoutLog selectTop1BaseInoutLogByIcNo(@Param("IcNo") String IcNo);

    /**
     * 根据工单号查询進出記錄
     *
     * @param vhNo 工单号集合
     * @return 進出記錄集合
     */
    public List<BaseInoutLog> selectBaseInoutLogListByVhNos(@Param("vhNo") String[] vhNo);

    /**
     * 根据身份证查询進出記錄
     *
     * @param idNo 身份证集合
     * @return 進出記錄集合
     */
    public List<BaseInoutLog> selectBaseInoutLogListByIdNos(@Param("idNo") String[] idNo);

    /**
     * 查询進出記錄列表
     *
     * @param baseInoutLog 進出記錄
     * @return 進出記錄集合
     */
    public List<BaseInoutLog> selectBaseInoutLogList(BaseInoutLog baseInoutLog);


    /**
     * 新增進出記錄
     *
     * @param baseInoutLog 進出記錄
     * @return 结果
     */
    public int insertBaseInoutLog(BaseInoutLog baseInoutLog);

    /**
     * 修改進出記錄
     *
     * @param baseInoutLog 進出記錄
     * @return 结果
     */
    public int updateBaseInoutLog(BaseInoutLog baseInoutLog);

    /**
     * 删除進出記錄
     *
     * @param id 進出記錄主键
     * @return 结果
     */
    public int deleteBaseInoutLogById(Long id);

    /**
     * 批量删除進出記錄
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseInoutLogByIds(Long[] ids);

    /**
     * 查询進出記錄
     *
     * @param vhNo 施工單號
     * @return 進出記錄
     */
    public List<BaseInoutLog> selectBaseInoutLogByVhNo(@Param("vhNo") String vhNo, @Param("inOutMode") String inOutMode, @Param("cardKind") Long cardKind);

    /**
     * 查询進出記錄列表
     *
     * @param today       当日时间
     * @param cnstrAreaId 区域id
     * @return 進出記錄集合
     */
    public List<BaseInoutLog> accessQueryTodayList(@Param("today") String today, @Param("cnstrAreaId") String cnstrAreaId
            , @Param("vndNo") String vndNo, @Param("inOutMode") String inOutMode);

    /**
     * 查询未当日出厂记录
     *
     * @param today       当日时间
     * @param cnstrAreaId 区域id
     * @return 進出記錄集合
     */
    public List<BaseInoutLog> accessQueryTodayNoExFactoryList(@Param("today") String today, @Param("cnstrAreaId") String cnstrAreaId);

    /**
     * 未出廠的數量
     *
     * @param vhNos
     * @return
     */
    public List<BaseInoutLog> selectCntNotOutByVhNos(@Param("vhNos") String[] vhNos, @Param("cardKind") Long cardKind);

    /**
     * 警报信息汇总
     */
    public List<BaseBoardWarn> listWarn(BaseBoardWarn baseBoardWarn);

    /**
     * 刪除未補錄數據
     *
     * @param idNo
     * @param abnormalState
     * @return
     */
    public int deleteBaseInoutLogNotSupp(@Param("idNo") String idNo, @Param("abnormalState") String abnormalState);

    /**
     * 根據施工單延期
     *
     * @param endTime
     * @param vhNo
     * @return
     */
    public int delayBaseInoutLogByVhNo(@Param("endTime") Date endTime, @Param("vhNo") String vhNo);
}

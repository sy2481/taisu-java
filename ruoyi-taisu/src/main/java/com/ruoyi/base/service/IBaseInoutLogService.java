package com.ruoyi.base.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.BaseBoardWarn;
import com.ruoyi.base.domain.BaseGate;
import com.ruoyi.base.domain.BaseInoutLog;
import com.ruoyi.base.domain.RegionDetail;

/**
 * 進出記錄Service接口
 *
 * @author ruoyi
 * @date 2022-08-26
 */
public interface IBaseInoutLogService {
    /**
     * 查询進出記錄
     *
     * @param id 進出記錄主键
     * @return 進出記錄
     */
    BaseInoutLog selectBaseInoutLogById(Long id);

    /**
     * 查询進出記錄列表
     *
     * @param baseInoutLog 進出記錄
     * @return 進出記錄集合
     */
    List<BaseInoutLog> selectBaseInoutLogList(BaseInoutLog baseInoutLog);

    /**
     * 新增進出記錄
     *
     * @param baseInoutLog 進出記錄
     * @return 结果
     */
    int insertBaseInoutLog(BaseInoutLog baseInoutLog);

    /**
     * 修改進出記錄
     *
     * @param baseInoutLog 進出記錄
     * @return 结果
     */
    int updateBaseInoutLog(BaseInoutLog baseInoutLog);

    /**
     * 批量删除進出記錄
     *
     * @param ids 需要删除的進出記錄主键集合
     * @return 结果
     */
    int deleteBaseInoutLogByIds(Long[] ids);

    /**
     * 删除進出記錄信息
     *
     * @param id 進出記錄主键
     * @return 结果
     */
    int deleteBaseInoutLogById(Long id);

    /**
     * 未出廠人員
     *
     * @param vhNo
     * @return
     */
    List<BaseInoutLog> listInNotOutVndUserByVhNo(String vhNo);

    /**
     * 未出廠車輛
     *
     * @param vhNo
     * @return
     */
    List<BaseInoutLog> listInNotOutVndCarByVhNo(String vhNo);

    /**
     * 根據工單號獲取入廠的 廠商人員
     *
     * @param vhNo
     * @return
     */
    List<BaseInoutLog> listInVndUserByVhNo(String vhNo);

    /**
     * 根據工單號獲取出廠的 廠商人員
     *
     * @param vhNo
     * @return
     */
    List<BaseInoutLog> listOutVndUserByVhNo(String vhNo);

    /**
     * 根據工單號獲取入廠的 廠商人員
     *
     * @param vhNo
     * @return
     */
    List<BaseInoutLog> listInVndCarByVhNo(String vhNo);

    /**
     * 根據工單號獲取出廠的 廠商人員
     *
     * @param vhNo
     * @return
     */
    List<BaseInoutLog> listOutVndCarByVhNo(String vhNo);

    /**
     * 查询厂商内厂商，员工，车辆信息
     *
     * @param type        1 厂商 2 员工 3 车辆
     * @param cnstrAreaId 区域id
     * @param vndNo       厂商编号
     * @return
     */
    List<BaseInoutLog> accessQueryTodayList(String type, String cnstrAreaId, String vndNo);

    /**
     * 查询厂商内厂商，员工，车辆信息总数
     *
     * @param cnstrAreaId 区域id
     * @return
     */
    JSONObject accessQueryTodayCount(String cnstrAreaId);

    /**
     * 本日出入场明细
     */
    JSONObject accessQueryTodayDetail();

    /**
     * 区域获取厂商列表
     */
    List<RegionDetail> selectFactorByRegion(String cnstrAreaId);

    /**
     * 区域数量分布总览
     */
    Map regionDistributionOverview();

    /**
     * 作业类别分布
     */
    Map oprOverview(String oprs);

    /**
     * 门状态
     */
    List<BaseGate> doorStatus();

    /**
     * 來賓進出記錄
     */
    List<BaseInoutLog> listGuest(BaseInoutLog baseInoutLog);

    /**
     * 特殊卡進出記錄
     */
    List<BaseInoutLog> listSpecial(BaseInoutLog baseInoutLog);

    /**
     * 廠商人員進出記錄
     */
    List<BaseInoutLog> listVndUser(BaseInoutLog baseInoutLog);

    /**
     * 廠商車輛進出記錄
     */
    List<BaseInoutLog> listVndCar(BaseInoutLog baseInoutLog);

    /**
     * 員工進出記錄
     */
    List<BaseInoutLog> listEmpUser(BaseInoutLog baseInoutLog);

    /**
     * 員工車輛進出記錄
     */
    List<BaseInoutLog> listEmpCar(BaseInoutLog baseInoutLog);

    /**
     * 危化人員進出記錄
     */
    List<BaseInoutLog> listHcUser(BaseInoutLog baseInoutLog);

    /**
     * 危化車輛進出記錄
     */
    List<BaseInoutLog> listHcCar(BaseInoutLog baseInoutLog);

    /**
     * 本日施工动态记录
     *
     * @param baseInoutLog
     * @return
     */
    List<BaseInoutLog> listVndUserInToday(BaseInoutLog baseInoutLog);

    /**
     * 出廠未讀卡記錄
     *
     * @param baseInoutLog
     * @return
     */
    List<BaseInoutLog> listOutNotRead(BaseInoutLog baseInoutLog);

    /**
     * 入廠未讀卡記錄
     *
     * @param baseInoutLog
     * @return
     */
    List<BaseInoutLog> listInNotRead(BaseInoutLog baseInoutLog);

    /**
     * 警报信息汇总
     */
    List<BaseBoardWarn> listWarn(BaseBoardWarn baseBoardWarn);

    /**
     * 警报确认
     */
    void warnConfirm(List<BaseBoardWarn> baseBoardWarnList);

    /**
     * 補錄
     *
     * @param baseInoutLog
     * @param abnormalState
     * @return
     */
    int supplementary(BaseInoutLog baseInoutLog, String abnormalState);

    /**
     * 入廠未讀卡記錄
     *
     * @param baseInoutLog
     * @return
     */
    List<BaseInoutLog> listOverdue(BaseInoutLog baseInoutLog, Long cardKind);

    /**
     * 查詢報表基本條件
     *
     * @param baseInoutLog
     * @param isHc         是否危化
     * @param isSup        true：正常、已補錄 ； false:未補錄
     * @param empOrVnd     null：不查詢； emp：員工；vnd：廠商人員
     * @return
     */
    BaseInoutLog getQueryParams(BaseInoutLog baseInoutLog, boolean isHc, boolean isSup, String empOrVnd);

    /**
     * 实时在线进出记录
     *
     * @param today
     * @param cnstrAreaId
     * @param vndNo
     * @return
     */
    List<BaseInoutLog> realBaseInoutLog(String today, String cnstrAreaId, String vndNo);

}
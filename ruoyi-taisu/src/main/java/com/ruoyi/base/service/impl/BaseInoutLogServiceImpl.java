package com.ruoyi.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.BaseBoardWarn;
import com.ruoyi.base.domain.BaseGate;
import com.ruoyi.base.domain.BaseInoutLog;
import com.ruoyi.base.domain.RegionDetail;
import com.ruoyi.base.mapper.BaseInoutLogMapper;
import com.ruoyi.base.service.IBaseInoutLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class BaseInoutLogServiceImpl implements IBaseInoutLogService {

    @Autowired
    private BaseInoutLogMapper baseInoutLogMapper;

    @Override
    public BaseInoutLog selectBaseInoutLogById(Long id) {
        return baseInoutLogMapper.selectBaseInoutLogById(id);
    }

    @Override
    public List<BaseInoutLog> selectBaseInoutLogList(BaseInoutLog baseInoutLog) {
        return baseInoutLogMapper.selectBaseInoutLogList(baseInoutLog);
    }

    @Override
    public int insertBaseInoutLog(BaseInoutLog baseInoutLog) {
        return 0;
    }

    @Override
    public int updateBaseInoutLog(BaseInoutLog baseInoutLog) {
        return 0;
    }

    @Override
    public int deleteBaseInoutLogByIds(Long[] ids) {
        return 0;
    }

    @Override
    public int deleteBaseInoutLogById(Long id) {
        return 0;
    }

    @Override
    public List<BaseInoutLog> listInNotOutVndUserByVhNo(String vhNo) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listInNotOutVndCarByVhNo(String vhNo) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listInVndUserByVhNo(String vhNo) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listOutVndUserByVhNo(String vhNo) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listInVndCarByVhNo(String vhNo) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listOutVndCarByVhNo(String vhNo) {
        return null;
    }

    @Override
    public List<BaseInoutLog> accessQueryTodayList(String type, String cnstrAreaId, String vndNo) {
        return null;
    }

    @Override
    public JSONObject accessQueryTodayCount(String cnstrAreaId) {
        return null;
    }

    @Override
    public JSONObject accessQueryTodayDetail() {
        return null;
    }

    @Override
    public List<RegionDetail> selectFactorByRegion(String cnstrAreaId) {
        return null;
    }

    @Override
    public Map regionDistributionOverview() {
        return null;
    }

    @Override
    public Map oprOverview(String oprs) {
        return null;
    }

    @Override
    public List<BaseGate> doorStatus() {
        return null;
    }

    @Override
    public List<BaseInoutLog> listGuest(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listSpecial(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listVndUser(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listVndCar(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listEmpUser(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listEmpCar(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listHcUser(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listHcCar(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listVndUserInToday(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listOutNotRead(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseInoutLog> listInNotRead(BaseInoutLog baseInoutLog) {
        return null;
    }

    @Override
    public List<BaseBoardWarn> listWarn(BaseBoardWarn baseBoardWarn) {
        return null;
    }

    @Override
    public void warnConfirm(List<BaseBoardWarn> baseBoardWarnList) {

    }

    @Override
    public int supplementary(BaseInoutLog baseInoutLog, String abnormalState) {
        return 0;
    }

    @Override
    public List<BaseInoutLog> listOverdue(BaseInoutLog baseInoutLog, Long cardKind) {
        return null;
    }

    @Override
    public BaseInoutLog getQueryParams(BaseInoutLog baseInoutLog, boolean isHc, boolean isSup, String empOrVnd) {
        return null;
    }

    @Override
    public List<BaseInoutLog> realBaseInoutLog(String today, String cnstrAreaId, String vndNo) {
        return null;
    }
}

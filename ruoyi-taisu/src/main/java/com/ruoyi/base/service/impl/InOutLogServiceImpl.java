package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.InOutLog;
import com.ruoyi.base.mapper.InOutLogMapper;
import com.ruoyi.base.service.IInOutLogService;
import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.base.vo.SelectExceptionInOutLogListVO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 进出记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-03-07
 */
@Service
public class InOutLogServiceImpl implements IInOutLogService {
    @Autowired
    private InOutLogMapper inOutLogMapper;

    /**
     * 查询进出记录
     *
     * @param id 进出记录主键
     * @return 进出记录
     */
    @Override
    public InOutLog selectInOutLogById(Long id) {
        return inOutLogMapper.selectInOutLogById(id);
    }

    /**
     * 查询进出记录列表
     *
     * @param inOutLog 进出记录
     * @return 进出记录
     */
    @Override
    public List<InOutLog> selectInOutLogList(InOutLog inOutLog) {

        /**
         * 根據登錄人從廠區查詢
         */
        //查询当前登录人是否擁有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList != null) {
            inOutLog.setFactoryIdList(longList);
        }
        List<InOutLog> inOutLogs = inOutLogMapper.selectInOutLogList(inOutLog);
        inOutLogs.forEach(inOutLogItem -> {
            if (inOutLogItem.getCreateTime() != null) {
                inOutLogItem.setInOutTime(inOutLogItem.getCreateTime());
            }
        });

        return inOutLogs;
    }

    /**
     * 新增进出记录
     *
     * @param inOutLog 进出记录
     * @return 结果
     */
    @Override
    public int insertInOutLog(InOutLog inOutLog) {
        inOutLog.setCreateTime(DateUtils.getNowDate());
        inOutLog.setOperationTime(DateUtils.getNowDate());
        return inOutLogMapper.insertInOutLog(inOutLog);
    }

    /**
     * 修改进出记录
     *
     * @param inOutLog 进出记录
     * @return 结果
     */
    @Override
    public int updateInOutLog(InOutLog inOutLog) {
        return inOutLogMapper.updateInOutLog(inOutLog);
    }

    /**
     * 批量删除进出记录
     *
     * @param ids 需要删除的进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogByIds(Long[] ids) {
        return inOutLogMapper.deleteInOutLogByIds(ids);
    }

    /**
     * 删除进出记录信息
     *
     * @param id 进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogById(Long id) {
        return inOutLogMapper.deleteInOutLogById(id);
    }

    @Override
    public InOutLog getInOutLogGuestByIdCard(String idCard, Integer time, String personType, Long deptId) {
        return inOutLogMapper.getInOutLogGuestByIdCard(idCard, time, personType, deptId);
    }

    @Override
    public int removeLog(Long id) {
        return inOutLogMapper.removeLog(id, SecurityUtils.getLoginUser().getUser().getNickName());
    }

    //0 in 1 out
    @Override
    public List<SelectExceptionInOutLogListVO> selectExceptionInOutLogList(Date dateTime) {

        List<SelectExceptionInOutLogListVO> voList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startTime = simpleDateFormat.format(dateTime);
        String endTime = simpleDateFormat.format(DateUtils.addDays(dateTime, 1));
//        try {
//            returnDate = simpleDateFormat.parse(dateTime);
//        }catch (Exception e){
//
//        }


//        String startTime = "2023-03-19 00:00:00";
//        String endTime = "2023-03-20 00:00:00";
        List<Map<String, Object>> inOutLogPersons = inOutLogMapper.selectPersonListByTime(startTime, endTime);
        if (inOutLogPersons != null && inOutLogPersons.size() > 0) {
            for (Map<String, Object> inOutLogPerson : inOutLogPersons) {
                Boolean errorCode = false;
                int errorInTimes = 0;
                int errorOutTimes = 0;

                String idCard = inOutLogPerson.get("idCard").toString();
                String name = inOutLogPerson.get("name").toString();
                Integer personType = Integer.parseInt(inOutLogPerson.get("personType").toString());
                String userNo = "";
                if (inOutLogPerson.get("userNo") != null) {
                    userNo = inOutLogPerson.get("userNo").toString();
                }
                List<InOutLog> personInOutLogs = inOutLogMapper.selectPersonListByIdcardAndTime(idCard, startTime, endTime);
                if (personInOutLogs != null && personInOutLogs.size() > 0) {
                    //进出记录只有一条时 存在问题
                    if (personInOutLogs.size() < 2) {
                        SelectExceptionInOutLogListVO vo = new SelectExceptionInOutLogListVO();
                        if (personType.equals(0)) {
                            vo.setIdCard(userNo);
                        } else {
                            vo.setIdCard(idCard);
                        }
                        vo.setPersonType(personType);
                        vo.setName(name);

                        if (personInOutLogs.get(0).getLogType().startsWith("0")) {
                            vo.setExceptionInTimes(1);
                            vo.setExceptionOutTimes(0);
                        }
                        if (personInOutLogs.get(0).getLogType().startsWith("1")) {
                            vo.setExceptionInTimes(0);
                            vo.setExceptionOutTimes(1);
                        }
                        voList.add(vo);
                    } else {
                        if (personInOutLogs.size() == 2) {
                            InOutLog base = personInOutLogs.get(0);
                            InOutLog compare = personInOutLogs.get(1);
                            if (personInOutLogs.get(0).getLogType().startsWith("0") && personInOutLogs.get(1).getLogType().startsWith("1")) {
                                //yes
                            } else if ((personInOutLogs.get(0).getLogType().startsWith("1") && personInOutLogs.get(1).getLogType().startsWith("0"))) {
//                                long diff = compare.getCreateTime().getTime() - base.getCreateTime().getTime();
//                                if (diff / 1000 < 5 * 60) {
                                SelectExceptionInOutLogListVO vo = new SelectExceptionInOutLogListVO();
//                                vo.setIdCard(idCard);
                                vo.setName(name);
                                if (personType.equals(0)) {
                                    vo.setIdCard(userNo);
                                } else {
                                    vo.setIdCard(idCard);
                                }
                                vo.setPersonType(personType);
                                vo.setExceptionInTimes(1);
                                vo.setExceptionOutTimes(1);
                                voList.add(vo);
//                                }
                            } else {
                                System.out.println(idCard + " size =2");
                            }
                        } else {
                            //first in
                            if (personInOutLogs.get(0).getLogType().startsWith("0")) {

                            } else if (personInOutLogs.get(0).getLogType().startsWith("1")) {
                                errorCode = true;
                                errorInTimes++;
                            }

                            InOutLog base = personInOutLogs.get(0);
                            for (int i = 1; i < personInOutLogs.size() - 1; i++) {
                                InOutLog compare = personInOutLogs.get(i);
                                if (!base.getLogType().substring(0, 1).equals(compare.getLogType().substring(0, 1))) {
                                    base = compare;
                                } else {
                                    long diff = compare.getCreateTime().getTime() - base.getCreateTime().getTime();
                                    if (diff / 1000 > 5 * 60) {
                                        if (base.getLogType().substring(0, 1).startsWith("0")) {
                                            errorCode = true;
                                            errorOutTimes++;
                                        } else if (base.getLogType().substring(0, 1).startsWith("1")) {
                                            errorCode = true;
                                            errorInTimes++;
                                        }
                                    }
                                }

                            }
                            if (personInOutLogs.get(personInOutLogs.size() - 1).getLogType().startsWith("0")) {
                                errorCode = true;
                                errorOutTimes++;
                            }

                            if (errorCode == true) {
                                SelectExceptionInOutLogListVO vo = new SelectExceptionInOutLogListVO();
//                                vo.setIdCard(idCard);
                                vo.setName(name);
                                if (personType.equals(0)) {
                                    vo.setIdCard(userNo);
                                } else {
                                    vo.setIdCard(idCard);
                                }
                                vo.setPersonType(personType);
                                vo.setExceptionInTimes(errorInTimes);
                                vo.setExceptionOutTimes(errorOutTimes);
                                voList.add(vo);
                            }
                        }
                    }
                }
            }
        }
        return voList;
    }


}

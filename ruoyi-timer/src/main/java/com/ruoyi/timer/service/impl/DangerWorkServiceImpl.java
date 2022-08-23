package com.ruoyi.timer.service.impl;


import com.ruoyi.base.bo.FactoryBo;
import com.ruoyi.base.bo.WorkBo;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.timer.bo.DangerFactoryBo;
import com.ruoyi.timer.bo.DangerWorkBo;
import com.ruoyi.timer.domain.DangerWork;
import com.ruoyi.timer.mapper.TimInOutLogMapper;
import com.ruoyi.timer.service.IDangerWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shiva   2022-03-27 19:42
 */
@Service
@DataSource(value = DataSourceType.IEMDG)
public class DangerWorkServiceImpl implements IDangerWorkService {

    @Autowired
    private RedisCache redisUtils;
    @Autowired
    private TimInOutLogMapper inOutLogMapper;

    /**
     * 拿危化品数据，传入当前时间
     * 1.根据时间查询
     * 2.根据单号分组
     * 3.区分车和人的列表
     */
    @Override
    public List<WorkBo> dangerWorkData() {
        //有的话查sql，组装数据
        Date today = DateUtils.parseDate(DateUtils.getDate());
        Date nextDay = DateUtils.addDays(today, 1);
        List<DangerWork> list = inOutLogMapper.getDangerData(
                DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, today),
                DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, nextDay));
        if (list == null || list.size() == 0) {
            return null;
        }
        //开始处理数据
        return getByDangerData(list);
    }


    private List<WorkBo> getByDangerData(List<DangerWork> source) {
        List<WorkBo> result = new ArrayList<>();
        //先根据单号分组；
        Map<String, List<DangerWork>> groupByVhno = source.stream().collect(Collectors.groupingBy(DangerWork::getVhno));
        groupByVhno.forEach((no, list) -> {
            //针对每个分组进行数据整理
            WorkBo workBo = instanceWorkBo(list);
            result.add(workBo);
        });
        return result;
    }

    private WorkBo instanceWorkBo(List<DangerWork> source) {
        WorkBo workBo = new WorkBo();
        //拿到车辆对应的数据条目
        DangerWork dangerWork = null;
        for (DangerWork work : source) {
            if (work.getIdno().length() < 10) {
                dangerWork = work;
                break;
            }
        }
        if (dangerWork == null){
            return null;
        }
        // 危化品工单相关
        workBo.setWorkNumber(dangerWork.getVhno());
        workBo.setDatetime(dangerWork.getIplttm());
        workBo.setIP(dangerWork.getKdnm());
        // 车牌数据
        workBo.setCarId(dangerWork.getIdno());
        // 人员数据
        List<FactoryBo> factoryBoList = new ArrayList<>();
        for (DangerWork work : source) {
            if (work.getIdno().length() > 10) {
                //这些都是人员
                FactoryBo factoryBo = new FactoryBo();
                factoryBo.setWorkNumber(workBo.getWorkNumber());
                factoryBo.setIdCard(work.getIdno());
                factoryBo.setFactoryName(work.getNm());
                factoryBo.setName(work.getNm());
                factoryBoList.add(factoryBo);
            }
        }
        workBo.setFactoryBoList(factoryBoList);
        return workBo;
    }
}

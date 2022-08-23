package com.ruoyi.timer.service;

import com.ruoyi.base.bo.WorkBo;
import com.ruoyi.base.domain.InOutLogPvc;
import com.ruoyi.timer.domain.TimInOutLog;

import java.util.List;
import java.util.Map;

public interface ITimInOutLogService {
    /**
     * 获取增量数据
     * @param maxId
     * @return
     */
    Map<String,Object> getInOutLog(Integer maxId,String factoryCode);

    /**
     * 获取延时数据
     * @param maxId
     * @return
     */
    List<WorkBo> getInOutLogExtend(Integer maxId,String factoryCode);
    List<InOutLogPvc> getInOutLogOrderByAid();
}

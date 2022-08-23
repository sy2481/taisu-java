package com.ruoyi.timer.mapper;

import com.ruoyi.timer.domain.TcInOutLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 兴跃
 */
@Mapper
@Repository
public interface TcInOutLogMapper {

    /**
     * 添加
     */
    int insertTcInOutLog(TcInOutLog tcInOutLog);

}

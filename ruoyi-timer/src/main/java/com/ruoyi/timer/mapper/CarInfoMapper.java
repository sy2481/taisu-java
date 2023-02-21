package com.ruoyi.timer.mapper;

import com.ruoyi.timer.domain.CarInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarInfoMapper {
    List<CarInfo> getCarInfoList(@Param("carId") String carId);
}

package com.ruoyi.base.service;

import com.ruoyi.base.vo.DayInAndOutInfoVO;
import com.ruoyi.base.vo.GetAllDayInAndOutInfoVO;
import com.ruoyi.base.vo.GetAllPersonAndCarDayInAndOutInfoVo;
import com.ruoyi.base.vo.GetDayInAndOutInfoByAreaVO;

import java.util.List;

public interface DynamicBoardService {
    List<DayInAndOutInfoVO> getDayInAndOutInfo();

    List<GetAllDayInAndOutInfoVO> getAllDayInAndOutInfo();

    GetDayInAndOutInfoByAreaVO getDayInAndOutInfoByArea(String area);

    GetAllPersonAndCarDayInAndOutInfoVo getAllPersonAndCarDayInAndOutInfo();

}

package com.ruoyi.timer.mapper;

import com.ruoyi.timer.domain.Safetyman;

import java.util.List;

/**
* @author mi
* @description 针对表【SafetyMan】的数据库操作Mapper
* @createDate 2022-08-25 16:41:37
* @Entity generator.domain.Safetyman
*/
public interface SafetymanMapper {


    List<Safetyman> getCarList();
}

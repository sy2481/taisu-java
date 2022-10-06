package com.ruoyi.base.mapper;


import com.ruoyi.base.domain.BaseSafetycar;

import java.util.List;

/**
* @author mi
* @description 针对表【base_safetyCar】的数据库操作Mapper
* @createDate 2022-08-29 09:39:08
* @Entity generator.domain.BaseSafetycar
*/
public interface SafetycarMapper {


    void insertCarlist(BaseSafetycar baseSafetycar);

    BaseSafetycar isExist(String ipltlic);

    List<BaseSafetycar> getSafetycarByCarno(String carParam);
}

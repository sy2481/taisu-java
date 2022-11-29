package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.BaseCarCardBind;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author mi
* @description 针对表【base_car_card_bind】的数据库操作Mapper
* @createDate 2022-09-05 20:47:03
* @Entity generator.domain.BaseCarCardBind
*/
public interface CarCardBindMapper {

    void addCarCardBind(BaseCarCardBind baseCarCardBind);

    List<BaseCarCardBind> getByPrimarykey(@Param("carCarNo") String carCarNo,@Param("workNo")  String workNo);

    int getCountByCarCardNo(String carCarNo);

    void deleteByPrimarykey(@Param("carCarNo") String carCarNo,@Param("workNo")  String workNo);

    void cardBindClear();
}

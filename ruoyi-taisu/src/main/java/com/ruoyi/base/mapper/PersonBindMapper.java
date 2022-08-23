package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.PersonBind;
import com.ruoyi.base.interact.PersonSendService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 兴跃
 */
@Repository
public interface PersonBindMapper {

    /**
     * 新增
     */
    int insertPersonBind(PersonBind personBind);

    /**
     * 根据身份证号查询
     */
    List<PersonBind> selectByIdCard(@Param("idCard") String idCard,@Param("personType")Integer personType);

    /**
     * 身份证号存在，修改数据
     */
    int updateByIdCard(PersonBind personBind);


    /**
     * 解绑
     */
    int relieveByIdCard(@Param("IdCard") String IdCard);
}

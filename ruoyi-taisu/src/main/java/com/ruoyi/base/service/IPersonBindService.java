package com.ruoyi.base.service;

import com.ruoyi.base.domain.PersonBind;

import java.util.List;

/**
 * @author 兴跃
 */
public interface IPersonBindService {

    /**
     * 新增
     */
    int insertPersonBind(PersonBind personBind);

    /**
     * 根据身份证号查询
     */
    List<PersonBind> selectByIdCard( String idCard,Integer personType);

    /**
     * 身份证号存在，修改数据
     */
    int updateByIdCard(PersonBind personBind);


    /**
     * 解绑
     */
    int relieveByIdCard( String IdCard);
}

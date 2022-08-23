package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.PersonBind;
import com.ruoyi.base.mapper.PersonBindMapper;
import com.ruoyi.base.service.IPersonBindService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * @author 兴跃
 */
@Service
public class PersonBindServiceImpl implements IPersonBindService {

    @Autowired
    private PersonBindMapper personBindMapper;

    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(propagation = Propagation.REQUIRES_NEW ,readOnly = false)
    @Override
    public int insertPersonBind(PersonBind personBind) {

        return personBindMapper.insertPersonBind(personBind);
    }

    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<PersonBind> selectByIdCard(String idCard,Integer personType) {
        return personBindMapper.selectByIdCard(idCard,personType);
    }

    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(propagation = Propagation.REQUIRES_NEW ,readOnly = false)
    @Override
    public int updateByIdCard(PersonBind personBind) {
        personBind.setCreateTime(new Date());
        return personBindMapper.updateByIdCard(personBind);
    }

    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(propagation = Propagation.REQUIRES_NEW ,readOnly = false)
    @Override
    public int relieveByIdCard(String IdCard) {
        return personBindMapper.relieveByIdCard(IdCard);
    }
}

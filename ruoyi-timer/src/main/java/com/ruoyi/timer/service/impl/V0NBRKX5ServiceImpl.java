package com.ruoyi.timer.service.impl;

import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.timer.domain.V0NBRKX5;
import com.ruoyi.timer.mapper.V0NBRKX5Mapper;
import com.ruoyi.timer.service.IV0NBRKX5Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 危化信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-21
 */
@Service
@DataSource(value = DataSourceType.IEMDG)
public class V0NBRKX5ServiceImpl implements IV0NBRKX5Service {
    @Autowired
    private V0NBRKX5Mapper v0NBRKX5Mapper;

    /**
     * 查询危化信息列表
     *
     * @param v0NBRKX5 危化信息
     * @return 危化信息
     */
    @Override
    public List<V0NBRKX5Bo> selectV0NBRKX5List(V0NBRKX5 v0NBRKX5) {
        List<V0NBRKX5Bo> result = new ArrayList<>();
        V0NBRKX5Bo entity = new V0NBRKX5Bo();
        //本日參數
        if (v0NBRKX5 == null) {
            v0NBRKX5 = new V0NBRKX5();
        }
        List<V0NBRKX5> list = v0NBRKX5Mapper.selectV0NBRKX5List(v0NBRKX5);
        for (V0NBRKX5 item : list) {
            entity = new V0NBRKX5Bo();
            BeanUtils.copyProperties(item, entity);
            result.add(entity);
        }
        return result;
    }

}

package com.ruoyi.timer.service;

import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.timer.domain.V0NBRKX5;

import java.util.List;

/**
 * 危化信息Service接口
 * 
 * @author ruoyi
 * @date 2022-10-21
 */
public interface IV0NBRKX5Service 
{
    /**
     * 查询危化信息列表
     * 
     * @param v0NBRKX5 危化信息
     * @return 危化信息集合
     */
    public List<V0NBRKX5Bo> selectV0NBRKX5List(V0NBRKX5 v0NBRKX5);

}

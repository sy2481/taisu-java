package com.ruoyi.timer.mapper;

import com.ruoyi.timer.domain.V0NBRKX5;

import java.util.List;

/**
 * 危化信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-21
 */
public interface V0NBRKX5Mapper 
{
    /**
     * 查询危化信息
     * 
     * @param idno 危化信息主键
     * @return 危化信息
     */
    //public V0NBRKX5 selectV0NBRKX5ByIdno(String idno);

    /**
     * 查询危化信息列表
     * 
     * @param v0NBRKX5 危化信息
     * @return 危化信息集合
     */
    public List<V0NBRKX5> selectV0NBRKX5List(V0NBRKX5 v0NBRKX5);

    /**
     * 新增危化信息
     * 
     * @param v0NBRKX5 危化信息
     * @return 结果
     */
    //public int insertV0NBRKX5(V0NBRKX5 v0NBRKX5);

    /**
     * 修改危化信息
     * 
     * @param v0NBRKX5 危化信息
     * @return 结果
     */
    //public int updateV0NBRKX5(V0NBRKX5 v0NBRKX5);

    /**
     * 删除危化信息
     * 
     * @param idno 危化信息主键
     * @return 结果
     */
    //public int deleteV0NBRKX5ByIdno(String idno);

    /**
     * 批量删除危化信息
     * 
     * @param idnos 需要删除的数据主键集合
     * @return 结果
     */
    //public int deleteV0NBRKX5ByIdnos(String[] idnos);
}

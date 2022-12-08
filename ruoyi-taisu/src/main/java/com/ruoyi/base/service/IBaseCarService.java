package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.BaseCar;
import org.apache.ibatis.annotations.Param;

/**
 * 車Service接口
 *
 * @author ruoyi
 * @date 2022-12-08
 */
public interface IBaseCarService
{
    /**
     * 查询車
     *
     * @param id 車主键
     * @return 車
     */
    public BaseCar selectBaseCarById(Long id);

    /**
     * 查询車列表
     *
     * @param baseCar 車
     * @return 車集合
     */
    public List<BaseCar> selectBaseCarList(BaseCar baseCar);

    /**
     * 新增車
     *
     * @param baseCar 車
     * @return 结果
     */
    public int insertBaseCar(BaseCar baseCar);

    /**
     * 修改車
     *
     * @param baseCar 車
     * @return 结果
     */
    public int updateBaseCar(BaseCar baseCar);

    /**
     * 批量删除車
     *
     * @param ids 需要删除的車主键集合
     * @return 结果
     */
    public int deleteBaseCarByIds(Long[] ids);

    /**
     * 删除車信息
     *
     * @param id 車主键
     * @return 结果
     */
    public int deleteBaseCarById(Long id);

    /**
     * 查询車
     *
     * @param idCard 車牌
     * @return 車
     */
    public BaseCar selectBaseCarByIdCard(String idCard);

    /**
     * 保存車輛表
     * @param baseCar
     * @return
     */
    public int saveBaseCar(BaseCar baseCar);
}

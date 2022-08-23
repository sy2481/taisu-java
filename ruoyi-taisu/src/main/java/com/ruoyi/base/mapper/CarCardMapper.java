package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.CarCard;

import java.util.List;

/**
 * 車卡Mapper接口
 *
 * @author ZZF
 * @date 2022-03-06
 */
public interface CarCardMapper

{

    /**
     * 查询車卡
     *
     * @param cardCarId 車卡主键
     * @return 車卡
     */
    public CarCard selectCarCardByCardCarId(Long cardCarId);

    /**
     * 根据编号查询車卡
     *
     * @param cardCarNo 車卡编号
     * @return 車卡
     */
    public CarCard selectCarCardByCardCarNo(String cardCarNo);


    /**
     * 查询車卡列表
     *
     * @param carCard 車卡
     * @return 車卡集合
     */
    public List<CarCard> selectCarCardList(CarCard carCard);

    /**
     * 新增車卡
     *
     * @param carCard 車卡
     * @return 结果
     */
    public int insertCarCard(CarCard carCard);

    /**
     * 修改車卡
     *
     * @param carCard 車卡
     * @return 结果
     */
    public int updateCarCard(CarCard carCard);

    /**
     * 根据车卡编号修改车卡
     *
     * @param carCard
     * @return
     */
    public int updateCarCardByNo(CarCard carCard);


    /**
     * 删除車卡
     *
     * @param cardCarId 車卡主键
     * @return 结果
     */
    public int deleteCarCardByCardCarId(Long cardCarId);

    /**
     * 批量删除車卡
     *
     * @param cardCarIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarCardByCardCarIds(Long[] cardCarIds);

    /**
     * 每天凌晨清理绑定关系
     */
    void dailyClear();
}

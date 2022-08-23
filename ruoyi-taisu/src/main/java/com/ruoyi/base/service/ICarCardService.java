package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.CarCard;

/**
 * 車卡Service接口
 * 
 * @author ZZF
 * @date 2022-03-06
 */
public interface ICarCardService 
{

    /**
     * 查询車卡
     * 
     * @param cardCarId 車卡主键
     * @return 車卡
     */
    public CarCard selectCarCardByCardCarId(Long cardCarId);

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
     * 批量删除車卡
     * 
     * @param cardCarIds 需要删除的車卡主键集合
     * @return 结果
     */
    public int deleteCarCardByCardCarIds(Long[] cardCarIds);

    /**
     * 删除車卡信息
     * 
     * @param cardCarId 車卡主键
     * @return 结果
     */
    public int deleteCarCardByCardCarId(Long cardCarId);

    /**
     * 領用/規劃車卡
     */
    public void doCard(CarCard carCard, String type);
}

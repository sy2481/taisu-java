package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.base.domain.CardRecord;
import com.ruoyi.base.service.ICardRecordService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.CarCardMapper;
import com.ruoyi.base.domain.CarCard;
import com.ruoyi.base.service.ICarCardService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 車卡Service业务层处理
 * 
 * @author ZZF
 * @date 2022-03-06
 */
@Service
public class CarCardServiceImpl implements ICarCardService 
{
    @Autowired
    private CarCardMapper carCardMapper;
    @Autowired
    private ICardRecordService cardRecordService;



    /**
     * 查询車卡
     * 
     * @param cardCarId 車卡主键
     * @return 車卡
     */
    @Override
    public CarCard selectCarCardByCardCarId(Long cardCarId)
    {
        return carCardMapper.selectCarCardByCardCarId(cardCarId);
    }

    /**
     * 查询車卡列表
     * 
     * @param carCard 車卡
     * @return 車卡
     */
    @Override
    public List<CarCard> selectCarCardList(CarCard carCard)
    {
        return carCardMapper.selectCarCardList(carCard);
    }

    /**
     * 新增車卡
     * 
     * @param carCard 車卡
     * @return 结果
     */
    @Override
    public int insertCarCard(CarCard carCard)
    {
        /**
         * 添加编号做好唯一
         */
        CarCard car = carCardMapper.selectCarCardByCardCarNo(carCard.getCardCarNo());
        if (car==null){
            carCard.setCreateTime(DateUtils.getNowDate());
            return carCardMapper.insertCarCard(carCard);
        }else {
            return -1;
        }

    }

    /**
     * 修改車卡
     * 
     * @param carCard 車卡
     * @return 结果
     */
    @Override
    public int updateCarCard(CarCard carCard)
    {
        CarCard card = carCardMapper.selectCarCardByCardCarId(carCard.getCardCarId());
        if (carCard.getCardCarNo().equals(card.getCardCarNo())){
            carCard.setUpdateTime(DateUtils.getNowDate());
            return carCardMapper.updateCarCard(carCard);
        }else {
            CarCard car = carCardMapper.selectCarCardByCardCarNo(carCard.getCardCarNo());
            if (car==null){
                carCard.setUpdateTime(DateUtils.getNowDate());
                return carCardMapper.updateCarCard(carCard);
            }else {
                return -1;
            }
        }

    }

    /**
     * 批量删除車卡
     * 
     * @param cardCarIds 需要删除的車卡主键
     * @return 结果
     */
    @Override
    public int deleteCarCardByCardCarIds(Long[] cardCarIds)
    {
        return carCardMapper.deleteCarCardByCardCarIds(cardCarIds);
    }

    /**
     * 删除車卡信息
     * 
     * @param cardCarId 車卡主键
     * @return 结果
     */
    @Override
    public int deleteCarCardByCardCarId(Long cardCarId)
    {
        return carCardMapper.deleteCarCardByCardCarId(cardCarId);
    }

    /**
     * 領用/歸還車卡
     */
    @Override
    @Transactional(readOnly = false)
    public void doCard(CarCard doCard, String type) {
        doCard.setUpdateTime(DateUtils.getNowDate());
        carCardMapper.updateCarCard(doCard);
        //跟蹤日志
        CardRecord cardRecord = new CardRecord();
        cardRecord.setCardId(doCard.getCardCarId());
        cardRecord.setCardNo(doCard.getCardCarNo());
        cardRecord.setCardType("0");//車卡
        if ("0".equals(type)) {
            //領用
            cardRecord.setCardRecordOperate(type);
            cardRecord.setCardRecordObject(doCard.getBindPlateNo());
            cardRecord.setCardRecordTime(doCard.getLeadTime());
            cardRecord.setCardRecordName(doCard.getLeadName());
        }else if ("1".equals(type)) {
            //歸還
            cardRecord.setCardRecordOperate(type);
            cardRecord.setCardRecordTime(doCard.getReturnTime());
            cardRecord.setCardRecordName(doCard.getReturnName());
        }
        cardRecord.setCreateBy(doCard.getUpdateBy());
        cardRecord.setCreateTime(DateUtils.getNowDate());
        cardRecordService.insertCardRecord(cardRecord);
    }
}

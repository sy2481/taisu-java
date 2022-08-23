package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.CardRecordMapper;
import com.ruoyi.base.domain.CardRecord;
import com.ruoyi.base.service.ICardRecordService;

/**
 * 卡片記錄Service业务层处理
 * 
 * @author ZZF
 * @date 2022-03-07
 */
@Service
public class CardRecordServiceImpl implements ICardRecordService 
{
    @Autowired
    private CardRecordMapper cardRecordMapper;

    /**
     * 查询卡片記錄
     * 
     * @param cardRecordId 卡片記錄主键
     * @return 卡片記錄
     */
    @Override
    public CardRecord selectCardRecordByCardRecordId(Long cardRecordId)
    {
        return cardRecordMapper.selectCardRecordByCardRecordId(cardRecordId);
    }

    /**
     * 查询卡片記錄列表
     * 
     * @param cardRecord 卡片記錄
     * @return 卡片記錄
     */
    @Override
    public List<CardRecord> selectCardRecordList(CardRecord cardRecord)
    {
        return cardRecordMapper.selectCardRecordList(cardRecord);
    }

    /**
     * 新增卡片記錄
     * 
     * @param cardRecord 卡片記錄
     * @return 结果
     */
    @Override
    public int insertCardRecord(CardRecord cardRecord)
    {
        cardRecord.setCreateTime(DateUtils.getNowDate());
        return cardRecordMapper.insertCardRecord(cardRecord);
    }

    /**
     * 修改卡片記錄
     * 
     * @param cardRecord 卡片記錄
     * @return 结果
     */
    @Override
    public int updateCardRecord(CardRecord cardRecord)
    {
        cardRecord.setUpdateTime(DateUtils.getNowDate());
        return cardRecordMapper.updateCardRecord(cardRecord);
    }

    /**
     * 批量删除卡片記錄
     * 
     * @param cardRecordIds 需要删除的卡片記錄主键
     * @return 结果
     */
    @Override
    public int deleteCardRecordByCardRecordIds(Long[] cardRecordIds)
    {
        return cardRecordMapper.deleteCardRecordByCardRecordIds(cardRecordIds);
    }

    /**
     * 删除卡片記錄信息
     * 
     * @param cardRecordId 卡片記錄主键
     * @return 结果
     */
    @Override
    public int deleteCardRecordByCardRecordId(Long cardRecordId)
    {
        return cardRecordMapper.deleteCardRecordByCardRecordId(cardRecordId);
    }
}

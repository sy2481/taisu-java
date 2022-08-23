package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.CardRecord;

/**
 * 卡片記錄Mapper接口
 * 
 * @author ZZF
 * @date 2022-03-07
 */
public interface CardRecordMapper 
{
    /**
     * 查询卡片記錄
     * 
     * @param cardRecordId 卡片記錄主键
     * @return 卡片記錄
     */
    public CardRecord selectCardRecordByCardRecordId(Long cardRecordId);

    /**
     * 查询卡片記錄列表
     * 
     * @param cardRecord 卡片記錄
     * @return 卡片記錄集合
     */
    public List<CardRecord> selectCardRecordList(CardRecord cardRecord);

    /**
     * 新增卡片記錄
     * 
     * @param cardRecord 卡片記錄
     * @return 结果
     */
    public int insertCardRecord(CardRecord cardRecord);

    /**
     * 修改卡片記錄
     * 
     * @param cardRecord 卡片記錄
     * @return 结果
     */
    public int updateCardRecord(CardRecord cardRecord);

    /**
     * 删除卡片記錄
     * 
     * @param cardRecordId 卡片記錄主键
     * @return 结果
     */
    public int deleteCardRecordByCardRecordId(Long cardRecordId);

    /**
     * 批量删除卡片記錄
     * 
     * @param cardRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCardRecordByCardRecordIds(Long[] cardRecordIds);
}

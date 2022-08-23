package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.LocateCard;

/**
 * 定位卡Service接口
 * 
 * @author ZZF
 * @date 2022-03-06
 */
public interface ILocateCardService 
{
    /**
     * 查询定位卡
     * 
     * @param cardLocateId 定位卡主键
     * @return 定位卡
     */
    public LocateCard selectLocateCardByCardLocateId(Long cardLocateId);

    /**
     * 查询定位卡列表
     * 
     * @param locateCard 定位卡
     * @return 定位卡集合
     */
    public List<LocateCard> selectLocateCardList(LocateCard locateCard);

    /**
     * 新增定位卡
     * 
     * @param locateCard 定位卡
     * @return 结果
     */
    public int insertLocateCard(LocateCard locateCard);

    /**
     * 修改定位卡
     * 
     * @param locateCard 定位卡
     * @return 结果
     */
    public int updateLocateCard(LocateCard locateCard);

    /**
     * 批量删除定位卡
     * 
     * @param cardLocateIds 需要删除的定位卡主键集合
     * @return 结果
     */
    public int deleteLocateCardByCardLocateIds(Long[] cardLocateIds);

    /**
     * 删除定位卡信息
     * 
     * @param cardLocateId 定位卡主键
     * @return 结果
     */
    public int deleteLocateCardByCardLocateId(Long cardLocateId);

    /**
     * 定位卡編號
     * @param cardLocateNo
     * @return
     */

    public int updateByLocationCardNo(String cardLocateNo,String cardLocateStatus);


    /**
     * 根据Sn号修改电量

     * @return
     */
    int updateBySn();

    /**
     * 根據SN查詢
     */
    List<LocateCard> selectCardBySn(String snNum);
}

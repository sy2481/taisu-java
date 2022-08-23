package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.LocateCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 定位卡Mapper接口
 * 
 * @author ZZF
 * @date 2022-03-06
 */
public interface LocateCardMapper 
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
     * 删除定位卡
     * 
     * @param cardLocateId 定位卡主键
     * @return 结果
     */
    public int deleteLocateCardByCardLocateId(Long cardLocateId);

    /**
     * 批量删除定位卡
     * 
     * @param cardLocateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLocateCardByCardLocateIds(Long[] cardLocateIds);

    /**
     * 根据定位卡编号，查询定位卡
     */
    LocateCard getByLocationCardNo(@Param("locationCardNo") String locationCardNo);

    /**
     * 根据定位卡编号修改状态
     */
    int updateByLocationCardNo(@Param("locationCardNo") String locationCardNo,@Param("cardLocateStatus") String cardLocateStatus);

    int updateNo(@Param("snNum") String snNum,@Param("cardLocateNo") String  cardLocateNo);

    /**
     * 根据Sn号修改电量
     * @param snNum
     * @param battery
     * @return
     */
    int updateBySn(@Param("snNum")String snNum,@Param("battery")Integer battery);

    /**
     * 根據SN查詢
     */
    List<LocateCard> selectCardBySn(@Param("snNum")String snNum);
}

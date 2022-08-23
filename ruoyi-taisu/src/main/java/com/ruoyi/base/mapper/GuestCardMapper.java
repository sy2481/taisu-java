package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.GuestCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 来宾卡Mapper接口
 *
 * @author ruoyi
 * @date 2022-03-26
 */
public interface GuestCardMapper {
    /**
     * 查询来宾卡
     *
     * @param id 来宾卡主键
     * @return 来宾卡
     */
    public GuestCard selectGuestCardById(Long id);

    /**
     * 查询来宾卡列表
     *
     * @param guestCard 来宾卡
     * @return 来宾卡集合
     */
    public List<GuestCard> selectGuestCardList(GuestCard guestCard);

    /**
     * 新增来宾卡
     *
     * @param guestCard 来宾卡
     * @return 结果
     */
    public int insertGuestCard(GuestCard guestCard);

    /**
     * 修改来宾卡
     *
     * @param guestCard 来宾卡
     * @return 结果
     */
    public int updateGuestCard(GuestCard guestCard);

    /**
     * 删除来宾卡
     *
     * @param id 来宾卡主键
     * @return 结果
     */
    public int deleteGuestCardById(Long id);

    /**
     * 批量删除来宾卡
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGuestCardByIds(Long[] ids);

    List<GuestCard> listByCardNo(@Param("no") String no,@Param("type")Integer type);


    int cardReturn(Long id);
}

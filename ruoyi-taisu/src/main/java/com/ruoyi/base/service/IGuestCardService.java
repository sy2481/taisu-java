package com.ruoyi.base.service;

import com.ruoyi.base.domain.GuestCard;

import java.util.List;

/**
 * 来宾卡Service接口
 *
 * @author ruoyi
 * @date 2022-03-26
 */
public interface IGuestCardService {
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
     * 批量删除来宾卡
     *
     * @param ids 需要删除的来宾卡主键集合
     * @return 结果
     */
    public int deleteGuestCardByIds(Long[] ids);

    /**
     * 删除来宾卡信息
     *
     * @param id 来宾卡主键
     * @return 结果
     */
    public int deleteGuestCardById(Long id);

    /**
     * 卡号查询
     * @param no
     * @return
     */
    List<GuestCard> listByCardNo(String no);

    /**
     * 卡号查+有效期
     * @param no
     * @return
     */
    List<GuestCard> getCardNo(String no);

    int cardReturn(Long id);
}

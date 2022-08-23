package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.GueatLog;
import com.ruoyi.base.domain.GuestCard;
import com.ruoyi.base.mapper.GueatLogMapper;
import com.ruoyi.base.mapper.GuestCardMapper;
import com.ruoyi.base.service.IGueatLogService;
import com.ruoyi.base.service.IGuestCardService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 来宾卡Service业务层处理
 *
 * @author ruoyi
 * @date 2022-03-26
 */
@Service
public class GuestCardServiceImpl implements IGuestCardService {
    @Autowired
    private GuestCardMapper guestCardMapper;

    @Autowired
    private IGueatLogService iGueatLogService;

    /**
     * 查询来宾卡
     *
     * @param id 来宾卡主键
     * @return 来宾卡
     */
    @Override
    public GuestCard selectGuestCardById(Long id) {
        GuestCard guestCard = guestCardMapper.selectGuestCardById(id);

        if (StringUtils.isNotBlank(guestCard.getArea())) {
            String[] split = guestCard.getArea().split(",");
            List<Long> list = new ArrayList<>();
            for (String s : split) {
                list.add(new Long(s));
            }
            guestCard.setFactoryIdArray(list.toArray(new Long[0]));
        }
        if (StringUtils.isNotBlank(guestCard.getPass())) {
            String[] split = guestCard.getPass().split(",");
            List<Long> list = new ArrayList<>();
            for (String s : split) {
                list.add(new Long(s));
            }
            guestCard.setPlcInfo(list.toArray(new Long[0]));
        }
        return guestCard;
    }

    /**
     * 查询来宾卡列表
     *
     * @param guestCard 来宾卡
     * @return 来宾卡
     */
    @Override
    public List<GuestCard> selectGuestCardList(GuestCard guestCard) {
        return guestCardMapper.selectGuestCardList(guestCard);
    }

    /**
     * 新增来宾卡
     *
     * @param guestCard 来宾卡
     * @return 结果
     */
    @Override
    public int insertGuestCard(GuestCard guestCard) {
        guestCard.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        guestCard.setCreateTime(DateUtils.getNowDate());
        return guestCardMapper.insertGuestCard(guestCard);
    }

    /**
     * 修改来宾卡
     *
     * @param guestCard 来宾卡
     * @return 结果
     */
    @Override
    public int updateGuestCard(GuestCard guestCard) {
        guestCard.setUpdateTime(DateUtils.getNowDate());
        //借用身份证ID不为空，保存借用日志
        if (StringUtils.isNotBlank(guestCard.getIdCard())){
            GueatLog gueatLog = new GueatLog();
            gueatLog.setOperationUser(SecurityUtils.getLoginUser().getUser().getNickName());
            gueatLog.setOperationInfo(guestCard.getUserName() + "借用来宾卡" + guestCard.getNo());
            iGueatLogService.insertGueatLog(gueatLog);
        }
        if (StringUtils.isNotNull(guestCard.getFactoryIdArray())) {
            String factory = StringUtils.join(guestCard.getFactoryIdArray(), ",");
            guestCard.setArea(factory);
        }
        if (StringUtils.isNotNull(guestCard.getPlcInfo())) {
            String plcInfo = StringUtils.join(guestCard.getPlcInfo(), ",");
            guestCard.setPass(plcInfo);
        }
        return guestCardMapper.updateGuestCard(guestCard);
    }

    /**
     * 批量删除来宾卡
     *
     * @param ids 需要删除的来宾卡主键
     * @return 结果
     */
    @Override
    public int deleteGuestCardByIds(Long[] ids) {
        return guestCardMapper.deleteGuestCardByIds(ids);
    }

    /**
     * 删除来宾卡信息
     *
     * @param id 来宾卡主键
     * @return 结果
     */
    @Override
    public int deleteGuestCardById(Long id) {
        return guestCardMapper.deleteGuestCardById(id);
    }

    @Override
    public List<GuestCard> listByCardNo(String no) {
        return guestCardMapper.listByCardNo(no, 0);
    }

    @Override
    public List<GuestCard> getCardNo(String no) {
        return guestCardMapper.listByCardNo(no, 1);
    }

    @Override
    public int cardReturn(Long id) {
        //添加归还日志
        GuestCard guestCard = guestCardMapper.selectGuestCardById(id);
        GueatLog gueatLog = new GueatLog();
        gueatLog.setOperationUser(SecurityUtils.getLoginUser().getUser().getNickName());
        gueatLog.setOperationInfo(guestCard.getUserName() + "归还来宾卡" + guestCard.getNo());
        iGueatLogService.insertGueatLog(gueatLog);
        return guestCardMapper.cardReturn(id);
    }
}

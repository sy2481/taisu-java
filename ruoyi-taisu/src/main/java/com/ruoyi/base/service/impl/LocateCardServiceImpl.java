package com.ruoyi.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.base.utils.ZhenQuUtils;
import com.ruoyi.base.vo.AccessTokenVO;
import com.ruoyi.base.vo.MacSuccessVo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.LocateCardMapper;
import com.ruoyi.base.domain.LocateCard;
import com.ruoyi.base.service.ILocateCardService;

/**
 * 定位卡Service业务层处理
 *
 * @author ZZF
 * @date 2022-03-06
 */
@Service
@Slf4j
public class LocateCardServiceImpl implements ILocateCardService {
    @Autowired
    private LocateCardMapper locateCardMapper;

    @Autowired
    private ZhenQuUtils zhenQuUtils;

    /**
     * 查询定位卡
     *
     * @param cardLocateId 定位卡主键
     * @return 定位卡
     */
    @Override
    public LocateCard selectLocateCardByCardLocateId(Long cardLocateId) {
        return locateCardMapper.selectLocateCardByCardLocateId(cardLocateId);
    }

    /**
     * 查询定位卡列表
     *
     * @param locateCard 定位卡
     * @return 定位卡
     */
    @Override
    public List<LocateCard> selectLocateCardList(LocateCard locateCard) {
        return locateCardMapper.selectLocateCardList(locateCard);
    }

    /**
     * 新增定位卡
     *
     * @param locateCard 定位卡
     * @return 结果
     */
    @Override
    public int insertLocateCard(LocateCard locateCard) {
        LocateCard cardNo = locateCardMapper.getByLocationCardNo(locateCard.getCardLocateNo());
        if (cardNo == null) {
            locateCard.setCreateTime(DateUtils.getNowDate());
            locateCard.setCardLocateStatus("0");
            return locateCardMapper.insertLocateCard(locateCard);
        } else {
            return -1;
        }

    }

    /**
     * 修改定位卡
     *
     * @param locateCard 定位卡
     * @return 结果
     */
    @Override
    public int updateLocateCard(LocateCard locateCard) {
        locateCard.setUpdateTime(DateUtils.getNowDate());
        return locateCardMapper.updateLocateCard(locateCard);
    }

    /**
     * 批量删除定位卡
     *
     * @param cardLocateIds 需要删除的定位卡主键
     * @return 结果
     */
    @Override
    public int deleteLocateCardByCardLocateIds(Long[] cardLocateIds) {
        return locateCardMapper.deleteLocateCardByCardLocateIds(cardLocateIds);
    }

    /**
     * 删除定位卡信息
     *
     * @param cardLocateId 定位卡主键
     * @return 结果
     */
    @Override
    public int deleteLocateCardByCardLocateId(Long cardLocateId) {
        return locateCardMapper.deleteLocateCardByCardLocateId(cardLocateId);
    }

    @Override
    public int updateByLocationCardNo(String cardLocateNo, String cardLocateStatus) {
        return locateCardMapper.updateByLocationCardNo(cardLocateNo, cardLocateStatus);
    }

    /**
     * 请求获取电量
     *
     * @return
     */
    @Override
    public int updateBySn() {
        List<LocateCard> locateCards = locateCardMapper.selectLocateCardList(null);
        List<String> macList = new ArrayList<>();
        if (locateCards.size() > 0) {
            locateCards.forEach(locateCard -> {
                if (StringUtils.isNotBlank(locateCard.getSnNum())) {
                    macList.add(locateCard.getSnNum());
                }
            });
        }
        //获取Token
        AccessTokenVO accessToken = zhenQuUtils.getAccessToken();
        log.info("token", accessToken);
        System.out.println(accessToken);
        if (accessToken.getErrorCode() == 0 && accessToken.getAccessToken() != null) {
            if (StringUtils.isNotBlank(accessToken.getAccessToken().getToken())) {
                List<MacSuccessVo> battery = zhenQuUtils.getBattery(accessToken.getAccessToken().getToken(), macList);
                if (battery.size() > 0) {
                    battery.forEach(macSuccessVo -> {
                        locateCardMapper.updateBySn(macSuccessVo.getMac(), macSuccessVo.getBattery());
                    });
                    return 0;
                }
            }
        }
        return 1;
    }

    @Override
    public List<LocateCard> selectCardBySn(String snNum) {
        return locateCardMapper.selectCardBySn(snNum);
    }


}

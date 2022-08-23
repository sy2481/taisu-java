package com.ruoyi.base.interact;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.ManWork;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.vo.CarCardVO;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shiva   2022-04-02 16:50
 */
@Log4j2
@Service
public class CarCardSendService {
    @Value("${plchlk.host}")
    private String host;

    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private UserJurisdiction userJurisdiction;

    /**
     * 厂商人员车卡权限下发
     */
    public void workCarCardDownSend(ManWork work) {
        if (work != null && StringUtils.isNotBlank(work.getWorkNo()) && StringUtils.isNotBlank(work.getCarCard())) {
            //车牌号可能存在多个，需要分割循环下发
            String[] split = work.getCarCard().split(",");
            for (String cardNo : split) {
                CarCardVO carCardVO = new CarCardVO();
                carCardVO.setAuthIsAll(false);
                //权限
                if (StringUtils.isBlank(work.getIp())) {
                    carCardVO.setDeviceNos(null);
                } else {
                    List<String> indexNos = new ArrayList<>();
                    // 有IP，直接拿相关的人脸设备
                    String[] splitIp = work.getIp().split(",");
                    for (String ip : splitIp) {
                        indexNos.addAll(equipmentMapper.listCarEquipmentCode(ip));
                    }
                    carCardVO.setDeviceNos(indexNos);
                }
                carCardVO.setCardNo(work.getWorkNo());
                carCardVO.setCardNumber(cardNo);
                carCardVO.setCardType(2);
                downSendCarCardMsg(carCardVO);
            }
        }
    }

    /**
     * 员工车卡权限下发
     */
    public void userCarCardDownSend(SysUser user) {
        if (user != null && StringUtils.isNotBlank(user.getIdCard()) && StringUtils.isNotBlank(user.getCarCard())) {
            //车牌号可能存在多个，需要分割循环下发
            String[] split = user.getCarCard().split(",");
            for (String cardNo : split) {
                CarCardVO carCardVO = new CarCardVO();

                carCardVO.setDeviceNos(userJurisdiction.getCodeByUser(user));
                carCardVO.setAuthIsAll(false);
                carCardVO.setCardNo(user.getIdCard());
                carCardVO.setCardNumber(cardNo);
                carCardVO.setCardType(1);
                downSendCarCardMsg(carCardVO);
            }
        }
    }


    /**
     * 车卡下发
     */
    public void downSendCarCardMsg(CarCardVO carCardVO) {
        String json = JSONObject.toJSONString(carCardVO);
        HttpUtils.sendJsonPost(host + "/hik/car/bind/card", json);
    }

    /**
     * 下发解绑车卡
     */
    public void downSendUnbindCarCard(String cardNumber) {
        JSONObject json = new JSONObject();
        json.put("cardNumber", cardNumber);
        HttpUtils.sendJsonPost(host + "/hik/car/untie/card", json.toJSONString());
    }


}

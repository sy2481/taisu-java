package com.ruoyi.base.interact;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.ManWork;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.mapper.EquipmentMapper;
import com.ruoyi.base.mapper.ManWorkMapper;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.vo.CarVO;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author shiva   2022-04-02 16:49
 */
@Log4j2
@Service
public class PlateSendService {
    @Value("${plchlk.host}")
    private String host;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private ManWorkMapper manWorkMapper;

    @Autowired
    private UserJurisdiction userJurisdiction;
    /**
     * 在接收到工单后，更新全部的工单车辆权限
     * 先全部删除，再全部下发
     */
    public void timerWorkPlate(String workNo) {
        try {
            //最后触发下车牌下发，全部下发
            ManWork manWork = manWorkMapper.selectManWorkByworkNo(workNo);
            if (StringUtils.isNotBlank(manWork.getCarId())) {
                String[] plateNos = manWork.getCarId().split(",");
                for (String plateNo : plateNos) {
                    unbindPlateNos(plateNo);
                }
            }
            workCarDownSend(manWork);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 厂商人员车牌权限下发
     */
    public void workCarDownSend(ManWork work) {
        if (work != null && StringUtils.isNotBlank(work.getWorkNo()) && StringUtils.isNotBlank(work.getCarId())) {
            //车牌号可能存在多个，需要分割循环下发
            String[] split = work.getCarId().split(",");
            for (String plateNo : split) {
                if (StringUtils.isBlank(plateNo)) {
                    continue;
                }
                CarVO carVO = new CarVO();
                //导入判断车辆类型
                if (work.getWorkType() != null && work.getWorkType() == 1) {
                    carVO.setCarType(2);
                }else {
                    carVO.setCarType(1);
                }
                carVO.setCarSn(work.getWorkNo());
                carVO.setCarNumber(plateNo);
                //权限
                if (StringUtils.isBlank(work.getIp())) {
                    carVO.setAuths(null);
                } else {
                    List<String> indexNos = new ArrayList<>();
                    // 有IP，直接拿相关的人脸设备
                    String[] splitIp = work.getIp().split(",");
                    for (String ip : splitIp) {
                        indexNos.addAll(equipmentMapper.listCarEquipmentCode(ip));
                    }
                    carVO.setAuths(indexNos);
                }
                carVO.setAuthIsAll(false);
                downSendCarMsg(carVO);
            }
        }
    }

    /**
     * 员工车牌权限下发
     */
    public void userCarDownSend(SysUser user) {
        if (user != null && StringUtils.isNotBlank(user.getIdCard()) && StringUtils.isNotBlank(user.getCarId())) {
            //车牌号可能存在多个，需要分割循环下发
            String[] split = user.getCarId().split(",");
            for (String plateNo : split) {
                CarVO carVO = new CarVO();
                carVO.setCarType(0);
                carVO.setCarSn(user.getIdCard());
                carVO.setCarNumber(plateNo);
                carVO.setAuths(userJurisdiction.getCodeByUser(user));
                carVO.setAuthIsAll(false);
                downSendCarMsg(carVO);
            }
        }
    }



    /**
     * 保存的时候，区分下修改了哪些车牌;
     * 可能新增，可能删除，可能同时存在
     */
    public void userPlateDiffSend(SysUser user, String oldPlateNos) {
        if (StringUtils.isBlank(oldPlateNos)) {
            //没有旧用户，或者旧的车牌号为空，全部新增旧完事了
            userCarDownSend(user);
            return;
        }
        //有旧用户，有旧车牌，需要对比车牌号
        String newPlateNos = user.getCarId();
        if (StringUtils.isBlank(newPlateNos)) {
            //新车牌为空，直接全部取消就行
            unbindPlateNos(oldPlateNos);
            return;
        }
        //然后才是有差异的，需要对比的
        //List<String> newPlateNoArray = Arrays.asList(newPlateNos.split(","));
       // List<String> oldPlateNoArray = Arrays.asList(oldPlateNos.split(","));
        //获得需要移除的,oldPlateNoArray
        //oldPlateNoArray.removeAll(newPlateNoArray);



        List<String> oldPlateNoArray =  new ArrayList<>();
        Collections.addAll(oldPlateNoArray,oldPlateNos.split(","));
        List<String> newPlateNoArray = new ArrayList<>();
        Collections.addAll(newPlateNoArray,newPlateNos.split(","));
        //获得需要移除的,oldPlateNoArray
        oldPlateNoArray.removeAll(newPlateNoArray);



        //循环接可以了
        for (String plateNo : oldPlateNoArray) {
            downSendUnbindPlateNo(plateNo);
        }
        //获得需要新增的,其实也不需要判断，直接全部下发就行了
        userCarDownSend(user);
    }

    /**
     * 根据车牌号数组，循环取消权限
     */
    public void unbindPlateNos(String plateNos) {
        if (StringUtils.isBlank(plateNos)) {
            return;
        }
        String[] split = plateNos.split(",");
        for (String plateNo : split) {
            downSendUnbindPlateNo(plateNo);
        }
    }


    public void downSendCarMsg(CarVO carVO) {
        String json = JSONObject.toJSONString(carVO);
        String resultStr = HttpUtils.sendJsonPost(host + "/hik/car", json);
    }

    /**
     * 下发解绑车牌
     */
    public void downSendUnbindPlateNo(String carNumber) {
        JSONObject json = new JSONObject();
        json.put("carNumber", carNumber);
        String resultStr = HttpUtils.sendJsonPost(host + "/hik/car/untie", json.toJSONString());
    }


}

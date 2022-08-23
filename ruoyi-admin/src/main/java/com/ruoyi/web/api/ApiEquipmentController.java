package com.ruoyi.web.api;

import com.alibaba.fastjson.JSON;
import com.ruoyi.base.bo.ControlJSONBo;
import com.ruoyi.base.bo.PlcRelationBO;
import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.LocateCard;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.mapper.EquipmentMapper;
import com.ruoyi.base.mapper.LocateCardMapper;
import com.ruoyi.base.service.ILocateCardService;
import com.ruoyi.base.service.impl.PlcEquipmentServiceImpl;
import com.ruoyi.base.utils.PlcRedisUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.api.basic.Response;
import com.ruoyi.web.api.bo.EquipmentBO;
import com.ruoyi.web.api.bo.TimeBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shiva   2022/3/12 21:28
 */
@Api(tags = "PLC")
@RestController
@RequestMapping("/api/equipment")
public class ApiEquipmentController {

    @Autowired
    private EquipmentMapper equipmentMapper;

    /*********************************************************/
    @Autowired
    private ILocateCardService locateCardService;
    @Autowired
    private LocateCardMapper locateCardMapper;

    @ApiOperation(value = "PLC关系")
    @ResponseBody
    @GetMapping("/getAll")
    public Response getAll() {
        try {
            List<EquipmentBO> result = new ArrayList<>();
            //先拿到全部的 PLC 和下属设备的关系列表
            List<PlcRelationBO> plcRelationBOS = equipmentMapper.listPlcRelation();
            for (PlcRelationBO plcRelationBO : plcRelationBOS) {
                // 对每一个 PLC ，都要专门区分，是不是车道设备和人脸设备绑定
                result.addAll(getEquipmentBOList(plcRelationBO));
            }
            //最后定位卡设备查出来
            List<HikEquipment> locationEquipList = equipmentMapper.listLocationEquip();
            //先要判断这个设备是不是已经被加入到列表了
            for (HikEquipment hikEquipment : locationEquipList) {
                //判断当前列表里是不是已经有了这个设备
                if (!judgeRepart(result, hikEquipment)) {
                    //不包含则进入这里进行添加
                    EquipmentBO equipmentBO = new EquipmentBO();
                    competeEquipmentBO(equipmentBO, hikEquipment, null);
                    result.add(equipmentBO);
                }
            }
            return Response.builder().code(0).data(result).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查询出错！");
    }

    private List<EquipmentBO> getEquipmentBOList(PlcRelationBO source) {
        List<EquipmentBO> result = new ArrayList<>();
        //根据关系，拿到下属设备列表
        //车辆设备绑定的人脸设备
        Long personId = source.getPersonId();
        //车辆设备
        Long carDeviceId = source.getCarDeviceId();
        //拿到人脸设备、车辆设备
        HikEquipment personDevice = equipmentMapper.getById(personId);
        HikEquipment carDevice = equipmentMapper.getById(carDeviceId);
        if (carDevice == null) {
            //人脸设备
            EquipmentBO personEquipmentBO = instanceEquipmentBO(source);
            competeEquipmentBO(personEquipmentBO, personDevice, null);
            result.add(personEquipmentBO);
        } else {
            //人脸设备
            EquipmentBO personEquipmentBO = instanceEquipmentBO(source);
            competeEquipmentBO(personEquipmentBO, personDevice, carDevice.getIndexCode());
            result.add(personEquipmentBO);
            //车辆设备
            EquipmentBO carEquipmentBO = instanceEquipmentBO(source);
            competeEquipmentBO(carEquipmentBO, carDevice, personDevice.getIndexCode());
            result.add(carEquipmentBO);
        }
        return result;
    }

    private EquipmentBO instanceEquipmentBO(PlcRelationBO source) {
        EquipmentBO item = new EquipmentBO();
        item.setPlcCommand(source.getPlcCommand());
        item.setPlcIp(source.getPlcIp());
        item.setPlcName(source.getPlcName());
        item.setPlcPort(source.getPlcPort());
        //门禁方式
        item.setControlJSONBo(JSON.parseObject(source.getControl(), ControlJSONBo.class));
        List<TimeBo> timeList=new ArrayList<>();
        if (StringUtils.isNotBlank(source.getTime1())){
            TimeBo timeBo=new TimeBo();
            String[] split = source.getTime1().split("-");
            if (split.length==2){
                timeBo.setStartTime(split[0]);
                timeBo.setEndTime(split[1]);
            }
            timeList.add(timeBo);
        }
        if (StringUtils.isNotBlank(source.getTime2())){
            TimeBo timeBo=new TimeBo();
            String[] split = source.getTime2().split("-");
            if (split.length==2){
                timeBo.setStartTime(split[0]);
                timeBo.setEndTime(split[1]);
            }
            timeList.add(timeBo);
        }
        if (StringUtils.isNotBlank(source.getTime3())){
            TimeBo timeBo=new TimeBo();
            String[] split = source.getTime3().split("-");
            if (split.length==2){
                timeBo.setStartTime(split[0]);
                timeBo.setEndTime(split[1]);
            }
            timeList.add(timeBo);
        }
        item.setTimeList(timeList);
        item.setControlType(source.getControlType());

        return item;
    }

    private void competeEquipmentBO(EquipmentBO equipmentBO, HikEquipment device, String bindIndexCode) {
        equipmentBO.setSubtitleMachineIp(device.getSubtitleMachineIp());
        equipmentBO.setSign(device.getSign());
        equipmentBO.setName(device.getName());
        equipmentBO.setIndexCode(device.getIndexCode());
        equipmentBO.setDeviceType(device.getDeviceType());
        equipmentBO.setDeviceAttribute(device.getEquipmentUseType());
        equipmentBO.setIp(device.getIp());
        equipmentBO.setBindIndexCode(bindIndexCode);
        equipmentBO.setLedCode(device.getLedCode());
    }

    private boolean judgeRepart(List<EquipmentBO> equipmentBOList, HikEquipment hikEquipment) {
        boolean result = false;
        for (EquipmentBO equipmentBO : equipmentBOList) {
            if (hikEquipment.getIndexCode().equals(equipmentBO.getIndexCode())) {
                result = true;
                break;
            }
        }
        return result;
    }



    @ApiOperation(value = "importData")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<LocateCard> util = new ExcelUtil<LocateCard>(LocateCard.class);
        List<LocateCard> locateCards = util.importExcel(file.getInputStream());
        for (int i = 0; i < locateCards.size(); i++) {
            LocateCard locateCard=new LocateCard();
            locateCard.setCardLocateNo(locateCards.get(i).getCardLocateNo());
            List<LocateCard> locateCards1 = locateCardService.selectLocateCardList(locateCard);
            if (locateCards1.size()>0){
                //修改
                locateCardMapper.updateNo(locateCards.get(i).getSnNum(),locateCards.get(i).getCardLocateNo());
            }else {
                //添加
                locateCardMapper.insertLocateCard(locateCards.get(i));
            }
        }


        return AjaxResult.success(666);
    }

}

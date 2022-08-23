package com.ruoyi.base.interact;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.vo.PersonVO;
import com.ruoyi.system.mapper.SysUserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shiva   2022-04-02 16:36
 */
@Log4j2
@Service
public class PersonSendService {
    @Value("${plchlk.host}")
    private String host;

    /**
     * 更新海康人员门禁权限
     */
    public void updateHikAuths(PersonVO personVO) {
        String json = JSONObject.toJSONString(personVO);
        HttpUtils.sendJsonPost(host + "/hik/person/issue/auth", json);
    }

    /**
     * 根据身份证号，更新人脸
     */
    public Integer updateUserFace(String inCard, String face) {
        JSONObject json = new JSONObject();
        json.put("personId", inCard);
        json.put("face", face);
        String str = json.toJSONString();
        String resultStr = HttpUtils.sendJsonPost(host + "/hik/person/issue/face", str);
        JSONObject downSednResultObj = JSONObject.parseObject(resultStr);
        return (Integer) downSednResultObj.get("code");
    }

    /**
     * 下发人员信息请求
     * 必要条件：定位卡、人脸照片、身份证号
     * 触发环境：员工绑定定位卡时
     * 员工编辑（修改）
     * 接口员工上传照片
     * return Inter
     */
    public Integer downSendPersonInfoRequest(PersonVO personVo) {
        String json = JSONObject.toJSONString(personVo);
        String resultStr = HttpUtils.sendJsonPost(host + "/hik/person", json);
        JSONObject downSednResultObj = JSONObject.parseObject(resultStr);
        return (Integer) downSednResultObj.get("code");
    }


    /**
     * 删除人员信息，
     */
    public void downSendDeletePerson(String idCard) {
        HttpUtils.sendPost(host + "/hik/person/delete/" + idCard, null);
    }

    /**
     * 更新设备信息
     */
    public  void equipmentCache(){
        HttpUtils.sendGet(host+"/hik/equipment/clean/cache",null);
    }


}

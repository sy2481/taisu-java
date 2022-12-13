package com.ruoyi.base.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 設備在線狀態
 * 
 * @author ruoyi
 *
 */
public enum EqDeviceStatus
{
    ON_LINE(1,"在線"),
    OFF_LINE(2,"離線");

    private long code;
    private String name;

    EqDeviceStatus(long code, String name) {
        this.code = code;
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取卡類型列表
     * @return
     */
    public static JSONArray getArray(){
        JSONArray jArr=new JSONArray();
        JSONObject jObj=new JSONObject();

        for (EqDeviceStatus value : EqDeviceStatus.values()) {
            jObj=new JSONObject();
            jObj.put("id",value.getCode());
            jObj.put("text",value.getName());
            jArr.add(jObj);
        }
        return jArr;
    }

    /**
     * 根據編號獲取名稱
     * @param code
     * @return
     */
    public static String getNameByCode(Long code){
        if(code==null){
            return null;
        }
        for (EqDeviceStatus value : EqDeviceStatus.values()) {
            if(value.getCode()==code){
                return value.getName();
            }
        }
        return null;
    }


}

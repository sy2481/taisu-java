package com.ruoyi.base.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 危化人員類型
 * 
 * @author ruoyi
 *
 */
public enum HcUserType
{
    DRIVER(1,"司機"),
    PASSENDER(2,"押運員");

    private long code;
    private String name;

    HcUserType(long code, String name) {
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

        for (HcUserType value : HcUserType.values()) {
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
        for (HcUserType value : HcUserType.values()) {
            if(value.getCode()==code){
                return value.getName();
            }
        }
        return null;
    }


}

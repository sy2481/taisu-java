package com.ruoyi.base.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 數據來源
 * 
 * @author ruoyi
 *
 */
public enum DataFrom
{
    MIS(1,"MIS"),
    MAN(2,"手動");

    private long code;
    private String name;

    DataFrom(long code, String name) {
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

        for (DataFrom value : DataFrom.values()) {
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
        for (DataFrom value : DataFrom.values()) {
            if(value.getCode()==code){
                return value.getName();
            }
        }
        return null;
    }


}

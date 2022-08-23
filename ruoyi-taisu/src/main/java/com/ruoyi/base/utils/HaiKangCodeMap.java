package com.ruoyi.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 兴跃
 */
public class HaiKangCodeMap {
    public static Map<String, String> HaiKangCode = new HashMap<String, String>(){
        {
            put("0x1f902300","人脸检测错误");
            put("0x1f902301","人脸检测超时 ");
            put("0x1f902303","图片两眼间距过小");
            put("0x1f902304","图片彩色置信度过低");
            put("0x1f902305","图片人脸角度过大");
            put("0x1f902306","图片清晰度过低");
            put("0x1f902307","图片过曝或过暗（灰阶值不符合要求）");
            put("0x1f902308","图片遮挡严重");
            put("0x1f902309","图片清晰度过低");

        }
    };
}

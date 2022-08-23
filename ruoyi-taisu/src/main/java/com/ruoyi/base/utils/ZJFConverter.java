package com.ruoyi.base.utils;

import com.spreada.utils.chinese.ZHConverter;

/**
 * @author 兴跃
 */
public class ZJFConverter {
    public static void main(String[] args) {
        String s = TraToSim("課長");
        System.out.println(s);
    }

    /**
     * 简体转繁体
     *
     * @param simpStr
     *            简体字符串
     * @return 繁体字符串
     */
    public static String SimToTra(String simpStr) {
        ZHConverter converter = ZHConverter
                .getInstance(ZHConverter.TRADITIONAL);
        return converter.convert(simpStr);
    }


    /**
     * 繁体转简体
     *
     * @param tradStr
     *            繁体字符串
     * @return 简体字符串
     */
    public static String TraToSim(String tradStr) {
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        return converter.convert(tradStr);
    }
}

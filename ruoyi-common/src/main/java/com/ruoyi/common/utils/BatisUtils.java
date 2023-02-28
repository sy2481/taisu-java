package com.ruoyi.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class BatisUtils {

    /**
     * 分批新增
     */
    public static <T> void batchSplitInsert(List<T> list, Consumer insertFunc) {
        int maxInsertItemNumPerTime = 500;//每次新增条数
        batchSplitInsert(list, insertFunc, maxInsertItemNumPerTime);
    }

    /**
     * 分批新增
     */
    public static <T> void batchSplitInsert(List<T> list, Consumer insertFunc, int maxInsertItemNumPerTime) {
        List<List<T>> all = new ArrayList<>();
        List<T> subList = null;
        if (list.size() > maxInsertItemNumPerTime) {
            int i = 0;
            while (i < list.size()) {
                if (i + maxInsertItemNumPerTime > list.size()) {
                    subList = list.subList(i, list.size());
                } else {
                    subList = list.subList(i, i + maxInsertItemNumPerTime);
                }
                i = i + maxInsertItemNumPerTime;
                all.add(subList);
            }
            all.parallelStream().forEachOrdered(insertFunc);
        } else {
            insertFunc.accept(list);
        }
    }


    /**
     * list切成多个list，以便进行数据库操作
     *
     * @param list
     * @param splitNum
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int splitNum) {
        List<List<T>> all = new ArrayList<List<T>>();
        List<T> subList = null;

        int i = 0;
        while (i < list.size()) {
            if (i + splitNum > list.size()) {
                subList = list.subList(i, list.size());
            } else {
                subList = list.subList(i, i + splitNum);
            }
            i = i + splitNum;
            all.add(subList);
        }
        return all;
    }

    /**
     * 如果Long是null，則返回-99
     *
     * @param num
     * @return
     */
    public static Long longIsNvl(Long num,Long defaultNum) {
        if (num == null) {
            if(defaultNum==null){
                return -99l;
            }
            else{
                return defaultNum;
            }
        } else {
            return num;
        }
    }

    /**
     * 字符串前後都加分號
     *
     * @param str
     * @return
     */
    public static String addSemicolonToTail(String str) {
        if (!StringUtils.isEmpty(str)) {
            while (str.substring(str.length() - 1).equals(";")) {
                str = str.substring(0, str.length() - 1);
            }
            str = ";" + str + ";";
        }
        return str;
    }

    /**
     * 字符串尾部和前面加一個逗號
     *
     * @param str
     * @return
     */
    public static String addCommaToHeadAndTail(String str) {
        if (!StringUtils.isEmpty(str)) {
            while (str.substring(0, 1).equals(",")) {
                str = str.substring(1);
            }
            while (str.substring(str.length() - 1).equals(",")) {
                str = str.substring(0, str.length() - 1);
            }
            str = "," + str + ",";
        }
        return str;
    }

    /**
     * 處理車牌
     *
     * @param license
     * @return
     */
    public static String dealLicense(String license) {
        //轉繁體+大寫+去空格
        if (!StringUtils.isEmpty(license)) {
            license = ZJFConverter.SimToTra(license);
            license = license.toUpperCase();
            license = license.trim();
            return license;
        } else {
            return "";
        }
    }

    /**
     * 從身份證獲取生日
     *
     * @param idNo
     * @return
     */
    public static Date getBirthFromIdNo(String idNo) {
        Date birth = null;
        if (!StringUtils.isEmpty(idNo)) {
            if (idNo.length() >= 18) {
                String birthStr = idNo.substring(6, 14);
                birth = DateUtils.parseDate(birthStr.substring(0, 4) + "-" + birthStr.substring(4, 6) + "-" + birthStr.substring(6));
            }
        }

        return birth;
    }

    /**
     * 從身份證獲取性別
     *
     * @param idNo
     * @return
     */
    public static long getSexFromIdNo(String idNo) {
        long sex = 3;
        int gender = 0;
        if (!StringUtils.isEmpty(idNo)) {
            if (idNo.length() >= 18) {
                //如果身份证号18位，取身份证号倒数第二位
                char c = idNo.charAt(idNo.length() - 2);
                gender = Integer.parseInt(String.valueOf(c));
            }
        }
        if (gender == 0) {
            sex = 3l;//未知
        } else if (gender % 2 == 1) {
            sex = 1l;//男
        } else {
            sex = 2l;//女
        }
        return sex;
    }

    public static void main(String[] args) {
        //BatisUtils.addSemicolonToTail("23423;234234;234234;;");
        //BatisUtils.addCommaToHeadAndTail(",,,,343m,,,");
    }
}

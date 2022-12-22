package com.ruoyi.common.utils;

import java.util.ArrayList;
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

    public static void main(String[] args) {
        //BatisUtils.addSemicolonToTail("23423;234234;234234;;");
        //BatisUtils.addCommaToHeadAndTail(",,,,343m,,,");
    }
}

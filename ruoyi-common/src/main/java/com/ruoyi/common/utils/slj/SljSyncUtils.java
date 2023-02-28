package com.ruoyi.common.utils.slj;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SljSyncUtils {

    /**
     * 獲取批量操作實體
     *
     * @param newList          新的數據
     * @param oldList          原有數據
     * @param keyFieldNames    比較字段名：根據比較字段值確定是更新還是修改
     * @param primaryFieldName 主鍵字段名：表的主鍵
     * @param syncFieldNames   同步字段名：如果這些字段的值沒變，則不更新數據
     * @param filterFunc       過濾：用於過濾數據的回調
     * @param addDealFunc      新增时處理：新增時回調
     * @param updateDealFunc   修改时處理：修改時回調
     * @return
     */
    public static <T> Map<String, List<T>> getOprMap(List<T> newList,
                                                     List<T> oldList,
                                                     List<String> keyFieldNames,
                                                     String primaryFieldName,
                                                     List<String> syncFieldNames,
                                                     SljSyncFilterFunc<T, T, Boolean> filterFunc,
                                                     Consumer<T> addDealFunc,
                                                     Consumer<T> updateDealFunc) {
        Map<String, List<T>> resultMap = new HashMap<>();
        List<T> addList = new ArrayList<>();
        List<T> updateList = new ArrayList<>();
        List<T> delList = new ArrayList<>();

        List<String> keyList = new ArrayList<>();
        for (T t : newList) {
            //獲取key
            String key = getValueByFiledNames(t, keyFieldNames);
            //比較值不能為空
            if (StringUtils.isEmpty(key)) {
                continue;
            }
            //如果已存在，則不繼續,防止重複數據
            if (keyList.contains(key)) {
                continue;
            } else {
                keyList.add(key);
            }

            //獲取oldT
            T oldT = null;
            boolean isAdd = true;//是否新增
            try {
                oldT = (T) t.getClass().newInstance();
                Optional<T> oldTOpt = oldList.stream().filter(x -> Objects.equals(key, getValueByFiledNames(x, keyFieldNames)))
                        .findFirst();
                if (oldTOpt != Optional.empty()) {
                    BeanUtils.copyProperties(oldTOpt.get(), oldT);
                    isAdd = false;//有值則修改
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //添加篩選函數
            if (filterFunc != null) {
                if (!filterFunc.test(oldT, t, isAdd)) {
                    continue;
                }
            }

            if (isAdd) {
                //新增时添加處理函數
                if (addDealFunc != null) {
                    addDealFunc.accept(t);
                }
                //默认添加新增人员、新增时间
                Field createBy = ReflectUtils.getAccessibleField(t, "createBy");
                Field createTime = ReflectUtils.getAccessibleField(t, "createTime");
                if (createBy != null) {
                    ReflectUtils.setFieldValue(t, "createBy", SecurityUtils.getUsernameDefaultSystem());
                }
                if (createTime != null) {
                    ReflectUtils.setFieldValue(t, "createTime", DateUtils.getNowDate());
                }
                addList.add(t);
            } else {
                //如果同步字段的值都相同，則不更新
                String oldValues = getValueByFiledNames(oldT, syncFieldNames);
                String newValues = getValueByFiledNames(t, syncFieldNames);
                if (oldValues.equals(newValues)) {
                    continue;
                }
                //採用老的主鍵值
                Object primaryValue = ReflectUtils.getFieldValue(oldT, primaryFieldName);
                ReflectUtils.setFieldValue(t, primaryFieldName, primaryValue);
                //修改时添加處理函數
                if (updateDealFunc != null) {
                    updateDealFunc.accept(t);
                }
                //默认添加修改人员、修改时间
                Field updateBy = ReflectUtils.getAccessibleField(t, "updateBy");
                Field updateTime = ReflectUtils.getAccessibleField(t, "updateTime");
                if (updateBy != null) {
                    ReflectUtils.setFieldValue(t, "updateBy", SecurityUtils.getUsernameDefaultSystem());
                }
                if (updateTime != null) {
                    ReflectUtils.setFieldValue(t, "updateTime", DateUtils.getNowDate());
                }
                updateList.add(t);
            }
        }

        //需要刪除的
        delList = oldList.stream()
                .filter(x -> !keyList.contains(getValueByFiledNames(x, keyFieldNames)))
                .collect(Collectors.toList());

        resultMap.put("addList", addList);
        resultMap.put("updateList", updateList);
        resultMap.put("delList", delList);

        return resultMap;

    }

    //根據比較字段獲取比較值
    private static <T> String getValueByFiledNames(T t, List<String> keyFieldNames) {
        String key = "";
        key = keyFieldNames.stream().map(x -> transObjToString(ReflectUtils.getFieldValue(t, x)))
                .collect(Collectors.joining("_"));
        return key;
    }

    //对象转为字符串
    private static String transObjToString(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }


}



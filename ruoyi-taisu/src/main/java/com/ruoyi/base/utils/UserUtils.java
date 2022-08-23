package com.ruoyi.base.utils;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 兴跃
 */
public class UserUtils {

    public static  List<Long> getUserDept(){
        String factoryId = SecurityUtils.getLoginUser().getUser().getFactoryId();
        if (StringUtils.isNotBlank(factoryId) ) {
            List<Long> longList = new ArrayList<>();
            String[] split = factoryId.split(",");
            for (int i = 0; i < split.length; i++) {
                longList.add(new Long(split[i]));
            }
           return longList;
        }
        return null;
    }
}

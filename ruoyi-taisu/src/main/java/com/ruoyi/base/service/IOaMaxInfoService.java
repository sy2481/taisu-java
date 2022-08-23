package com.ruoyi.base.service;

import com.ruoyi.base.domain.BaseDoor;
import com.ruoyi.base.domain.OaMaxInfo;

import java.util.List;

/**
 * 門Service接口
 *
 * @author ruoyi
 * @date 2022-07-16
 */
public interface IOaMaxInfoService {
    /**
     * 查詢
     *
     * @param factoryCode 厂区编号
     * @param tableName   表名
     * @return 数据
     */
    public OaMaxInfo selectOaMaxInfoByParam(String factoryCode, String tableName);

    /**
     * 新增
     *
     * @param oaMaxInfo
     * @return 結果
     */
    public int insertOaMaxInfo(OaMaxInfo oaMaxInfo);

    /**
     * 修改
     *
     * @param oaMaxInfo
     * @return 結果
     */
    public int updateOaMaxInfo(OaMaxInfo oaMaxInfo);

    /**
     * 初始化数据
     *
     * @param factoryCode
     * @param tableName
     * @return 最大值
     */
    public OaMaxInfo initOaMaxInfo(String factoryCode, String tableName);


}

package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.OaMaxInfo;
import com.ruoyi.base.mapper.BaseDoorMapper;
import com.ruoyi.base.mapper.OaMaxInfoMapper;
import com.ruoyi.base.service.IOaMaxInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class OaMaxInfoServiceImpl implements IOaMaxInfoService {

    @Autowired
    private OaMaxInfoMapper oaMaxInfoMapper;

    /**
     * 查詢
     *
     * @param tableName 表名
     * @return 数据
     */
    public OaMaxInfo selectOaMaxInfoByParam(String factoryCode, String tableName) {
        return oaMaxInfoMapper.selectOaMaxInfoByParam(factoryCode, tableName);
    }

    /**
     * 新增
     *
     * @param oaMaxInfo
     * @return 結果
     */
    public int insertOaMaxInfo(OaMaxInfo oaMaxInfo) {
        return oaMaxInfoMapper.insertOaMaxInfo(oaMaxInfo);
    }

    /**
     * 修改
     *
     * @param oaMaxInfo
     * @return 結果
     */
    public int updateOaMaxInfo(OaMaxInfo oaMaxInfo) {
        return oaMaxInfoMapper.updateOaMaxInfo(oaMaxInfo);
    }

    /**
     * 初始化
     *
     * @param tableName
     * @return
     */
    public OaMaxInfo initOaMaxInfo(String factoryCode, String tableName) {
        //获取最大ID
        OaMaxInfo oaMaxInfo = this.selectOaMaxInfoByParam(factoryCode,tableName);
        if (oaMaxInfo == null) {
            oaMaxInfo = new OaMaxInfo();
            oaMaxInfo.setFactoryCode(factoryCode);
            oaMaxInfo.setTableName(tableName);
            oaMaxInfo.setMaxId(0l);
            this.insertOaMaxInfo(oaMaxInfo);
        }
        return oaMaxInfo;
    }


}

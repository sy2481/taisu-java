package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.OaMaxInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * OA最大id的Mapper接口
 *
 * @author sunlj
 * @date 2022-07-18
 */
@Repository
public interface OaMaxInfoMapper {
    /**
     * 查詢
     *
     * @param factoryCode 厂区编号
     * @param tableName 表名
     * @return 数据
     */
    public OaMaxInfo selectOaMaxInfoByParam(@Param("factoryCode")String factoryCode, @Param("tableName")String tableName);

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

}

package com.ruoyi.base.mapper;


import com.ruoyi.base.domain.ManWorkFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 工单Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
@Component
public interface ManWorkFactoryMapper
{
    List<ManWorkFactory> selectManWorkByWorkId(Long workId);

    ManWorkFactory selectManWorkByFactoryId(Long factoryId);
    /**
     * 新增工單廠商關聯
     * 新增工單廠商關聯
     *
     * @param manWorkFactory 工單廠商關聯
     * @return 结果
     */
    public int insertManWorkFactory(ManWorkFactory manWorkFactory);

    /**
     * 根据工单号，查询
     */
    List<ManWorkFactory> getByWorkNo(@Param("workNo") String workNo);

    List<ManWorkFactory> selectManWorkInfo(ManWorkFactory manWorkFactory);

    int updateManWorkByFactoryId(ManWorkFactory manWorkFactory);
    int deleteWorkFactory(@Param("factoryId")Long factoryId);

    int delTimeFactory();
}

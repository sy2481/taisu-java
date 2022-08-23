package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.BasePassageway;

/**
 * 通道Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public interface BasePassagewayMapper 
{
    /**
     * 查詢通道
     * 
     * @param id 通道主鍵
     * @return 通道
     */
    public BasePassageway selectBasePassagewayById(Long id);

    /**
     * 查詢通道列表
     * 
     * @param basePassageway 通道
     * @return 通道集合
     */
    public List<BasePassageway> selectBasePassagewayList(BasePassageway basePassageway);

    /**
     * 新增通道
     * 
     * @param basePassageway 通道
     * @return 結果
     */
    public int insertBasePassageway(BasePassageway basePassageway);

    /**
     * 修改通道
     * 
     * @param basePassageway 通道
     * @return 結果
     */
    public int updateBasePassageway(BasePassageway basePassageway);

    /**
     * 删除通道
     * 
     * @param id 通道主鍵
     * @return 結果
     */
    public int deleteBasePassagewayById(Long id);

    /**
     * 批量删除通道
     * 
     * @param ids 需要删除的数据主鍵集合
     * @return 結果
     */
    public int deleteBasePassagewayByIds(Long[] ids);
}

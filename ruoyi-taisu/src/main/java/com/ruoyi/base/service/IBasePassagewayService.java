package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.BasePassageway;
import com.ruoyi.base.domain.BindPassagewayEquipment;
import com.ruoyi.base.domain.HikEquipment;

/**
 * 通道Service接口
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public interface IBasePassagewayService 
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
     * 批量刪除通道
     * 
     * @param ids 需要删除的通道主鍵集合
     * @return 結果
     */
    public int deleteBasePassagewayByIds(Long[] ids);

    /**
     * 删除通道信息
     * 
     * @param id 通道主鍵
     * @return 結果
     */
    public int deleteBasePassagewayById(Long id);

    /**
     * 批量綁定設備
     *
     * @param bindPassagewayEquipment 通道綁定設備
     * @return 結果
     */
    public int bindHikEquipmentById(BindPassagewayEquipment bindPassagewayEquipment);

    /**
     * 查詢通道下綁定的設備
     *
     * @param id 通道主鍵
     * @return 結果
     */
    public List<HikEquipment> selectPassageEquipment(Long id);

    /**
     * 查詢模式
     *
     * @return 結果
     */
    public List<String> selectModeEnum();

    /**
     * 查詢方向
     *
     * @return 結果
     */
    public List<String> selectDirectionEnum();

    /**
     * 查詢通道詳情列表
     *
     * @param basePassageway 通道
     * @return 通道
     */
    public List<BasePassageway> selectBasePassagewayDetailList(BasePassageway basePassageway);
}

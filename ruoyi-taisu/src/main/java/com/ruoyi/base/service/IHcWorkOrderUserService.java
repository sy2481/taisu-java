package com.ruoyi.base.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.domain.HcWorkOrderUser;

/**
 * 危化施工單明細Service接口
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcWorkOrderUserService 
{
    /**
     * 查询危化施工單明細
     * 
     * @param id 危化施工單明細主键
     * @return 危化施工單明細
     */
    public HcWorkOrderUser selectHcWorkOrderUserById(Long id);

    /**
     * 查询危化施工單明細列表
     * 
     * @param hcWorkOrderUser 危化施工單明細
     * @return 危化施工單明細集合
     */
    public List<HcWorkOrderUser> selectHcWorkOrderUserList(HcWorkOrderUser hcWorkOrderUser);

    /**
     * 新增危化施工單明細
     * 
     * @param hcWorkOrderUser 危化施工單明細
     * @return 结果
     */
    public int insertHcWorkOrderUser(HcWorkOrderUser hcWorkOrderUser);

    /**
     * 修改危化施工單明細
     * 
     * @param hcWorkOrderUser 危化施工單明細
     * @return 结果
     */
    public int updateHcWorkOrderUser(HcWorkOrderUser hcWorkOrderUser);

    /**
     * 批量删除危化施工單明細
     * 
     * @param ids 需要删除的危化施工單明細主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderUserByIds(Long[] ids);

    /**
     * 删除危化施工單明細信息
     * 
     * @param id 危化施工單明細主键
     * @return 结果
     */
    public int deleteHcWorkOrderUserById(Long id);

    /**
     * 獲取實體map
     *
     * @return
     */
    public Map<String, HcWorkOrderUser> getEntityMap(List<String> vhNoList);

    /**
     * 從中心庫獲取人臉數據
     */
    public int syncCent();

    /**
     * 強制更新人臉
     * @param ids
     */
    public int syncCentByIds(Long[] ids);

    /**
     * 從基礎表獲取數據
     * @param hcWorkOrderUserList
     * @return
     */
    public int syncFromHcUserByPage(List<HcWorkOrderUser> hcWorkOrderUserList);
}

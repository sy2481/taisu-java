package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.HcHrWorkOrderUser;

/**
 * 歷史危化施工單明細Service接口
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcHrWorkOrderUserService 
{
    /**
     * 查询歷史危化施工單明細
     * 
     * @param id 歷史危化施工單明細主键
     * @return 歷史危化施工單明細
     */
    public HcHrWorkOrderUser selectHcHrWorkOrderUserById(Long id);

    /**
     * 查询歷史危化施工單明細列表
     * 
     * @param hcHrWorkOrderUser 歷史危化施工單明細
     * @return 歷史危化施工單明細集合
     */
    public List<HcHrWorkOrderUser> selectHcHrWorkOrderUserList(HcHrWorkOrderUser hcHrWorkOrderUser);

    /**
     * 新增歷史危化施工單明細
     * 
     * @param hcHrWorkOrderUser 歷史危化施工單明細
     * @return 结果
     */
    public int insertHcHrWorkOrderUser(HcHrWorkOrderUser hcHrWorkOrderUser);

    /**
     * 修改歷史危化施工單明細
     * 
     * @param hcHrWorkOrderUser 歷史危化施工單明細
     * @return 结果
     */
    public int updateHcHrWorkOrderUser(HcHrWorkOrderUser hcHrWorkOrderUser);

    /**
     * 批量删除歷史危化施工單明細
     * 
     * @param ids 需要删除的歷史危化施工單明細主键集合
     * @return 结果
     */
    public int deleteHcHrWorkOrderUserByIds(Long[] ids);

    /**
     * 删除歷史危化施工單明細信息
     * 
     * @param id 歷史危化施工單明細主键
     * @return 结果
     */
    public int deleteHcHrWorkOrderUserById(Long id);
}

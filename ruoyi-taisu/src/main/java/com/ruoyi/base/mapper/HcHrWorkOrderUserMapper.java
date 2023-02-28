package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.HcHrWorkOrderUser;
import com.ruoyi.base.domain.HcWorkOrder;
import com.ruoyi.base.domain.HcWorkOrderUser;
import org.apache.ibatis.annotations.Param;

/**
 * 歷史危化施工單明細Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcHrWorkOrderUserMapper 
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
     * 删除歷史危化施工單明細
     * 
     * @param id 歷史危化施工單明細主键
     * @return 结果
     */
    public int deleteHcHrWorkOrderUserById(Long id);

    /**
     * 批量删除歷史危化施工單明細
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcHrWorkOrderUserByIds(Long[] ids);

    /**
     * 查询危化施工單人員
     * @param vhNos
     * @return
     */
    public List<HcHrWorkOrderUser> selectHcHrWorkOrderUserListByVhNos(@Param("vhNos") String[] vhNos);

    /**
     * 批量新增
     *
     * @param list 車
     * @return 结果
     */
    public int batchInsertHcHrWorkOrderUser(@Param("list") List<HcHrWorkOrderUser> list);
}

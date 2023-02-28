package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.HcWorkOrderAuth;
import com.ruoyi.base.domain.HcWorkOrderUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 危化工單組合認證Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-07
 */
public interface HcWorkOrderAuthMapper
{
    /**
     * 查询危化工單組合認證
     *
     * @param id 危化工單組合認證主键
     * @return 危化工單組合認證
     */
    public HcWorkOrderAuth selectHcWorkOrderAuthById(Long id);

    /**
     * 查询危化工單組合認證列表
     *
     * @param hcWorkOrderAuth 危化工單組合認證
     * @return 危化工單組合認證集合
     */
    public List<HcWorkOrderAuth> selectHcWorkOrderAuthList(HcWorkOrderAuth hcWorkOrderAuth);

    /**
     * 根據人臉設備IP查询危化工單組合認證列表
     *
     * @param personDeviceIp 人臉設備IP
     * @return 危化工單組合認證集合
     */
    public List<HcWorkOrderAuth> selectHcWorkOrderAuthByPersonDeviceIp(@Param("personDeviceIp") String personDeviceIp);

    /**
     * 新增危化工單組合認證
     *
     * @param hcWorkOrderAuth 危化工單組合認證
     * @return 结果
     */
    public int insertHcWorkOrderAuth(HcWorkOrderAuth hcWorkOrderAuth);

    /**
     * 修改危化工單組合認證
     *
     * @param hcWorkOrderAuth 危化工單組合認證
     * @return 结果
     */
    public int updateHcWorkOrderAuth(HcWorkOrderAuth hcWorkOrderAuth);

    /**
     * 删除危化工單組合認證
     *
     * @param id 危化工單組合認證主键
     * @return 结果
     */
    public int deleteHcWorkOrderAuthById(Long id);

    /**
     * 删除危化工單組合認證根据PLC的Id
     *
     * @param plcId 危化工單組合認證主键
     * @return 结果
     */
    public int deleteHcWorkOrderAuthByPLCId(@Param("plcId") Long plcId);
    /**
     * 批量删除危化工單組合認證
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderAuthByIds(Long[] ids);
}
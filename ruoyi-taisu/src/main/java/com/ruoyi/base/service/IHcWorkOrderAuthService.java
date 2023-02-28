package com.ruoyi.base.service;

import com.ruoyi.base.bo.HcWorkOrderCarForPermitBo;
import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcWorkOrderAuth;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.common.core.domain.entity.SysDept;

import java.util.List;
import java.util.Map;
/**
 * 危化工單組合認證Service接口
 *
 * @author ruoyi
 * @date 2023-02-07
 */
public interface IHcWorkOrderAuthService
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
     * 批量删除危化工單組合認證
     *
     * @param ids 需要删除的危化工單組合認證主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderAuthByIds(Long[] ids);

    /**
     * 删除危化工單組合認證信息
     *
     * @param id 危化工單組合認證主键
     * @return 结果
     */
    public int deleteHcWorkOrderAuthById(Long id);
}
package com.ruoyi.base.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.domain.HcWorkOrderUser;
import com.ruoyi.base.enums.VndIdNoType;
import com.ruoyi.common.core.domain.entity.SysDept;
import org.springframework.transaction.annotation.Transactional;

/**
 * 危化人員Service接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcUserService
{
    /**
     * 查询危化人員
     *
     * @param id 危化人員主键
     * @return 危化人員
     */
    public HcUser selectHcUserById(Long id);

    /**
     * 查询危化人員列表
     *
     * @param hcUser 危化人員
     * @return 危化人員集合
     */
    public List<HcUser> selectHcUserList(HcUser hcUser);

    /**
     * 新增危化人員
     *
     * @param hcUser 危化人員
     * @return 结果
     */
    public int insertHcUser(HcUser hcUser);

    /**
     * 修改危化人員
     *
     * @param hcUser 危化人員
     * @return 结果
     */
    public int updateHcUser(HcUser hcUser);

    /**
     * 批量删除危化人員
     *
     * @param ids 需要删除的危化人員主键集合
     * @return 结果
     */
    public int deleteHcUserByIds(Long[] ids);

    /**
     * 删除危化人員信息
     *
     * @param id 危化人員主键
     * @return 结果
     */
    public int deleteHcUserById(Long id);

    /**
     * 构造参数
     * @param hcWorkOrderUser
     * @return
     */
    public void buildUser(HcWorkOrderUser hcWorkOrderUser);

    /**
     * 判斷合約卡、臨時卡的idNo的類型 MAN=人 有牌車  3 無牌車
     * @param idNo
     * @return
     */
    public VndIdNoType checkIdNoType(String idNo);

    /**
     * 從中心庫獲取危化人員人臉數據
     */
    public int syncCentByHcUser(List<String> idNoList, boolean forceUpdate);

    /**
     * 獲取實體map
     *
     * @return
     */
    public Map<String, HcUser> getEntityMap(List<String> idNoList);

    /**
     * 查询危化人員
     *
     * @param idNo 危化人員身份证
     * @return 危化人員
     */
    public HcUser selectHcUserByIdNo(String idNo);

    /**
     * 從ERP獲取數據
     * @param boList
     * @return
     */
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant);

    /**
     * 從中心庫更新到hcUser
     * @return
     */
    public void syncCent();
}

package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.HcWorkOrderUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 危化施工單明細Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcWorkOrderUserMapper {
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
     * 删除危化施工單明細
     *
     * @param id 危化施工單明細主键
     * @return 结果
     */
    public int deleteHcWorkOrderUserById(Long id);

    /**
     * 批量删除危化施工單明細
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderUserByIds(Long[] ids);

    /**
     * 查询危化施工單人員
     *
     * @param vhNos
     * @return
     */
    public List<HcWorkOrderUser> selectHcWorkOrderUserListByVhNos(@Param("vhNos") String[] vhNos);

    /**
     * 查询危化工單明細
     *
     * @param ids
     * @return 危化工單明細
     */
    public List<HcWorkOrderUser> selectHcWorkOrderUserListByIds(Long[] ids);

    /**
     * 批量新增
     *
     * @param list
     * @return 结果
     */
    public int batchInsertHcWorkOrderUser(@Param("list") List<HcWorkOrderUser> list);

    /**
     * 批量修改
     *
     * @param list
     * @return 结果
     */
    public int batchUpdateHcWorkOrderUser(@Param("list") List<HcWorkOrderUser> list);

    /**
     * 根據工單號獲取人員數量
     *
     * @param vhNo
     * @return
     */
    public int selectPeopleCntByVhNo(@Param("vhNo") String vhNo, @Param("license")String license, @Param("ipltTm") Date ipltTm, @Param("exceptId") Long exceptId);

    /**
     * 是否存在同工單號、同身份證號的危化工單人員
     *
     * @param hcWorkOrderUser
     * @return
     */
    public List<HcWorkOrderUser> existSameIdno(HcWorkOrderUser hcWorkOrderUser);

    /**
     * 是否存在同工單號、同人員類型的危化工單明細
     *
     * @param hcWorkOrderUser
     * @return
     */
    public List<HcWorkOrderUser> existDriver(HcWorkOrderUser hcWorkOrderUser);

    /**
     * 根據車牌模糊查詢
     *
     * @param vhNo
     * @param idNo
     * @return
     */
    public List<HcWorkOrderUser> selectHcWorkOrderUserListByVhNoIdNo(@Param("vhNo") String vhNo, @Param("idNo") String idNo);

    /**
     * 根據車牌精確查詢
     *
     * @param vhNo
     * @param idNo
     * @return
     */
    public HcWorkOrderUser selectHcWorkOrderUserByVhNoIdNo(@Param("vhNo") String vhNo, @Param("idNo") String idNo,@Param("ipltTm") Date ipltTm);


}

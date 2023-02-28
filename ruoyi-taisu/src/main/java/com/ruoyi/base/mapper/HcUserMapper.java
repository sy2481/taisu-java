package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.HcUser;
import org.apache.ibatis.annotations.Param;

/**
 * 危化人員Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcUserMapper
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
     * 删除危化人員
     *
     * @param id 危化人員主键
     * @return 结果
     */
    public int deleteHcUserById(Long id);

    /**
     * 批量删除危化人員
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcUserByIds(Long[] ids);

    /**
     * 查询危化人員
     *
     * @param idNo 危化人員身份证
     * @return 危化人員
     */
    public HcUser selectHcUserByIdNo(String idNo);

    /**
     * 批量新增
     *
     * @param list 車
     * @return 结果
     */
    public int batchInsertHcUser(@Param("list") List<HcUser> list);

    /**
     * 批量修改
     *
     * @param list 車
     * @return 结果
     */
    public int batchUpdateHcUser(@Param("list") List<HcUser> list);

    /**
     * 根據身份證獲取數據
     * @param idNos
     * @return
     */
    public List<HcUser> selectHcUserListByIdNos(String[] idNos);
}

package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.ZqSubscribe;

/**
 * 报预紧信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-07
 */
public interface ZqSubscribeMapper
{
    /**
     * 查询报预紧信息
     *
     * @param id 报预紧信息主键
     * @return 报预紧信息
     */
    public ZqSubscribe selectZqSubscribeById(Long id);

    /**
     * 查询报预紧信息列表
     *
     * @param zqSubscribe 报预紧信息
     * @return 报预紧信息集合
     */
    public List<ZqSubscribe> selectZqSubscribeList(ZqSubscribe zqSubscribe);

    /**
     * 新增报预紧信息
     *
     * @param zqSubscribe 报预紧信息
     * @return 结果
     */
    public int insertZqSubscribe(ZqSubscribe zqSubscribe);

    /**
     * 修改报预紧信息
     *
     * @param zqSubscribe 报预紧信息
     * @return 结果
     */
    public int updateZqSubscribe(ZqSubscribe zqSubscribe);

    /**
     * 删除报预紧信息
     *
     * @param id 报预紧信息主键
     * @return 结果
     */
    public int deleteZqSubscribeById(Long id);

    /**
     * 批量删除报预紧信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZqSubscribeByIds(Long[] ids);
}

package com.ruoyi.base.service;


import com.ruoyi.base.domain.Device;

import java.util.List;

/**
 * deviceService接口
 *
 * @author ruoyi
 * @date 2022-10-21
 */
public interface IDeviceService
{
    /**
     * 查询device
     *
     * @param id device主键
     * @return device
     */
    public Device selectDeviceById(Long id);

    /**
     * 查询device列表
     *
     * @param device device
     * @return device集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增device
     *
     * @param device device
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改device
     *
     * @param device device
     * @return 结果
     */
    public int updateDevice(Device device);

    public int updateDeviceByIp(Device device);

    /**
     * 批量删除device
     *
     * @param ids 需要删除的device主键集合
     * @return 结果
     */
    public int deleteDeviceByIds(Long[] ids);

    /**
     * 删除device信息
     *
     * @param id device主键
     * @return 结果
     */
    public int deleteDeviceById(Long id);
}
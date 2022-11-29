package com.ruoyi.base.mapper;


import com.ruoyi.base.domain.Device;

import java.util.List;


/**
 * deviceMapper接口
 *
 * @author ruoyi
 * @date 2022-10-21
 */
public interface DeviceMapper {
    /**
     * 查询device
     *
     * @param id device主键
     * @return device
     */
    Device selectDeviceById(Long id);

    /**
     * 查询device列表
     *
     * @param device device
     * @return device集合
     */
    List<Device> selectDeviceList(Device device);

    List<Device> selectDeviceAllList();

    /**
     * 新增device
     *
     * @param device device
     * @return 结果
     */
    int insertDevice(Device device);

    /**
     * 修改device
     *
     * @param device device
     * @return 结果
     */
    int updateDevice(Device device);

    int updateDeviceByIp(Device device);

    /**
     * 删除device
     *
     * @param id device主键
     * @return 结果
     */
    int deleteDeviceById(Long id);

    /**
     * 批量删除device
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteDeviceByIds(Long[] ids);
}
package com.ruoyi.base.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.base.domain.Device;
import com.ruoyi.base.mapper.DeviceMapper;
import com.ruoyi.base.service.IDeviceService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * deviceService业务层处理
 *
 * @author ruoyi
 * @date 2022-10-21
 */
@Service
public class IDeviceServiceImpl implements IDeviceService
{
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 查询device
     *
     * @param id device主键
     * @return device
     */
    @Override
    public Device selectDeviceById(Long id)
    {
        return deviceMapper.selectDeviceById(id);
    }

    /**
     * 查询device列表
     *
     * @param device device
     * @return device
     */
    @Override
    public List<Device> selectDeviceList(Device device)
    {
        return deviceMapper.selectDeviceList(device);
    }

    /**
     * 新增device
     *
     * @param device device
     * @return 结果
     */
    @Override
    public int insertDevice(Device device)
    {
        return deviceMapper.insertDevice(device);
    }

    /**
     * 修改device
     *
     * @param device device
     * @return 结果
     */
    @Override
    public int updateDevice(Device device)
    {
        device.setUpdateTime(DateUtils.getNowDate());
        return deviceMapper.updateDevice(device);
    }

    @Override
    public int updateDeviceByIp(Device device)
    {
        device.setUpdateTime(new Date());
        return deviceMapper.updateDeviceByIp(device);
    }

    /**
     * 批量删除device
     *
     * @param ids 需要删除的device主键
     * @return 结果
     */
    @Override
    public int deleteDeviceByIds(Long[] ids)
    {
        return deviceMapper.deleteDeviceByIds(ids);
    }

    /**
     * 删除device信息
     *
     * @param id device主键
     * @return 结果
     */
    @Override
    public int deleteDeviceById(Long id)
    {
        return deviceMapper.deleteDeviceById(id);
    }
}
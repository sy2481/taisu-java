package com.ruoyi.web.controller.base;



import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.base.domain.Device;
import com.ruoyi.base.service.IDeviceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * deviceController
 *
 * @author ruoyi
 * @date 2022-10-21
 */
@RestController
@RequestMapping("/system/device")
public class DeviceController extends BaseController
{
    @Autowired
    private IDeviceService IDeviceService;

    /**
     * 查询device列表
     */
    @PreAuthorize("@ss.hasPermi('system:device:list')")
    @GetMapping("/list")
    public TableDataInfo list(Device device)
    {
        startPage();
        List<Device> list = IDeviceService.selectDeviceList(device);
        return getDataTable(list);
    }

    /**
     * 导出device列表
     */
    @PreAuthorize("@ss.hasPermi('system:device:export')")
    @Log(title = "device", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Device device)
    {
        List<Device> list = IDeviceService.selectDeviceList(device);
        ExcelUtil<Device> util = new ExcelUtil<Device>(Device.class);
        util.exportExcel(response, list, "device数据");
    }

    /**
     * 获取device详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:device:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(IDeviceService.selectDeviceById(id));
    }

    /**
     * 新增device
     */
    @PreAuthorize("@ss.hasPermi('system:device:add')")
    @Log(title = "device", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Device device)
    {
        return toAjax(IDeviceService.insertDevice(device));
    }

    /**
     * 修改device
     */
    @PreAuthorize("@ss.hasPermi('system:device:edit')")
    @Log(title = "device", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Device device)
    {
        return toAjax(IDeviceService.updateDevice(device));
    }

    /**
     * 删除device
     */
    @PreAuthorize("@ss.hasPermi('system:device:remove')")
    @Log(title = "device", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(IDeviceService.deleteDeviceByIds(ids));
    }
}
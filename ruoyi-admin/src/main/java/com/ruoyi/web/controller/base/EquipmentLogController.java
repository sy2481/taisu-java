package com.ruoyi.web.controller.base;



import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.base.domain.EquipmentLog;
import com.ruoyi.base.service.IEquipmentLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备在线记录Controller
 *
 * @author ruoyi
 * @date 2022-04-27
 */
@RestController
@RequestMapping("/equipment/log")
public class EquipmentLogController extends BaseController
{
    @Autowired
    private IEquipmentLogService equipmentLogService;

    /**
     * 查询设备在线记录列表
     */

    @GetMapping("/list")
    public TableDataInfo list(EquipmentLog equipmentLog)
    {
        startPage();
        List<EquipmentLog> list = equipmentLogService.selectEquipmentLogList(equipmentLog);
        return getDataTable(list);
    }

    /**
     * 导出设备在线记录列表
     */

    @Log(title = "设备在线记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentLog equipmentLog)
    {
        List<EquipmentLog> list = equipmentLogService.selectEquipmentLogList(equipmentLog);
        ExcelUtil<EquipmentLog> util = new ExcelUtil<EquipmentLog>(EquipmentLog.class);
        util.exportExcel(response, list, "设备在线记录数据");
    }

    /**
     * 获取设备在线记录详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(equipmentLogService.selectEquipmentLogById(id));
    }

    /**
     * 新增设备在线记录
     */

    @Log(title = "设备在线记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentLog equipmentLog)
    {
        return toAjax(equipmentLogService.insertEquipmentLog(equipmentLog));
    }

    /**
     * 修改设备在线记录
     */

    @Log(title = "设备在线记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentLog equipmentLog)
    {
        return toAjax(equipmentLogService.updateEquipmentLog(equipmentLog));
    }

    /**
     * 删除设备在线记录
     */

    @Log(title = "设备在线记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipmentLogService.deleteEquipmentLogByIds(ids));
    }
}


package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.PlcHik;
import com.ruoyi.base.mapper.EquipmentMapper;
import com.ruoyi.base.service.IHikEquipmentService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * HIK 海康设备Controller
 *
 * @author ruoyi
 * @date 2022-04-06
 */
@RestController
@RequestMapping("/base/hik")
public class HikEquipmentController extends BaseController {
    @Autowired
    private IHikEquipmentService hikEquipmentService;
    @Autowired
    private EquipmentMapper equipmentMapper;

    /**
     * 查询HIK 海康设备列表
     */
    @PreAuthorize("@ss.hasPermi('base:hik:list')")
    @GetMapping("/list")
    public TableDataInfo list(HikEquipment hikEquipment) {
        startPage();
        List<HikEquipment> list = hikEquipmentService.selectHikEquipmentList(hikEquipment);
        return getDataTable(list);
    }

    /**
     * 导出HIK 海康设备列表
     */
    @PreAuthorize("@ss.hasPermi('base:hik:export')")
    @Log(title = "HIK 海康设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HikEquipment hikEquipment) {
        List<HikEquipment> list = hikEquipmentService.selectHikEquipmentList(hikEquipment);
        ExcelUtil<HikEquipment> util = new ExcelUtil<HikEquipment>(HikEquipment.class);
        util.exportExcel(response, list, "HIK 海康设备数据");
    }

    /**
     * 获取HIK 海康设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:hik:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(hikEquipmentService.selectHikEquipmentById(id));
    }

    /**
     * 新增HIK 海康设备
     */
    @PreAuthorize("@ss.hasPermi('base:hik:add')")
    @Log(title = "HIK 海康设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HikEquipment hikEquipment) {
        return toAjax(hikEquipmentService.insertHikEquipment(hikEquipment));
    }

    /**
     * 修改HIK 海康设备
     */
    @PreAuthorize("@ss.hasPermi('base:hik:edit')")
    @Log(title = "HIK 海康设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HikEquipment hikEquipment) {
        return toAjax(hikEquipmentService.updateHikEquipment(hikEquipment));
    }

    /**
     * 删除HIK 海康设备
     */
    @PreAuthorize("@ss.hasPermi('base:hik:remove')")
    @Log(title = "HIK 海康设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(hikEquipmentService.deleteHikEquipmentByIds(ids));
    }

    /**
     * 根据PLC id，查询列表清单
     */
    @GetMapping(value = "/listRelationEquip")
    public AjaxResult listRelationEquip(Long plcId) {
        return AjaxResult.success(hikEquipmentService.listRelationEquip(plcId));
    }

    /**
     * 根据 PlcHik ID，获得对象
     */
    @GetMapping(value = "/getPlcHilRelation")
    public AjaxResult getPlcHilRelation(Long id) {
        return AjaxResult.success(hikEquipmentService.getPlcHilRelation(id));
    }

    /**
     * 根据类型，获取设备列表
     */
    @GetMapping(value = "/listEquipByType")
    public AjaxResult listEquipByType(Integer type) {
        return AjaxResult.success(equipmentMapper.listEquipByType(type));
    }

    /**
     * 新增中間關係
     */
    @PostMapping(value = "addPlcHik")
    public AjaxResult addPlcHik(@RequestBody PlcHik plcHik) {
        return toAjax(hikEquipmentService.insertPlcHik(plcHik));
    }

    /**
     * 更新中間關係
     */
    @Log(title = "更新 PLC HIK 中间关联信息", businessType = BusinessType.UPDATE)
    @PutMapping(value = "editPlcHik")
    public AjaxResult editPlcHik(@RequestBody PlcHik plcHik) {
        return toAjax(hikEquipmentService.updatePlcHik(plcHik));
    }

    /**
     * 刪除中間關係
     */
    @Log(title = "删除 PLC HIK 中间关联信息", businessType = BusinessType.UPDATE)
    @DeleteMapping("removePlcHik/{id}")
    public AjaxResult removePlcHik(@PathVariable Long id) {
        return toAjax(hikEquipmentService.deletePlcHikById(id));
    }

    /**
     * 查询HIK 未綁定的海康設備
     */
    @PreAuthorize("@ss.hasPermi('base:hik:notBindList')")
    @GetMapping("/notBindList")
    public AjaxResult notBindList(HikEquipment hikEquipment) {
        return AjaxResult.success(hikEquipmentService.selectHikEquipmentNotBindList(hikEquipment));
    }
}

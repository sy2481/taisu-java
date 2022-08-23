package com.ruoyi.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.ControlBo;
import com.ruoyi.base.bo.ControlJSONBo;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.base.utils.PlcRedisUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PLC 道闸Controller
 *
 * @author ruoyi
 * @date 2022-04-05
 */
@RestController
@RequestMapping("/base/equipment")
public class PlcEquipmentController extends BaseController {
    @Autowired
    private IPlcEquipmentService plcEquipmentService;
    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询PLC 道闸列表
     */
    @PreAuthorize("@ss.hasPermi('base:equipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlcEquipment plcEquipment) {
        startPage();
        List<PlcEquipment> list = plcEquipmentService.selectPlcEquipmentList(plcEquipment);
        return getDataTable(list);
    }


    @GetMapping(value = "getDept")
    public AjaxResult getDept() {
        List<SysDept> plant = deptService.getPlant();
        return AjaxResult.success("查询成功", plant);
    }

    /**
     * 门禁方式
     */
    @PostMapping(value = "addControl")
    public AjaxResult addControl(HttpServletResponse response, @RequestBody ControlBo controlBo) {

        ControlJSONBo controlJSONBo = new ControlJSONBo();
        if (controlBo != null) {
            controlJSONBo.setLane(controlBo.getLane());
            controlJSONBo.setHumane(controlBo.getHumane());
            controlJSONBo.setDanger(controlBo.getDanger());
        }
        PlcEquipment plcEquipment = new PlcEquipment();
        plcEquipment.setId(controlBo.getPlcId());
        plcEquipment.setControl(JSON.toJSONString(controlJSONBo));
        plcEquipmentService.updatePlcEquipment(plcEquipment);
        return success();
    }

    /**
     * 查询门禁方式
     */
    @GetMapping(value = "getControl/{plcId}")
    public AjaxResult getControl(@PathVariable("plcId") Long plcId) {
        PlcEquipment plcEquipment = plcEquipmentService.selectPlcEquipmentById(plcId);
        ControlJSONBo controlJSONBo = JSONObject.parseObject(plcEquipment.getControl(), ControlJSONBo.class);
        return AjaxResult.success("查询成功", controlJSONBo);

    }

    /**
     * 导出PLC 道闸列表
     */
    @PreAuthorize("@ss.hasPermi('base:equipment:export')")
    @Log(title = "PLC 道闸", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlcEquipment plcEquipment) {
        List<PlcEquipment> list = plcEquipmentService.selectPlcEquipmentList(plcEquipment);
        ExcelUtil<PlcEquipment> util = new ExcelUtil<PlcEquipment>(PlcEquipment.class);
        util.exportExcel(response, list, "PLC 道闸数据");
    }

    /**
     * 获取PLC 道闸详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:equipment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(plcEquipmentService.selectPlcEquipmentById(id));
    }

    /**
     * 新增PLC 道闸
     */
    @PreAuthorize("@ss.hasPermi('base:equipment:add')")
    @Log(title = "PLC 道闸", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlcEquipment plcEquipment) {
        return toAjax(plcEquipmentService.insertPlcEquipment(plcEquipment));
    }

    /**
     * 修改PLC 道闸
     */
    @PreAuthorize("@ss.hasPermi('base:equipment:edit')")
    @Log(title = "PLC 道闸", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlcEquipment plcEquipment) {
        //三个字段    00:00-03:00
        plcEquipment.setTime1(plcEquipment.getStartTime1()+"-"+plcEquipment.getEndTime1());
        plcEquipment.setTime2(plcEquipment.getStartTime2()+"-"+plcEquipment.getEndTime2());
        plcEquipment.setTime3(plcEquipment.getStartTime3()+"-"+plcEquipment.getEndTime3());
        return toAjax(plcEquipmentService.updatePlcEquipment(plcEquipment));
    }

    /**
     * 删除PLC 道闸
     */
    @PreAuthorize("@ss.hasPermi('base:equipment:remove')")
    @Log(title = "PLC 道闸", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {

        return toAjax(plcEquipmentService.deletePlcEquipmentByIds(ids));
    }

    /**
     * 根据厂区获取PLC
     */
    @GetMapping(value = "getPlcByArea/{deptId}")
    public AjaxResult getPlcByArea(@PathVariable("deptId") Long[] deptId ) {
        List<Long> longList = new ArrayList<>(Arrays.asList(deptId));
        List<PlcEquipment> plcByArea = plcEquipmentService.getPlcEquipmentByDept(longList);
        return AjaxResult.success("PLC信息", plcByArea);
    }
}

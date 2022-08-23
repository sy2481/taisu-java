package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.InOutLog;
import com.ruoyi.base.service.IInOutLogService;
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
import java.util.Date;
import java.util.List;

/**
 * 进出记录Controller
 * 
 * @author ruoyi
 * @date 2022-03-07
 */
@RestController
@RequestMapping("/base/log")
public class InOutLogController extends BaseController
{
    @Autowired
    private IInOutLogService inOutLogService;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询进出记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(InOutLog inOutLog)
    {
        startPage();
        List<InOutLog> list = inOutLogService.selectInOutLogList(inOutLog);
        return getDataTable(list);
    }

    /**
     * 导出进出记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:log:export')")
    @Log(title = "进出记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InOutLog inOutLog)
    {
        List<InOutLog> list = inOutLogService.selectInOutLogList(inOutLog);
        ExcelUtil<InOutLog> util = new ExcelUtil<InOutLog>(InOutLog.class);
        util.exportExcel(response, list, "进出记录数据");
    }

    /**
     * 获取进出记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(inOutLogService.selectInOutLogById(id));
    }

    /**
     * 新增进出记录
     */
    @PreAuthorize("@ss.hasPermi('base:log:add')")
    @Log(title = "进出记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InOutLog inOutLog)
    {
        inOutLog.setValidType(0);
        inOutLog.setOperationTime(new Date());
        if (inOutLog.getDeptId() != null) {
            SysDept sysDept = sysDeptService.selectDeptById(inOutLog.getDeptId());
            inOutLog.setAreaName(sysDept.getDeptName());
        }
        return toAjax(inOutLogService.insertInOutLog(inOutLog));
    }

    /**
     * 修改进出记录
     */
    @PreAuthorize("@ss.hasPermi('base:log:edit')")
    @Log(title = "进出记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InOutLog inOutLog)
    {
        return toAjax(inOutLogService.updateInOutLog(inOutLog));
    }

    /**
     * 删除进出记录
     */
    @PreAuthorize("@ss.hasPermi('base:log:remove')")
    @Log(title = "进出记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(inOutLogService.deleteInOutLogByIds(ids));
    }

    /**
     * 作废
     * @param id
     * @return
     */
    @GetMapping(value = "removeLog")
    public AjaxResult removeLog(@RequestParam Long id) {
        return toAjax(inOutLogService.removeLog(id));
    }

}

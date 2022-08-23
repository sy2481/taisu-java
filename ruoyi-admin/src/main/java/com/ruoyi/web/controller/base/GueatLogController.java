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
import com.ruoyi.base.domain.GueatLog;
import com.ruoyi.base.service.IGueatLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 来宾卡操作记录Controller
 * 
 * @author xxy
 * @date 2022-06-05
 */
@RestController
@RequestMapping("/base/gueatLog")
public class GueatLogController extends BaseController
{
    @Autowired
    private IGueatLogService gueatLogService;

    /**
     * 查询来宾卡操作记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(GueatLog gueatLog)
    {
        startPage();
        List<GueatLog> list = gueatLogService.selectGueatLogList(gueatLog);
        return getDataTable(list);
    }

    /**
     * 导出来宾卡操作记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:log:export')")
    @Log(title = "来宾卡操作记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GueatLog gueatLog)
    {
        List<GueatLog> list = gueatLogService.selectGueatLogList(gueatLog);
        ExcelUtil<GueatLog> util = new ExcelUtil<GueatLog>(GueatLog.class);
        util.exportExcel(response, list, "来宾卡操作记录数据");
    }

    /**
     * 获取来宾卡操作记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gueatLogService.selectGueatLogById(id));
    }

    /**
     * 新增来宾卡操作记录
     */
    @PreAuthorize("@ss.hasPermi('base:log:add')")
    @Log(title = "来宾卡操作记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GueatLog gueatLog)
    {
        return toAjax(gueatLogService.insertGueatLog(gueatLog));
    }

    /**
     * 修改来宾卡操作记录
     */
    @PreAuthorize("@ss.hasPermi('base:log:edit')")
    @Log(title = "来宾卡操作记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GueatLog gueatLog)
    {
        return toAjax(gueatLogService.updateGueatLog(gueatLog));
    }

    /**
     * 删除来宾卡操作记录
     */
    @PreAuthorize("@ss.hasPermi('base:log:remove')")
    @Log(title = "来宾卡操作记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gueatLogService.deleteGueatLogByIds(ids));
    }
}

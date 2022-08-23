package com.ruoyi.web.controller.base;


import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.base.domain.InOutLogPerilous;
import com.ruoyi.base.service.IInOutLogPerilousService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化进出记录Controller
 *
 * @author ruoyi
 * @date 2022-04-28
 */
@RestController
@RequestMapping("/base/perilous")
public class InOutLogPerilousController extends BaseController
{
    @Autowired
    private IInOutLogPerilousService inOutLogPerilousService;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询危化进出记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(InOutLogPerilous inOutLogPerilous)
    {
        startPage();
        List<InOutLogPerilous> list = inOutLogPerilousService.selectInOutLogPerilousList(inOutLogPerilous);
        return getDataTable(list);
    }

    /**
     * 导出危化进出记录列表
     */
    @Log(title = "危化进出记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InOutLogPerilous inOutLogPerilous)
    {
        List<InOutLogPerilous> list = inOutLogPerilousService.selectInOutLogPerilousList(inOutLogPerilous);
        ExcelUtil<InOutLogPerilous> util = new ExcelUtil<InOutLogPerilous>(InOutLogPerilous.class);
        util.exportExcel(response, list, "危化进出记录数据");
    }

    /**
     * 获取危化进出记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(inOutLogPerilousService.selectInOutLogPerilousById(id));
    }

    /**
     * 新增危化进出记录
     */
    @Log(title = "危化进出记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InOutLogPerilous inOutLogPerilous)
    {
        if (inOutLogPerilous.getDeptId() != null) {
            SysDept sysDept = sysDeptService.selectDeptById(inOutLogPerilous.getDeptId());
            inOutLogPerilous.setAreaName(sysDept.getDeptName());
        }
        return toAjax(inOutLogPerilousService.insertInOutLogPerilous(inOutLogPerilous));
    }

    /**
     * 修改危化进出记录
     */
    @Log(title = "危化进出记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InOutLogPerilous inOutLogPerilous)
    {
        return toAjax(inOutLogPerilousService.updateInOutLogPerilous(inOutLogPerilous));
    }

    /**
     * 删除危化进出记录
     */
    @Log(title = "危化进出记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(inOutLogPerilousService.deleteInOutLogPerilousByIds(ids));
    }


    @GetMapping(value = "removeLog")
    public AjaxResult removeLog(@RequestParam Long id) {
        return toAjax(inOutLogPerilousService.removeLog(id));
    }


}


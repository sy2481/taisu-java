package com.ruoyi.web.controller.base;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.base.domain.InOutLogGuest;
import com.ruoyi.base.service.IInOutLogGuestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 来宾卡进出记录Controller
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@RestController
@RequestMapping("/base/guest")
public class InOutLogGuestController extends BaseController {
    @Autowired
    private IInOutLogGuestService inOutLogGuestService;

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询来宾卡进出记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:guest:list')")
    @GetMapping("/list")
    public TableDataInfo list(InOutLogGuest inOutLogGuest) {
        startPage();
        List<InOutLogGuest> list = inOutLogGuestService.selectInOutLogGuestList(inOutLogGuest);
        return getDataTable(list);
    }

    /**
     * 导出来宾卡进出记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:guest:export')")
    @Log(title = "来宾卡进出记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InOutLogGuest inOutLogGuest) {
        List<InOutLogGuest> list = inOutLogGuestService.selectInOutLogGuestList(inOutLogGuest);
        ExcelUtil<InOutLogGuest> util = new ExcelUtil<InOutLogGuest>(InOutLogGuest.class);
        util.exportExcel(response, list, "来宾卡进出记录数据");
    }

    /**
     * 获取来宾卡进出记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:guest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(inOutLogGuestService.selectInOutLogGuestById(id));
    }

    /**
     * 新增来宾卡进出记录
     */
    @PreAuthorize("@ss.hasPermi('base:guest:add')")
    @Log(title = "来宾卡进出记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InOutLogGuest inOutLogGuest) {
        inOutLogGuest.setOperationTime(new Date());
        inOutLogGuest.setValidType(0);
        if (inOutLogGuest.getDeptId() != null) {
            SysDept sysDept = sysDeptService.selectDeptById(inOutLogGuest.getDeptId());
            inOutLogGuest.setAreaName(sysDept.getDeptName());
        }
        return toAjax(inOutLogGuestService.insertInOutLogGuest(inOutLogGuest));
    }

    /**
     * 修改来宾卡进出记录
     */
    @PreAuthorize("@ss.hasPermi('base:guest:edit')")
    @Log(title = "来宾卡进出记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InOutLogGuest inOutLogGuest) {
        return toAjax(inOutLogGuestService.updateInOutLogGuest(inOutLogGuest));
    }

    /**
     * 删除来宾卡进出记录
     */
    @PreAuthorize("@ss.hasPermi('base:guest:remove')")
    @Log(title = "来宾卡进出记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(inOutLogGuestService.deleteInOutLogGuestByIds(ids));
    }


    /**
     * 来宾卡进出记录作废
     */
    @GetMapping(value = "removeLog")
    public AjaxResult removeLog(@RequestParam Long id) {
        return toAjax(inOutLogGuestService.removeLog(id));
    }

}


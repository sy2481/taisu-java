package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.HcHrWorkOrderCar;
import com.ruoyi.base.domain.HcWorkOrderAuth;
import com.ruoyi.base.service.IHcHrWorkOrderCarService;
import com.ruoyi.base.service.IHcWorkOrderAuthService;
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
 * 危化工單組合認證Controller
 *
 * @author ruoyi
 * @date 2023-02-07
 */
@RestController
@RequestMapping("/system/auth")
public class HcWorkOrderAuthController extends BaseController
{
    @Autowired
    private IHcWorkOrderAuthService hcWorkOrderAuthService;

    /**
     * 查询危化工單組合認證列表
     */
    @PreAuthorize("@ss.hasPermi('system:auth:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcWorkOrderAuth hcWorkOrderAuth)
    {
        startPage();
        List<HcWorkOrderAuth> list = hcWorkOrderAuthService.selectHcWorkOrderAuthList(hcWorkOrderAuth);
        return getDataTable(list);
    }

    /**
     * 导出危化工單組合認證列表
     */
    @PreAuthorize("@ss.hasPermi('system:auth:export')")
    @Log(title = "危化工單組合認證", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcWorkOrderAuth hcWorkOrderAuth)
    {
        List<HcWorkOrderAuth> list = hcWorkOrderAuthService.selectHcWorkOrderAuthList(hcWorkOrderAuth);
        ExcelUtil<HcWorkOrderAuth> util = new ExcelUtil<HcWorkOrderAuth>(HcWorkOrderAuth.class);
        util.exportExcel(response, list, "危化工單組合認證数据");
    }

    /**
     * 获取危化工單組合認證详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:auth:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcWorkOrderAuthService.selectHcWorkOrderAuthById(id));
    }

    /**
     * 新增危化工單組合認證
     */
    @PreAuthorize("@ss.hasPermi('system:auth:add')")
    @Log(title = "危化工單組合認證", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcWorkOrderAuth hcWorkOrderAuth)
    {
        return toAjax(hcWorkOrderAuthService.insertHcWorkOrderAuth(hcWorkOrderAuth));
    }

    /**
     * 修改危化工單組合認證
     */
    @PreAuthorize("@ss.hasPermi('system:auth:edit')")
    @Log(title = "危化工單組合認證", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcWorkOrderAuth hcWorkOrderAuth)
    {
        return toAjax(hcWorkOrderAuthService.updateHcWorkOrderAuth(hcWorkOrderAuth));
    }

    /**
     * 删除危化工單組合認證
     */
    @PreAuthorize("@ss.hasPermi('system:auth:remove')")
    @Log(title = "危化工單組合認證", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcWorkOrderAuthService.deleteHcWorkOrderAuthByIds(ids));
    }
}
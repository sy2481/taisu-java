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
import com.ruoyi.base.domain.HcHrWorkOrderUser;
import com.ruoyi.base.service.IHcHrWorkOrderUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 歷史危化施工單明細Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcHrWorkOrderUser")
public class HcHrWorkOrderUserController extends BaseController
{
    @Autowired
    private IHcHrWorkOrderUserService hcHrWorkOrderUserService;

    /**
     * 查询歷史危化施工單明細列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcHrWorkOrderUser hcHrWorkOrderUser)
    {
        startPage();
        List<HcHrWorkOrderUser> list = hcHrWorkOrderUserService.selectHcHrWorkOrderUserList(hcHrWorkOrderUser);
        return getDataTable(list);
    }

    /**
     * 导出歷史危化施工單明細列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderUser:export')")
    @Log(title = "歷史危化施工單明細", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcHrWorkOrderUser hcHrWorkOrderUser)
    {
        List<HcHrWorkOrderUser> list = hcHrWorkOrderUserService.selectHcHrWorkOrderUserList(hcHrWorkOrderUser);
        ExcelUtil<HcHrWorkOrderUser> util = new ExcelUtil<HcHrWorkOrderUser>(HcHrWorkOrderUser.class);
        util.exportExcel(response, list, "歷史危化施工單明細数据");
    }

    /**
     * 获取歷史危化施工單明細详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcHrWorkOrderUserService.selectHcHrWorkOrderUserById(id));
    }

    /**
     * 新增歷史危化施工單明細
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderUser:add')")
    @Log(title = "歷史危化施工單明細", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcHrWorkOrderUser hcHrWorkOrderUser)
    {
        return toAjax(hcHrWorkOrderUserService.insertHcHrWorkOrderUser(hcHrWorkOrderUser));
    }

    /**
     * 修改歷史危化施工單明細
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderUser:edit')")
    @Log(title = "歷史危化施工單明細", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcHrWorkOrderUser hcHrWorkOrderUser)
    {
        return toAjax(hcHrWorkOrderUserService.updateHcHrWorkOrderUser(hcHrWorkOrderUser));
    }

    /**
     * 删除歷史危化施工單明細
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderUser:remove')")
    @Log(title = "歷史危化施工單明細", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcHrWorkOrderUserService.deleteHcHrWorkOrderUserByIds(ids));
    }
}

package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.base.domain.HcWorkOrderUser;
import com.ruoyi.base.service.IHcWorkOrderUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化施工單明細Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcWorkOrderUser")
public class HcWorkOrderUserController extends BaseController
{
    @Autowired
    private IHcWorkOrderUserService hcWorkOrderUserService;

    /**
     * 查询危化施工單明細列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcWorkOrderUser hcWorkOrderUser)
    {
        startPage();
        List<HcWorkOrderUser> list = hcWorkOrderUserService.selectHcWorkOrderUserList(hcWorkOrderUser);
        return getDataTable(list);
    }

    /**
     * 导出危化施工單明細列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderUser:export')")
    @Log(title = "危化施工單明細", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcWorkOrderUser hcWorkOrderUser)
    {
        List<HcWorkOrderUser> list = hcWorkOrderUserService.selectHcWorkOrderUserList(hcWorkOrderUser);
        ExcelUtil<HcWorkOrderUser> util = new ExcelUtil<HcWorkOrderUser>(HcWorkOrderUser.class);
        util.exportExcel(response, list, "危化施工單明細数据");
    }

    /**
     * 获取危化施工單明細详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcWorkOrderUserService.selectHcWorkOrderUserById(id));
    }

    /**
     * 新增危化施工單明細
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderUser:add')")
    @Log(title = "危化施工單明細", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcWorkOrderUser hcWorkOrderUser)
    {
        return toAjax(hcWorkOrderUserService.insertHcWorkOrderUser(hcWorkOrderUser));
    }

    /**
     * 修改危化施工單明細
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderUser:edit')")
    @Log(title = "危化施工單明細", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcWorkOrderUser hcWorkOrderUser)
    {
        return toAjax(hcWorkOrderUserService.updateHcWorkOrderUser(hcWorkOrderUser));
    }

    /**
     * 删除危化施工單明細
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderUser:remove')")
    @Log(title = "危化施工單明細", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcWorkOrderUserService.deleteHcWorkOrderUserByIds(ids));
    }

    /**
     * 中心人臉全量同步
     */
    @ApiOperation("中心人臉危化人員同步")
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderEntry:syncCent')")
    @Log(title = "中心人臉危化人員同步")
    @GetMapping("/syncCent")
    public AjaxResult syncCent() {
        return AjaxResult.success(hcWorkOrderUserService.syncCent());
    }

    /**
     * 選中人臉更新
     */
    @ApiOperation("選中人臉更新")
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderEntry:syncCentByIds')")
    @Log(title = "選中人臉更新")
    @GetMapping("/syncCentByIds/{ids}")
    public AjaxResult syncCentByIds(@PathVariable Long[] ids) {
        return AjaxResult.success(hcWorkOrderUserService.syncCentByIds(ids));
    }
}

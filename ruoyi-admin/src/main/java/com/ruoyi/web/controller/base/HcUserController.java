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
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.service.IHcUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化人員Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcUser")
public class HcUserController extends BaseController
{
    @Autowired
    private IHcUserService hcUserService;

    /**
     * 查询危化人員列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcUser hcUser)
    {
        startPage();
        List<HcUser> list = hcUserService.selectHcUserList(hcUser);
        return getDataTable(list);
    }

    /**
     * 导出危化人員列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcUser:export')")
    @Log(title = "危化人員", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcUser hcUser)
    {
        List<HcUser> list = hcUserService.selectHcUserList(hcUser);
        ExcelUtil<HcUser> util = new ExcelUtil<HcUser>(HcUser.class);
        util.exportExcel(response, list, "危化人員数据");
    }

    /**
     * 获取危化人員详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcUserService.selectHcUserById(id));
    }

    /**
     * 新增危化人員
     */
    @PreAuthorize("@ss.hasPermi('base:HcUser:add')")
    @Log(title = "危化人員", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcUser hcUser)
    {
        return toAjax(hcUserService.insertHcUser(hcUser));
    }

    /**
     * 修改危化人員
     */
    @PreAuthorize("@ss.hasPermi('base:HcUser:edit')")
    @Log(title = "危化人員", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcUser hcUser)
    {
        return toAjax(hcUserService.updateHcUser(hcUser));
    }

    /**
     * 删除危化人員
     */
    @PreAuthorize("@ss.hasPermi('base:HcUser:remove')")
    @Log(title = "危化人員", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcUserService.deleteHcUserByIds(ids));
    }

    /**
     * 获取危化人員详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcUser:getInfoByIdNo')")
    @GetMapping(value = "/getInfoByIdNo/{idNo}")
    public AjaxResult getInfoByIdNo(@PathVariable("idNo") String idNo)
    {
        return AjaxResult.success(hcUserService.selectHcUserByIdNo(idNo));
    }
}

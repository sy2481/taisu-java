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
import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.service.IHcCarService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化車輛Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcCar")
public class HcCarController extends BaseController
{
    @Autowired
    private IHcCarService hcCarService;

    /**
     * 查询危化車輛列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcCar:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcCar hcCar)
    {
        startPage();
        List<HcCar> list = hcCarService.selectHcCarList(hcCar);
        return getDataTable(list);
    }

    /**
     * 导出危化車輛列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcCar:export')")
    @Log(title = "危化車輛", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcCar hcCar)
    {
        List<HcCar> list = hcCarService.selectHcCarList(hcCar);
        ExcelUtil<HcCar> util = new ExcelUtil<HcCar>(HcCar.class);
        util.exportExcel(response, list, "危化車輛数据");
    }

    /**
     * 获取危化車輛详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcCar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcCarService.selectHcCarById(id));
    }

    /**
     * 新增危化車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcCar:add')")
    @Log(title = "危化車輛", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcCar hcCar)
    {
        return toAjax(hcCarService.insertHcCar(hcCar));
    }

    /**
     * 修改危化車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcCar:edit')")
    @Log(title = "危化車輛", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcCar hcCar)
    {
        return toAjax(hcCarService.updateHcCar(hcCar));
    }

    /**
     * 删除危化車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcCar:remove')")
    @Log(title = "危化車輛", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcCarService.deleteHcCarByIds(ids));
    }

    /**
     * 获取危化車輛详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcCar:getInfoByIdNo')")
    @GetMapping(value = "/getInfoByIdNo/{idNo}")
    public AjaxResult getInfoByIdNo(@PathVariable("idNo") String idNo)
    {
        return AjaxResult.success(hcCarService.selectHcCarByIdNo(idNo));
    }
}

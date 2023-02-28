package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.base.bo.HcWorkOrderCarForPermitBo;
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
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.service.IHcWorkOrderCarService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化施工單車輛Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcWorkOrderCar")
public class HcWorkOrderCarController extends BaseController
{
    @Autowired
    private IHcWorkOrderCarService hcWorkOrderCarService;

    /**
     * 查询危化施工單車輛列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderCar:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcWorkOrderCar hcWorkOrderCar)
    {
        startPage();
        List<HcWorkOrderCar> list = hcWorkOrderCarService.selectHcWorkOrderCarList(hcWorkOrderCar);
        return getDataTable(list);
    }

    /**
     * 导出危化施工單車輛列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderCar:export')")
    @Log(title = "危化施工單車輛", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcWorkOrderCar hcWorkOrderCar)
    {
        List<HcWorkOrderCar> list = hcWorkOrderCarService.selectHcWorkOrderCarList(hcWorkOrderCar);
        ExcelUtil<HcWorkOrderCar> util = new ExcelUtil<HcWorkOrderCar>(HcWorkOrderCar.class);
        util.exportExcel(response, list, "危化施工單車輛数据");
    }

    /**
     * 获取危化施工單車輛详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderCar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcWorkOrderCarService.selectHcWorkOrderCarById(id));
    }

    /**
     * 新增危化施工單車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderCar:add')")
    @Log(title = "危化施工單車輛", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcWorkOrderCar hcWorkOrderCar)
    {
        return toAjax(hcWorkOrderCarService.insertHcWorkOrderCar(hcWorkOrderCar));
    }

    /**
     * 修改危化施工單車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderCar:edit')")
    @Log(title = "危化施工單車輛", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcWorkOrderCar hcWorkOrderCar)
    {
        return toAjax(hcWorkOrderCarService.updateHcWorkOrderCar(hcWorkOrderCar));
    }

    /**
     * 删除危化施工單車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderCar:remove')")
    @Log(title = "危化施工單車輛", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcWorkOrderCarService.deleteHcWorkOrderCarByIds(ids));
    }

    /**
     * 按車輛下發權限
     */
    @ApiOperation("按車輛下發權限")
    @PreAuthorize("@ss.hasPermi('egs:HcWorkOrder:permit')")
    @Log(title = "按車輛下發權限")
    @PostMapping("/permit")
    public AjaxResult permit(@RequestBody HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo) {
        hcWorkOrderCarService.permit(hcWorkOrderCarForPermitBo);
        return AjaxResult.success();
    }

    /**
     * 查询危化施工單車輛列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrderCar:listByVhNoIdNo')")
    @GetMapping("/listByVhNoIdNo/{vhNo}/{idNo}")
    public TableDataInfo listByVhNoIdNo(@PathVariable("vhNo") String vhNo,@PathVariable("idNo") String idNo)
    {
        List<HcWorkOrderCar> list = hcWorkOrderCarService.selectHcWorkOrderCarListByVhNoIdNo(vhNo,idNo);
        return getDataTable(list);
    }

}

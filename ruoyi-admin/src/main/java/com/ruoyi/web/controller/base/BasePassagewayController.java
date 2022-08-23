package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.base.domain.BindPassagewayEquipment;
import io.swagger.annotations.Api;
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
import com.ruoyi.base.domain.BasePassageway;
import com.ruoyi.base.service.IBasePassagewayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 通道Controller
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
@Api(tags = "通道")
@RestController
@RequestMapping("/base/passageway")
public class BasePassagewayController extends BaseController
{
    @Autowired
    private IBasePassagewayService basePassagewayService;

    /**
     * 查詢通道列表
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:list')")
    @GetMapping("/list")
    public TableDataInfo list(BasePassageway basePassageway)
    {
        startPage();
        List<BasePassageway> list = basePassagewayService.selectBasePassagewayList(basePassageway);
        return getDataTable(list);
    }

    /**
     * 查詢通道列表
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:list')")
    @GetMapping("/detailList")
    public AjaxResult detailList(BasePassageway basePassageway)
    {
        return AjaxResult.success(basePassagewayService.selectBasePassagewayDetailList(basePassageway));
    }

    /**
     * 導出通道列表
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:export')")
    @Log(title = "通道", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasePassageway basePassageway)
    {
        List<BasePassageway> list = basePassagewayService.selectBasePassagewayList(basePassageway);
        ExcelUtil<BasePassageway> util = new ExcelUtil<BasePassageway>(BasePassageway.class);
        util.exportExcel(response, list, "通道數據");
    }

    /**
     * 獲取通道詳細數據
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(basePassagewayService.selectBasePassagewayById(id));
    }

    /**
     * 新增通道
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:add')")
    @Log(title = "通道", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasePassageway basePassageway)
    {
        basePassageway.setCreator(getUsername());
        int success = basePassagewayService.insertBasePassageway(basePassageway);
        if (success>0){
            return toAjax(success);
        }else {
            return AjaxResult.error("請選擇正確的門");
        }
    }

    /**
     * 修改通道
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:edit')")
    @Log(title = "通道", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasePassageway basePassageway)
    {
        return toAjax(basePassagewayService.updateBasePassageway(basePassageway));
    }

    /**
     * 刪除通道
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:remove')")
    @Log(title = "通道", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(basePassagewayService.deleteBasePassagewayByIds(ids));
    }

    /**
     * 通道綁定設備
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:bindHikEquipment')")
    @PostMapping("/bindHikEquipmentById")
    public AjaxResult bindHikEquipmentById(@RequestBody BindPassagewayEquipment bindPassagewayEquipment)
    {
        int success = basePassagewayService.bindHikEquipmentById(bindPassagewayEquipment);
        if (success>0){
            return toAjax(success);
        }else {
            return  AjaxResult.error("該通道的方向與需要綁定的設備類型不同，或需要綁定的設備已被綁定在其他通道上");
        }
    }

    /**
     * 查詢通道綁定的海康設備
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:selectPassageEquipment')")
    @GetMapping("/selectPassageEquipment")
    public AjaxResult selectPassageEquipment(Long id)
    {
        return AjaxResult.success(basePassagewayService.selectPassageEquipment(id));
    }

    /**
     * 查詢模式
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:selectModeEnum')")
    @GetMapping("/selectModeEnum")
    public AjaxResult selectModeEnum()
    {
        return AjaxResult.success(basePassagewayService.selectModeEnum());
    }

    /**
     * 查詢方向
     */
    @PreAuthorize("@ss.hasPermi('base:passageway:selectDirectionEnum')")
    @GetMapping("/selectDirectionEnum")
    public AjaxResult selectDirectionEnum()
    {
        return AjaxResult.success(basePassagewayService.selectDirectionEnum());
    }
}

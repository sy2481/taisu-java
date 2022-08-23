package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
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
import com.ruoyi.base.domain.BaseDoor;
import com.ruoyi.base.service.IBaseDoorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 門Controller
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
@Api(tags = "門")
@RestController
@RequestMapping("/base/door")
public class BaseDoorController extends BaseController
{
    @Autowired
    private IBaseDoorService baseDoorService;

    /**
     * 查詢門列表
     */
    @PreAuthorize("@ss.hasPermi('base:door:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaseDoor baseDoor)
    {
        startPage();
        List<BaseDoor> list = baseDoorService.selectBaseDoorList(baseDoor);
        return getDataTable(list);
    }

    /**
     * 導出門列表
     */
    @PreAuthorize("@ss.hasPermi('base:door:export')")
    @Log(title = "門", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaseDoor baseDoor)
    {
        List<BaseDoor> list = baseDoorService.selectBaseDoorList(baseDoor);
        ExcelUtil<BaseDoor> util = new ExcelUtil<BaseDoor>(BaseDoor.class);
        util.exportExcel(response, list, "門數據");
    }

    /**
     * 獲取門詳細信息
     */
    @PreAuthorize("@ss.hasPermi('base:door:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(baseDoorService.selectBaseDoorById(id));
    }

    /**
     * 新增門
     */
    @PreAuthorize("@ss.hasPermi('base:door:add')")
    @Log(title = "門", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseDoor baseDoor)
    {
        baseDoor.setCreator(getUsername());
        return toAjax(baseDoorService.insertBaseDoor(baseDoor));
    }

    /**
     * 修改門
     */
    @PreAuthorize("@ss.hasPermi('base:door:edit')")
    @Log(title = "門", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseDoor baseDoor)
    {
        return toAjax(baseDoorService.updateBaseDoor(baseDoor));
    }

    /**
     * 刪除門
     */
    @PreAuthorize("@ss.hasPermi('base:door:remove')")
    @Log(title = "門", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baseDoorService.deleteBaseDoorByIds(ids));
    }

    /**
     * 查詢類型
     */
    @PreAuthorize("@ss.hasPermi('base:door:selectTypeEnum')")
    @GetMapping("/selectTypeEnum")
    public AjaxResult selectTypeEnum()
    {
        return AjaxResult.success(baseDoorService.selectTypeEnum());
    }
}

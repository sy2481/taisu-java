package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.base.domain.EqStateRecord;
import com.ruoyi.base.service.IEqStateRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 設備在線記錄Controller
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
@Api(tags = "設備在線記錄")
@RestController
@RequestMapping("/base/EqStateRecord")
public class EqStateRecordController extends BaseController
{
    @Autowired
    private IEqStateRecordService eqStateRecordService;

    /**
     * 查询設備在線記錄列表
     */
    @PreAuthorize("@ss.hasPermi('base:EqStateRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(EqStateRecord eqStateRecord)
    {
        startPage();
        List<EqStateRecord> list = eqStateRecordService.selectEqStateRecordList(eqStateRecord);
        return getDataTable(list);
    }

    /**
     * 导出設備在線記錄列表
     */
    @PreAuthorize("@ss.hasPermi('base:EqStateRecord:export')")
    @Log(title = "設備在線記錄", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EqStateRecord eqStateRecord)
    {
        List<EqStateRecord> list = eqStateRecordService.selectEqStateRecordList(eqStateRecord);
        ExcelUtil<EqStateRecord> util = new ExcelUtil<EqStateRecord>(EqStateRecord.class);
        util.exportExcel(response, list, "設備在線記錄数据");
    }

    /**
     * 获取設備在線記錄详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:EqStateRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(eqStateRecordService.selectEqStateRecordById(id));
    }

    /**
     * 新增設備在線記錄
     */
    @PreAuthorize("@ss.hasPermi('base:EqStateRecord:add')")
    @Log(title = "設備在線記錄", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqStateRecord eqStateRecord)
    {
        return toAjax(eqStateRecordService.insertEqStateRecord(eqStateRecord));
    }

    /**
     * 修改設備在線記錄
     */
    @PreAuthorize("@ss.hasPermi('base:EqStateRecord:edit')")
    @Log(title = "設備在線記錄", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqStateRecord eqStateRecord)
    {
        return toAjax(eqStateRecordService.updateEqStateRecord(eqStateRecord));
    }

    /**
     * 删除設備在線記錄
     */
    @PreAuthorize("@ss.hasPermi('base:EqStateRecord:remove')")
    @Log(title = "設備在線記錄", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(eqStateRecordService.deleteEqStateRecordByIds(ids));
    }
}

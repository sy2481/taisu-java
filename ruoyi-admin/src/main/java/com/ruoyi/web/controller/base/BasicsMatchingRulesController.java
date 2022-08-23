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
import com.ruoyi.base.domain.BasicsMatchingRules;
import com.ruoyi.base.service.IBasicsMatchingRulesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车牌近似规则配置Controller
 * 
 * @author ruoyi
 * @date 2022-07-15
 */
@Api(tags = "BasicsMatchingRules")
@RestController
@RequestMapping("/matchingrules")
public class BasicsMatchingRulesController extends BaseController
{
    @Autowired
    private IBasicsMatchingRulesService basicsMatchingRulesService;

    /**
     * 查询车牌近似规则配置列表
     */
    @PreAuthorize("@ss.hasPermi('basics:matchingrules:list')")
    @GetMapping("/list")
    public TableDataInfo list(BasicsMatchingRules basicsMatchingRules)
    {
        startPage();
        List<BasicsMatchingRules> list = basicsMatchingRulesService.selectBasicsMatchingRulesList(basicsMatchingRules);
        return getDataTable(list);
    }

    /**
     * 导出车牌近似规则配置列表
     */
    @PreAuthorize("@ss.hasPermi('basics:matchingrules:export')")
    @Log(title = "车牌近似规则配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasicsMatchingRules basicsMatchingRules)
    {
        List<BasicsMatchingRules> list = basicsMatchingRulesService.selectBasicsMatchingRulesList(basicsMatchingRules);
        ExcelUtil<BasicsMatchingRules> util = new ExcelUtil<BasicsMatchingRules>(BasicsMatchingRules.class);
        util.exportExcel(response, list, "车牌近似规则配置数据");
    }

    /**
     * 获取车牌近似规则配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('basics:matchingrules:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable("configId") Long configId)
    {
        return AjaxResult.success(basicsMatchingRulesService.selectBasicsMatchingRulesByConfigId(configId));
    }

    /**
     * 新增车牌近似规则配置
     */
    @PreAuthorize("@ss.hasPermi('basics:matchingrules:add')")
    @Log(title = "车牌近似规则配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasicsMatchingRules basicsMatchingRules)
    {
        return toAjax(basicsMatchingRulesService.insertBasicsMatchingRules(basicsMatchingRules));
    }

    /**
     * 修改车牌近似规则配置
     */
    @PreAuthorize("@ss.hasPermi('basics:matchingrules:edit')")
    @Log(title = "车牌近似规则配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasicsMatchingRules basicsMatchingRules)
    {
        return toAjax(basicsMatchingRulesService.updateBasicsMatchingRules(basicsMatchingRules));
    }

    /**
     * 删除车牌近似规则配置
     */
    @PreAuthorize("@ss.hasPermi('basics:matchingrules:remove')")
    @Log(title = "车牌近似规则配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(basicsMatchingRulesService.deleteBasicsMatchingRulesByConfigIds(configIds));
    }

    /**
     * 返回类似车牌
     */
    @GetMapping("ReturnLicensePlate/{licenseplate}")
    public AjaxResult ReturnLicensePlate(@PathVariable String licenseplate)
    {
        return AjaxResult.success(basicsMatchingRulesService.ReturnLicensePlate(licenseplate));


    }
}

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
import com.ruoyi.base.domain.ZqSubscribe;
import com.ruoyi.base.service.IZqSubscribeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报预紧信息Controller
 *
 * @author ruoyi
 * @date 2022-05-07
 */
@RestController
@RequestMapping("/base/subscribe")
public class ZqSubscribeController extends BaseController
{
    @Autowired
    private IZqSubscribeService zqSubscribeService;

    /**
     * 查询报预紧信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ZqSubscribe zqSubscribe)
    {
        startPage();
        List<ZqSubscribe> list = zqSubscribeService.selectZqSubscribeList(zqSubscribe);
        return getDataTable(list);
    }

    /**
     * 导出报预紧信息列表
     */
    @Log(title = "报预紧信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZqSubscribe zqSubscribe)
    {
        List<ZqSubscribe> list = zqSubscribeService.selectZqSubscribeList(zqSubscribe);
        ExcelUtil<ZqSubscribe> util = new ExcelUtil<ZqSubscribe>(ZqSubscribe.class);
        util.exportExcel(response, list, "报预紧信息数据");
    }

    /**
     * 获取报预紧信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(zqSubscribeService.selectZqSubscribeById(id));
    }

    /**
     * 新增报预紧信息
     */
    @Log(title = "报预紧信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZqSubscribe zqSubscribe)
    {
        return toAjax(zqSubscribeService.insertZqSubscribe(zqSubscribe));
    }

    /**
     * 修改报预紧信息
     */
    @Log(title = "报预紧信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZqSubscribe zqSubscribe)
    {
        return toAjax(zqSubscribeService.updateZqSubscribe(zqSubscribe));
    }

    /**
     * 删除报预紧信息
     */
    @Log(title = "报预紧信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(zqSubscribeService.deleteZqSubscribeByIds(ids));
    }
}

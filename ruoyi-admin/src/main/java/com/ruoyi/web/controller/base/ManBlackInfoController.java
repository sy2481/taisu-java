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
import com.ruoyi.base.domain.ManBlackInfo;
import com.ruoyi.base.service.IManBlackInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 廠商黑名單記錄Controller
 *
 * @author ruoyi
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/Black/blackInfo")
public class ManBlackInfoController extends BaseController
{
    @Autowired
    private IManBlackInfoService manBlackInfoService;

    /**
     * 查询廠商黑名單記錄列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ManBlackInfo manBlackInfo)
    {
        startPage();
        List<ManBlackInfo> list = manBlackInfoService.selectManBlackInfoList(manBlackInfo);
        return getDataTable(list);
    }

    /**
     * 导出廠商黑名單記錄列表
     */
    @PreAuthorize("@ss.hasPermi('Black:blackInfo:export')")
    @Log(title = "廠商黑名單記錄", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManBlackInfo manBlackInfo)
    {
        List<ManBlackInfo> list = manBlackInfoService.selectManBlackInfoList(manBlackInfo);
        ExcelUtil<ManBlackInfo> util = new ExcelUtil<ManBlackInfo>(ManBlackInfo.class);
        util.exportExcel(response, list, "廠商黑名單記錄数据");
    }

    /**
     * 获取廠商黑名單記錄详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(manBlackInfoService.selectManBlackInfoById(id));
    }

    /**
     * 新增廠商黑名單記錄
     */

    @Log(title = "廠商黑名單記錄", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ManBlackInfo manBlackInfo)
    {
        //判断是否在黑名单
        ManBlackInfo blackInfo = manBlackInfoService.getBlackInfoByCard(manBlackInfo.getIdCard());
        if (blackInfo!=null){
            return  AjaxResult.error(manBlackInfo.getFactoryName()+"已存在，请核对后再试");
        }
        return toAjax(manBlackInfoService.insertManBlackInfo(manBlackInfo));
    }

    /**
     * 修改廠商黑名單記錄
     */

    @Log(title = "廠商黑名單記錄", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ManBlackInfo manBlackInfo)
    {
        ManBlackInfo oldBlack = manBlackInfoService.selectManBlackInfoById(manBlackInfo.getId());
        if (!(oldBlack.getFactoryName().equals(manBlackInfo.getFactoryName()))){
            //判断是否在黑名单
            ManBlackInfo blackInfo = manBlackInfoService.getBlackInfo(manBlackInfo.getFactoryName());
            if (blackInfo!=null){
                return  AjaxResult.error(manBlackInfo.getFactoryName()+"已存在，请核对后再试");
            }
        }
        return toAjax(manBlackInfoService.updateManBlackInfo(manBlackInfo));
    }

    /**
     * 删除廠商黑名單記錄
     */

    @Log(title = "廠商黑名單記錄", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(manBlackInfoService.deleteManBlackInfoByIds(ids));
    }
}

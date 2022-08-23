package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.GuestCard;
import com.ruoyi.base.mapper.GuestCardMapper;
import com.ruoyi.base.service.IGuestCardService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 来宾卡Controller
 *
 * @author ruoyi
 * @date 2022-03-26
 */
@RestController
@RequestMapping("/base/guestCard")
public class GuestCardController extends BaseController {
    @Autowired
    private IGuestCardService guestCardService;
    @Autowired
    private GuestCardMapper guestCardMapper;

    /**
     * 查询来宾卡列表
     */
    @PreAuthorize("@ss.hasPermi('base:guestCard:list')")
    @GetMapping("/list")
    public TableDataInfo list(GuestCard guestCard) {
        startPage();
        List<GuestCard> list = guestCardService.selectGuestCardList(guestCard);
        return getDataTable(list);
    }

    /**
     * 导出来宾卡列表
     */
    @PreAuthorize("@ss.hasPermi('base:guestCard:export')")
    @Log(title = "来宾卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GuestCard guestCard) {
        List<GuestCard> list = guestCardService.selectGuestCardList(guestCard);
        ExcelUtil<GuestCard> util = new ExcelUtil<GuestCard>(GuestCard.class);
        util.exportExcel(response, list, "来宾卡数据");
    }

    /**
     * 获取来宾卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:guestCard:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(guestCardService.selectGuestCardById(id));
    }

    /**
     * 新增来宾卡
     */
    @PreAuthorize("@ss.hasPermi('base:guestCard:add')")
    @Log(title = "来宾卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GuestCard guestCard) {
        List<GuestCard> list = guestCardMapper.listByCardNo(guestCard.getNo(),0);
        if (list != null && list.size() > 0) {
            return AjaxResult.error("来宾卡编号已存在！");
        }
        return toAjax(guestCardService.insertGuestCard(guestCard));
    }

    /**
     * 修改来宾卡
     */
    @PreAuthorize("@ss.hasPermi('base:guestCard:edit')")
    @Log(title = "来宾卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GuestCard guestCard) {
        return toAjax(guestCardService.updateGuestCard(guestCard));
    }

    /**
     * 删除来宾卡
     */
    @PreAuthorize("@ss.hasPermi('base:guestCard:remove')")
    @Log(title = "来宾卡", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(guestCardService.deleteGuestCardByIds(ids));
    }


    /**
     * 归还
     */
    @GetMapping(value = "/cardReturn")
    public AjaxResult cardReturn(@RequestParam Long id){
        return  toAjax(  guestCardService.cardReturn(id));
    }

}

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
import com.ruoyi.base.domain.CardRecord;
import com.ruoyi.base.service.ICardRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 卡片記錄Controller
 * 
 * @author ZZF
 * @date 2022-03-07
 */
@RestController
@RequestMapping("/base/cardRecord")
public class CardRecordController extends BaseController
{
    @Autowired
    private ICardRecordService cardRecordService;

    /**
     * 查询卡片記錄列表
     */
//    @PreAuthorize("@ss.hasPermi('base:cardRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(CardRecord cardRecord)
    {
        startPage();
        List<CardRecord> list = cardRecordService.selectCardRecordList(cardRecord);
        return getDataTable(list);
    }

}

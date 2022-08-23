package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.LocateCard;
import com.ruoyi.base.interact.LocationCardSendService;
import com.ruoyi.base.service.ILocateCardService;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.web.api.basic.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 定位卡Controller
 *
 * @author ZZF
 * @date 2022-03-06
 */
@RestController
@RequestMapping("/base/locateCard")
public class LocateCardController extends BaseController {
    @Autowired
    private ILocateCardService locateCardService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ApiService apiService;
    @Autowired
    private ThreadPoolConfig pool;
    @Autowired
    private LocationCardSendService locationCardSendService;

    /**
     * 2022.04.02 定位卡逻辑修改
     * 绑定定位卡后，如果这个人已经下发成功了，那就直接绑定人和卡
     * 如果人员信息没有下发过，那判断下齐不齐。齐了就发一下人
     *
     * @param idCardNo 身份证号
     * @param locationCardNo 定位卡編號
     */
    @GetMapping("/putLocationCard")
    public Response putLocationCard(@RequestParam String idCardNo, @RequestParam String locationCardNo) {
        try {
            apiService.bind(idCardNo, locationCardNo);
            List<SysUser> byCommonParams = userMapper.getByCommonParams(idCardNo, null, null, null);
            if (byCommonParams.size() > 0) {
                pool.threadPoolTaskExecutor().execute(() -> apiService.userBindHlk(byCommonParams.get(0)));
            }
            return Response.success("绑定成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("異常：" + e);
        }

    }

    //解绑
    @GetMapping("/locationCardClose")
    public Response locationCardClose(@RequestParam String locationCardNo) {
        try {
            apiService.unbind(locationCardNo);
            pool.threadPoolTaskExecutor().execute(() ->  locationCardSendService.downSendUnbindLocationCard(locationCardNo));
            return Response.success("解绑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("異常：" + e);
        }
    }

    /**
     * 獲取定位卡
     *
     * @param empNo
     * @return
     */
    @GetMapping("/getCarCardBindList/{empNo}")
    public Response getLocationCardList(@PathVariable String empNo) {
        try {
            HashMap<String, Object> resultObj = new HashMap<>();
            //根据员工编号查询
            SysUser byUserNo = userMapper.getByUserNo(empNo);
            if (!StringUtils.isEmpty(byUserNo.getPositionCardNo())) {
                //已绑定车卡列表
                resultObj.put("bind", byUserNo.getPositionCardNo());
            } else {
                resultObj.put("bind", null);
            }
            //未绑定的定位卡
            LocateCard locateCard = new LocateCard();
            locateCard.setCardLocateStatus("0");
            List<LocateCard> locateCards = locateCardService.selectLocateCardList(locateCard);
            resultObj.put("noBind", locateCards);
            return Response.success("获取成功", resultObj);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("異常信息：" + e);
        }
    }





    /**
     * 查询定位卡列表
     */
    @PreAuthorize("@ss.hasPermi('base:locateCard:list')")
    @GetMapping("/list")
    public TableDataInfo list(LocateCard locateCard) {
        startPage();
        List<LocateCard> list = locateCardService.selectLocateCardList(locateCard);
        return getDataTable(list);
    }

    /**
     * 导出定位卡列表
     */
    @PreAuthorize("@ss.hasPermi('base:locateCard:export')")
    @Log(title = "定位卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LocateCard locateCard) {
        List<LocateCard> list = locateCardService.selectLocateCardList(locateCard);
        ExcelUtil<LocateCard> util = new ExcelUtil<LocateCard>(LocateCard.class);
        util.exportExcel(response, list, "定位卡数据");
    }

    /**
     * 获取定位卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:locateCard:query')")
    @GetMapping(value = "/{cardLocateId}")
    public AjaxResult getInfo(@PathVariable("cardLocateId") Long cardLocateId) {
        return AjaxResult.success(locateCardService.selectLocateCardByCardLocateId(cardLocateId));
    }

    /**
     * 新增定位卡
     */
    @PreAuthorize("@ss.hasPermi('base:locateCard:add')")
    @Log(title = "定位卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LocateCard locateCard) {
        int card = locateCardService.insertLocateCard(locateCard);
        if (card > 0) {
            return toAjax(card);
        } else {
            return AjaxResult.error("定位卡編號" + locateCard.getCardLocateNo() + "已存在，請核對后再試");
        }
    }

    /**
     * 修改定位卡
     */
    @PreAuthorize("@ss.hasPermi('base:locateCard:edit')")
    @Log(title = "定位卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LocateCard locateCard) {
        return toAjax(locateCardService.updateLocateCard(locateCard));
    }

    /**
     * 删除定位卡
     */
    @PreAuthorize("@ss.hasPermi('base:locateCard:remove')")
    @Log(title = "定位卡", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cardLocateIds}")
    public AjaxResult remove(@PathVariable Long[] cardLocateIds) {
        for (int i = 0; i < cardLocateIds.length; i++) {
            LocateCard locateCard = locateCardService.selectLocateCardByCardLocateId(cardLocateIds[i]);
            if ("1".equals(locateCard.getCardLocateStatus())){
                return error("定位卡已经被绑定，不允许删除");
            }
        }
        return toAjax(locateCardService.deleteLocateCardByCardLocateIds(cardLocateIds));
    }
}

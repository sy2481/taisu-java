package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.LocateCard;
import com.ruoyi.base.domain.ManBlackInfo;
import com.ruoyi.base.domain.ManFactory;
import com.ruoyi.base.mapper.ManFactoryMapper;
import com.ruoyi.base.service.ILocateCardService;
import com.ruoyi.base.service.IManBlackInfoService;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.utils.IDcard;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 厂商Controller
 *
 * @author ruoyi
 * @date 2022-03-06
 */
@RestController
@RequestMapping("/base/factory")
public class ManFactoryController extends BaseController {
    @Autowired
    private IManFactoryService manFactoryService;
    @Autowired
    private ApiService apiService;

    @Autowired
    private ManFactoryMapper factoryMapper;

    @Autowired
    private IManBlackInfoService manBlackInfoService;

    @Autowired
    private ILocateCardService locateCardService;
    /**
     * 查询厂商列表
     */
//    @PreAuthorize("@ss.hasPermi('base:factory:list')")
    @GetMapping("/list")
    public TableDataInfo list(ManFactory manFactory) {

        startPage();
        List<ManFactory> list = manFactoryService.selectManFactoryList(manFactory);
        return getDataTable(list);
    }

    /**
     * 导出厂商列表
     */
//    @PreAuthorize("@ss.hasPermi('base:factory:export')")
    @Log(title = "厂商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManFactory manFactory) {
        List<ManFactory> list = manFactoryService.selectManFactoryList(manFactory);
        ExcelUtil<ManFactory> util = new ExcelUtil<ManFactory>(ManFactory.class);
        util.exportExcel(response, list, "厂商数据");
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<ManFactory> util = new ExcelUtil<ManFactory>(ManFactory.class);
        util.importTemplateExcel(response, "廠商数据");
    }

    /**
     * 获取厂商详细信息
     */
//    @PreAuthorize("@ss.hasPermi('base:factory:query')")
    @GetMapping(value = "/{factoryId}")
    public AjaxResult getInfo(@PathVariable("factoryId") Long factoryId) {
        return AjaxResult.success(manFactoryService.selectManFactoryByFactoryId(factoryId));
    }

    /**
     * 新增厂商
     */
//    @PreAuthorize("@ss.hasPermi('base:factory:add')")
    @Log(title = "厂商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ManFactory manFactory) {
        //判断是否在黑名单(身份証)
        ManBlackInfo blackInfo = manBlackInfoService.getBlackInfoByCard(manFactory.getIdCard());
        if (blackInfo != null) {
            return AjaxResult.error(manFactory.getFactoryName() + "(" + manFactory.getIdCard() + ")已被拉黑，请核对后再添加！");
        }
        // 工单插入，记得保存中间表
        manFactory.setSex((long) ("男".equals(IDcard.getSex(manFactory.getIdCard())) ? 1 : 2));
        manFactory.setBirthDay(IDcard.getBirthday(manFactory.getIdCard()));
        manFactory.setSended(0);
        int factory = manFactoryService.insertManFactory(manFactory);
        if (factory > 0) {
            return toAjax(factory);
        } else {
            return AjaxResult.error(manFactory.getIdCard() + "身份证号已存在");
        }


    }

    /**
     * 修改厂商
     */
    @PreAuthorize("@ss.hasPermi('base:factory:edit')")
    @Log(title = "厂商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ManFactory manFactory) {
        //判断是否在黑名单
        ManBlackInfo blackInfo = manBlackInfoService.getBlackInfoByCard(manFactory.getIdCard());
        if (blackInfo != null) {
            return AjaxResult.error(manFactory.getFactoryName() + "(" + manFactory.getIdCard() + ")已被拉黑，请核对后再添加！");
        }

        // 工单如果发生变化，记得中间表
        int factory = manFactoryService.updateManFactory(manFactory);
        if (factory > 0) {
            return toAjax(factory);
        } else {
            return AjaxResult.error(manFactory.getIdCard() + "身份证号已存在");
        }
        //return toAjax(manFactoryService.updateManFactory(manFactory));
    }

    /**
     * 删除厂商
     */
    @PreAuthorize("@ss.hasPermi('base:factory:remove')")
    @Log(title = "厂商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{factoryIds}")
    public AjaxResult remove(@PathVariable Long[] factoryIds) {
        return toAjax(manFactoryService.deleteManFactoryByFactoryIds(factoryIds));
    }

    @PreAuthorize("@ss.hasPermi('base:factory:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ManFactory> util = new ExcelUtil<ManFactory>(ManFactory.class);
        List<ManFactory> factoryList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = manFactoryService.importManFactory(factoryList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 批量下发厂商人员的人脸信息
     */
    @DeleteMapping(value = "sendFactoryMsgList/{ids}")
    public AjaxResult sendFactoryMsgList(@PathVariable Long[] ids) {
        return AjaxResult.success(apiService.sendFactoryMsgList(ids));
    }

    @GetMapping("/deleteFace")
    public AjaxResult deleteFace(Long factoryId) {
        try {
            manFactoryService.deleteFaceByFactoryId(factoryId);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("操作失敗，請稍後再試。");
        }
    }



}

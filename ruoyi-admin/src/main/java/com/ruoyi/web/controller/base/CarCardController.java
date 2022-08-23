package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.CarCard;
import com.ruoyi.base.service.ICarCardService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.api.basic.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 車卡Controller
 * 
 * @author ZZF
 * @date 2022-03-06
 */
@RestController
@RequestMapping("/base/carCard")
public class CarCardController extends BaseController
{
    @Autowired
    private ICarCardService carCardService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysUserMapper userMapper;


    /**
     * 查询車卡列表
     */
//    @PreAuthorize("@ss.hasPermi('base:carCard:list')")
    @GetMapping("/list")
    public TableDataInfo list(CarCard carCard)
    {
        startPage();
        List<CarCard> list = carCardService.selectCarCardList(carCard);
        return getDataTable(list);
    }

    /**
     * 导出車卡列表
     */
//    @PreAuthorize("@ss.hasPermi('base:carCard:export')")
    @Log(title = "車卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CarCard carCard)
    {
        List<CarCard> list = carCardService.selectCarCardList(carCard);
        ExcelUtil<CarCard> util = new ExcelUtil<CarCard>(CarCard.class);
        util.exportExcel(response, list, "車卡数据");
    }

    /**
     * 获取車卡详细信息
     */
//    @PreAuthorize("@ss.hasPermi('base:carCard:query')")
    @GetMapping(value = "/{cardCarId}")
    public AjaxResult getInfo(@PathVariable("cardCarId") Long cardCarId)
    {
        return AjaxResult.success(carCardService.selectCarCardByCardCarId(cardCarId));
    }

    /**
     * 新增車卡
     */
//    @PreAuthorize("@ss.hasPermi('base:carCard:add')")
    @Log(title = "車卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CarCard carCard)
    {
        carCard.setCreateBy(getUsername());
        carCard.setCardCarStatus("0");
        int card = carCardService.insertCarCard(carCard);
        if (card>0){
            return toAjax(card);
        }else {
            return  AjaxResult.error("車卡編號"+carCard.getCardCarNo()+"已存在，請核對后再試");
        }

    }

    /**
     * 修改車卡
     */
//    @PreAuthorize("@ss.hasPermi('base:carCard:edit')")
    @Log(title = "車卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CarCard carCard)
    {
        carCard.setUpdateBy(getUsername());
        int card = carCardService.updateCarCard(carCard);
        if (card>0){
            return toAjax(card);
        }else {
            return  AjaxResult.error("車卡編號"+carCard.getCardCarNo()+"已存在，請核對后再試");
        }

    }

    /**
     * 删除車卡
     */
//    @PreAuthorize("@ss.hasPermi('base:carCard:remove')")
    @Log(title = "車卡", businessType = BusinessType.DELETE)
	@DeleteMapping("/{cardCarIds}")
    public AjaxResult remove(@PathVariable Long[] cardCarIds)
    {
        for (int i = 0; i < cardCarIds.length; i++) {
            CarCard carCard = carCardService.selectCarCardByCardCarId(cardCarIds[i]);
            if ("1".equals(carCard.getCardCarStatus())){
                return error("车卡已经被绑定，不允许删除");
            }
        }
        return toAjax(carCardService.deleteCarCardByCardCarIds(cardCarIds));
    }

    /**
     * 領用/歸還車卡
     */
    @Log(title = "車卡", businessType = BusinessType.OTHER)
    @PostMapping("/doCard")
    public AjaxResult doCard(@RequestBody CarCard doCard)
    {
        try {
            CarCard carCard = carCardService.selectCarCardByCardCarId(doCard.getCardCarId());
            if ("0".equals(doCard.getType())) {
                //領用
                if ("1".equals(carCard.getCardCarStatus())) {
                    return error("該車卡已綁定！");
                }
                carCard.setCardCarStatus("1");
                carCard.setBindPlateNo(doCard.getBindPlateNo());
                carCard.setLeadName(doCard.getLeadName());
                carCard.setLeadTime(doCard.getLeadTime());
                carCard.setReturnName(null);
                carCard.setReturnTime(null);
            }else if ("1".equals(doCard.getType())) {
                //歸還
                if ("0".equals(carCard.getCardCarStatus())) {
                    return error("該車卡已歸還！");
                }
                carCard.setCardCarStatus("0");
                carCard.setBindPlateNo(null);
                carCard.setLeadName(null);
                carCard.setLeadTime(null);
                carCard.setReturnName(doCard.getReturnName());
                carCard.setReturnTime(doCard.getReturnTime());
            }
            carCard.setUpdateBy(getUsername());
            carCardService.doCard(carCard, doCard.getType());
            return success();
        }catch (Exception e) {
            logger.error("領用/歸還車卡失败：{}", e.getMessage());
            return error("操作失敗！");
        }
    }

    /**
     * 获取未绑定的车卡和已绑定的车卡
     * @return
     */
    @PostMapping("/getCarCardBindList")
    public Response getCarCardBindList(@RequestBody String empNo) {
        SysUser byUserNo = userMapper.getByUserNo(empNo);
        String[] carCardArray;
        if (!StringUtils.isEmpty(byUserNo.getCarCard())){
            carCardArray= byUserNo.getCarCard().split(",");
        }else{
            carCardArray = null;
        }
        HashMap<String,Object> resultObj = new HashMap<>();
        //已绑定车卡列表
        resultObj.put("bind",carCardArray);
        CarCard carCard = new CarCard();
        carCard.setCardCarStatus("0");
        //未绑定车卡列表
        List<CarCard> list = carCardService.selectCarCardList(carCard);
        resultObj.put("noBind",list);
        return Response.success("获取成功",resultObj);
    }
}

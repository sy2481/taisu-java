package com.ruoyi.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.mapper.ManFactoryMapper;
import com.ruoyi.base.mapper.ManWorkFactoryMapper;
import com.ruoyi.base.mapper.ManWorkMapper;
import com.ruoyi.base.service.ICarCardService;
import com.ruoyi.base.service.IHikEquipmentService;
import com.ruoyi.base.service.IManWorkService;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.base.service.impl.PlcEquipmentServiceImpl;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.ThreadPoolConfig;
import com.ruoyi.web.api.basic.Response;
import com.ruoyi.web.controller.system.SysUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工单Controller
 *
 * @author ruoyi
 * @date 2022-03-06
 */
@RestController
@RequestMapping("/base/work")
public class ManWorkController extends BaseController {
    @Autowired
    private IManWorkService manWorkService;
    @Autowired
    private ManWorkFactoryMapper manWorkFactoryMapper;
    @Autowired
    private ManFactoryMapper manFactoryMapper;
    @Autowired
    private ManWorkMapper workMapper;
    @Autowired
    private ICarCardService carCardService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private ThreadPoolConfig pool;
    @Autowired
    private ManWorkMapper manWorkMapper;
    @Autowired
    private PlateSendService plateSendService;
    @Autowired
    private PlcEquipmentServiceImpl plcEquipmentService;
    @Autowired
    private IHikEquipmentService hikEquipmentService;

    /**
     * 查询工单列表
     */
//    @PreAuthorize("@ss.hasPermi('base:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(ManWork manWork) {
        // 用以查詢未過期工單
        manWork.setHistoryQuery(false);
        startPage();
        List<ManWork> list = manWorkService.selectManWorkList(manWork);
        return getDataTable(list);
    }

    /**
     * 历史工单数据
     */
    @GetMapping("/historyList")
    public TableDataInfo historyList(ManWork manWork) {
        // 用以查詢歷史訂單
        manWork.setHistoryQuery(true);
        startPage();
        List<ManWork> list = manWorkService.selectManWorkList(manWork);
        return getDataTable(list);
    }

    /**
     * 导出工单列表
     */
//    @PreAuthorize("@ss.hasPermi('base:work:export')")
    @Log(title = "工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManWork manWork) {
        List<ManWork> list = manWorkService.selectManWorkList(manWork);
        ExcelUtil<ManWork> util = new ExcelUtil<ManWork>(ManWork.class);
        util.exportExcel(response, list, "工单数据");
    }

    /**
     * 导出历史工单列表
     */
//    @PreAuthorize("@ss.hasPermi('base:work:export')")
    @Log(title = "导出历史工单", businessType = BusinessType.EXPORT)
    @PostMapping("/exportHistory")
    public void exportHistory(HttpServletResponse response, ManWork manWork) {
        manWork.setHistoryQuery(true);
        List<ManWork> list = manWorkService.selectManWorkList(manWork);
        ExcelUtil<ManWork> util = new ExcelUtil<ManWork>(ManWork.class);
        util.exportExcel(response, list, "历史工单数据");
    }

    /**
     * 获取工单详细信息
     */
//    @PreAuthorize("@ss.hasPermi('base:work:query')")
    @GetMapping(value = "/{workId}")
    public TableDataInfo getInfo(@PathVariable("workId") Long workId) {
        startPage();
        List<ManWorkFactory> manWorkFactories = manWorkFactoryMapper.selectManWorkByWorkId(workId);
        List<ManFactory> manFactoryList = new ArrayList<>();
        for (ManWorkFactory manWorkFactory : manWorkFactories) {
            Long factoryId = manWorkFactory.getFactoryId();
            ManFactory manFactory = manFactoryMapper.selectManFactoryByFactoryId(factoryId);
            manFactoryList.add(manFactory);
        }
        return getDataTable(manFactoryList);
    }

    /**
     * 获取厂商详细信息
     */
//    @PreAuthorize("@ss.hasPermi('base:factory:query')")
    @GetMapping("/getInfo/{workId}")
    public AjaxResult getInfoByworkId(@PathVariable("workId") Long workId) {
        return AjaxResult.success(manWorkService.selectManWorkByWorkId(workId));
    }


    /**
     * 新增工单
     */
//    @PreAuthorize("@ss.hasPermi('base:work:add')")
    @Log(title = "工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ManWork manWork) {
        ManWork existWork = manWorkMapper.selectManWorkByworkNo(manWork.getWorkNo());
        if (existWork != null) {
            return AjaxResult.error(manWork.getWorkNo() + "，工单号已存在");
        }
        if (manWork.getIp() != null) {
            PlcEquipment equipment = plcEquipmentService.findByIp(manWork.getIp());
            if (equipment != null && equipment.getPlantAreaId() != null) {
                manWork.setDeptId(equipment.getPlantAreaId());
            }
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String nickName = SecurityUtils.getLoginUser().getUser().getNickName();
        manWork.setMngName(nickName);
        manWork.setMngTime(sdf1.format(new Date()));
        manWork.setWorkStatus(0);
        //危化品根据厂区去设置下属危化品通道的IP
        if (manWork.getWorkType() == 1) {
            manWork.setIp(manWorkService.getIp(manWork.getDeptId()));
        }
        return toAjax(manWorkService.insertManWork(manWork));
    }

    /**
     * 修改工单
     */
//    @PreAuthorize("@ss.hasPermi('base:work:edit')")
    @Log(title = "工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ManWork manWork) {
        if (manWork.getIp() != null) {
            PlcEquipment equipment = plcEquipmentService.findByIp(manWork.getIp());
            if (equipment != null && equipment.getPlantAreaId() != null) {
                manWork.setDeptId(equipment.getPlantAreaId());
            }
        }
        return toAjax(manWorkService.updateManWork(manWork));
    }

    /**
     * 删除工单
     */
//    @PreAuthorize("@ss.hasPermi('base:work:remove')")
    @Log(title = "工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{workIds}")
    public AjaxResult remove(@PathVariable Long[] workIds) {
        return toAjax(manWorkService.deleteManWorkByWorkIds(workIds));
    }

    /**
     * 查询全部当前可用的工单
     * 0-厂商工单，1-危化品工单
     */
    @GetMapping("/listCurrentWork")
    public AjaxResult listCurrentWork(Integer dangerType) {
        ManWork manWork = new ManWork();
        manWork.setHistoryQuery(false);
        manWork.setWorkType(dangerType);
        return AjaxResult.success(manWorkService.selectManWorkList(manWork));
    }

    /**
     * 获取未绑定的车卡和已绑定的车卡
     */
    @PostMapping("/getCarCardBindList")
    public Response getCarCardBindList(@RequestBody String workNo) {
        ManWork workInfo = workMapper.selectManWorkByworkNo(workNo);
        String[] carCardArray;
        if (!StringUtils.isEmpty(workInfo.getCarCard())) {
            carCardArray = workInfo.getCarCard().split(",");
        } else {
            carCardArray = null;
        }
        HashMap<String, Object> resultObj = new HashMap<>();
        //已绑定车卡列表
        resultObj.put("bind", carCardArray);
        CarCard carCard = new CarCard();
        carCard.setCardCarStatus("0");
        //未绑定车卡列表
        List<CarCard> list = carCardService.selectCarCardList(carCard);
        resultObj.put("noBind", list);
        return Response.success("获取成功", resultObj);
    }

    /**
     * 删除车卡
     */
    @PostMapping("/delCarCard")
    public Response delCarCard(@RequestBody String requestObj) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestObj);
            manWorkService.delCard(jsonObject);
            return Response.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("删除失败");
        }
    }

    /**
     * 添加车卡
     */
    @PostMapping("/addCarCard")
    public Response addCarCard(@RequestBody String requestObj) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestObj);
            manWorkService.addCarCard(jsonObject);
            return Response.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("添加失败");
        }
    }

    /**
     * 获取已绑定的车辆列表
     */
    @PostMapping("/getCarIdBindList")
    public Response getCarNoBindList(@RequestBody String workNo) {
        ManWork workInfo = workMapper.selectManWorkByworkNo(workNo);
        String[] carNodArray;
        if (!StringUtils.isEmpty(workInfo.getCarId())) {
            carNodArray = workInfo.getCarId().split(",");
        } else {
            carNodArray = null;
        }
        HashMap<String, Object> resultObj = new HashMap<>();
        //已绑定车卡列表
        resultObj.put("bind", carNodArray);
        return Response.success("获取成功", resultObj);
    }

    /**
     * 绑定车辆
     */
    @PostMapping("/addCarId")
    public Response addCarId(@RequestBody String requestObj) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestObj);
            //获取工单要绑定的车辆
            String carId = (String) jsonObject.get("carId");
            //要添加车卡的工单信息
            ManWork workInfo = workMapper.selectManWorkByworkNo((String) jsonObject.get("workNo"));
            String[] split;
            List<String> list;
            List<String> list1;
            if (!StringUtils.isEmpty(workInfo.getCarId())) {
                split = workInfo.getCarId().split(",");
                list = Arrays.asList(split);
                list1 = new ArrayList<>(list);
                list1.add(carId);
            } else {
                //添加车卡
                list1 = new ArrayList<>();
                list1.add(carId);
            }
            StringBuilder carIdlongStr = new StringBuilder();
            //删除后重新拼接员工车卡
            for (int i = 0; i < list1.size(); i++) {
                String addCarId = list1.get(i);
                if (i == 0) {
                    carIdlongStr = new StringBuilder(addCarId);
                } else {
                    carIdlongStr.append(",").append(addCarId);
                }
            }
            /***************更新车车牌绑定信息**********************/
            //更新工单绑定车牌信息
            ManWork manWork = new ManWork();
            manWork.setCarId(carIdlongStr.toString());
            manWork.setWorkId(workInfo.getWorkId());
            workMapper.updateManWork(manWork);
            //更新工单下的厂商人员车辆信息
            ManFactory manFactory = new ManFactory();
            manFactory.setWorkNo((String) jsonObject.get("workNo"));
            manFactory.setLcensePlate(carIdlongStr.toString());
            manFactoryMapper.updateManFactoryByWorkNo(manFactory);
            //最后下发车牌权限
            pool.threadPoolTaskExecutor().execute(() -> plateSendService.workCarDownSend(manWorkService.selectManWorkByWorkId(manWork.getWorkId())));
            return Response.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("添加失败");
        }
    }

    /**
     * 删除绑定车辆
     */
    @PostMapping("/delCarId")
    public Response delCarId(@RequestBody String requestObj) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestObj);
            //获取要删除的车辆
            String carId = (String) jsonObject.get("carId");
            //删除车辆的工单信息
            ManWork workInfo = workMapper.selectManWorkByworkNo((String) jsonObject.get("workNo"));
            String[] split = workInfo.getCarId().split(",");
            List<String> list = Arrays.asList(split);
            int carCardIndex = SysUserController.getCarCardIndex(list, carId);
            List<String> list1 = new ArrayList<>(list);
            //删除工单中的车辆
            list1.remove(carCardIndex);
            StringBuilder carIdlongStr = new StringBuilder();
            //删除后重新拼接工单下的车辆
            for (int i = 0; i < list1.size(); i++) {
                String cardIdAdd = list1.get(i);
                if (i == 0) {
                    carIdlongStr = new StringBuilder(cardIdAdd);
                } else {
                    carIdlongStr.append(",").append(cardIdAdd);
                }
            }
            //更新工单绑定车卡信息
            ManWork manWork = new ManWork();
            manWork.setCarId(carIdlongStr.toString());
            manWork.setWorkId(workInfo.getWorkId());
            workMapper.updateManWork(manWork);
            //更新工单下的厂商人员车辆信息
            ManFactory manFactory = new ManFactory();
            manFactory.setWorkNo((String) jsonObject.get("workNo"));
            manFactory.setLcensePlate(carIdlongStr.toString());
            manFactoryMapper.updateManFactoryByWorkNo(manFactory);
            // 工单解绑车牌，已经提前获得了信息
            pool.threadPoolTaskExecutor().execute(() -> plateSendService.downSendUnbindPlateNo(carId));
            return Response.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("删除失败");
        }
    }
}

package com.ruoyi.web.api;

import com.ruoyi.base.bo.PersonMsgBO;
import com.ruoyi.base.domain.BaseSafetycar;
import com.ruoyi.base.service.SafetycarService;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.web.api.basic.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据查询接口
 *
 * @author shiva   2022/3/7 14:17
 */
@RestController
@RequestMapping("/api/query")
public class ApiQueryController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private SafetycarService safetycarService;
    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 根据⻋牌号、⻋卡查询⼈员信息
     * 根据 ⻋牌号/⻋卡，查询 对应的⼈员信息、⼈员对应的⼯单。我们提供数据结构，全了就⾏
     *
     * @param queryType 0-车牌号查询，1-车卡查询
     */
    @ResponseBody
    @GetMapping("/getByPlateNoOrCard")
    public Response getByPlateNoOrCard(String param, String queryType) {
        try {
            List<PersonMsgBO> result = new ArrayList<>();
            // 先查询员工表，没有的话，再查询厂商
            if ("0".equals(queryType)) {
                //根據車牌號查詢
                result = apiService.queryPersonByPlateNo(param);
            } else if ("1".equals(queryType)) {
                //根據車卡查詢
                result = apiService.queryPersonByCarCard(param);
            } else {
                throw new Exception("查询类型不正确");
            }
            if (result.size() == 0) {
                return Response.error("未查詢到相關人員信息！");
            }
            return Response.builder().code(0).data(result).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }

    }


    /**
     * 根据⾝份证号查询⼈员信息
     * 海康可以拿到⾝份证，根据⾝份证，根据⾝份证号查询⼈员信息
     */
    @ResponseBody
    @GetMapping("/getByIdCardNo")
    public Response getByIdCardNo(String param) {
        try {
            PersonMsgBO result = null;
            // 先查询员工表，没有的话，再查询厂商
            result = apiService.queryPersonByIdcardNo(param);
            if (result == null) {
                return Response.error("未查詢到相關人員信息！");
            }
            return Response.builder().code(0).data(result).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }


    /**
     * 根据定位卡编号查询⼈员信息
     * 根据定位卡编号，返回⼈员信息，和上一个一样
     */
    @ResponseBody
    @GetMapping("/getByLocationCardNo")
    public Response getByLocationCardNo(String param) {
        try {
            PersonMsgBO result = null;
            // 先查询员工表，没有的话，再查询厂商
            result = apiService.queryPersonByLocationCardNo(param);
            if (result == null) {
                return Response.error("未查詢到相關人員信息！");
            }
            return Response.builder().code(0).data(result).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

    /**
     * TODO 判断押运员、司机、车辆是否是同一个人
     */

    /*根據車牌獲取車輛數據*/

    /**
     * 根据定位卡编号查询⼈员信息
     * 根据定位卡编号，返回⼈员信息，和上一个一样
     */
    @ResponseBody
    @GetMapping("/getSafetyCarByIdno")
    public Response getSafetyCarByIdno(String param) {
        try {
            List<BaseSafetycar> result = null;
            result = safetycarService.getSafetycarByCarno(param);

            return Response.builder().code(0).data(result).build();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

    /**
     * 根据定位卡编号查询⼈员信息
     * 根据定位卡编号，返回⼈员信息，和上一个一样
     */
    @ResponseBody
    @GetMapping("/dictType/{dictType}")
    public Response dictType(@PathVariable String dictType) {
        try {
            List<SysDictData> result = dictTypeService.selectDictDataByType(dictType);
            if (StringUtils.isNull(result))
            {
                result = new ArrayList<SysDictData>();
            }

            return Response.builder().code(0).data(result).build();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

}

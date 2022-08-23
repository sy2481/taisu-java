package com.ruoyi.web.api;

import com.ruoyi.base.bo.PersonMsgBO;
import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.web.api.basic.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
            if (result.size() == 0){
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
            if (result == null){
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
            if (result == null){
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

}

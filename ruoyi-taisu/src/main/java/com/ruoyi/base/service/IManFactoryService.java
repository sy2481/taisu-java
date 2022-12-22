package com.ruoyi.base.service;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.FactoryWorkBO;
import com.ruoyi.base.domain.ManFactory;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 厂商Service接口
 *
 * @author ruoyi
 * @date 2022-03-06
 */
public interface IManFactoryService {
    /**
     * 查询厂商
     *
     * @param factoryId 厂商主键
     * @return 厂商
     */
    public ManFactory selectManFactoryByFactoryId(Long factoryId);

    /**
     * 查询厂商列表
     *
     * @param manFactory 厂商
     * @return 厂商集合
     */
    public List<ManFactory> selectManFactoryList(ManFactory manFactory);

    /**
     * 新增厂商
     *
     * @param manFactory 厂商
     * @return 结果
     */
    public int insertManFactory(ManFactory manFactory);

    /**
     * exel导入插入数据
     *
     * @param manFactory 厂商
     * @return 结果
     */
    public int insertManFactoryExel(ManFactory manFactory) throws ParseException;

    /**
     * 修改厂商
     *
     * @param manFactory 厂商
     * @return 结果
     */
    public int updateManFactory(ManFactory manFactory);

    /**
     * 批量删除厂商
     *
     * @param factoryIds 需要删除的厂商主键集合
     * @return 结果
     */
    public int deleteManFactoryByFactoryIds(Long[] factoryIds);

    /**
     * 删除厂商信息
     *
     * @param factoryId 厂商主键
     * @return 结果
     */
    public int deleteManFactoryByFactoryId(Long factoryId);

    /**
     * @param workNo 工單號
     * @param date   日期
     */
    List<FactoryWorkBO> listByWorkNoAndDate(String workNo, String date, Integer workType);

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importManFactory(List<ManFactory> userList, Boolean isUpdateSupport, String operName);

    /**
     * 定时任务修改
     */
    public int updateCar();

    int updateDangerCar();

    void delFactory();

    List<ManFactory> selectCangerousCar(String idCard, String plateNo);


    int deleteFaceByFactoryId( Long factoryId);

    List<FactoryWorkBO> saveForNoFace(List<FactoryWorkBO> list, Map<String, JSONObject> map);

    /**
     * 保存中心库人脸
     * @param manFactory
     */
    void saveFaceForCenter(ManFactory manFactory);

    /**
     * 根据身份证获取
     * @param idCards 身份证号
     */
    Map<String,ManFactory> getListByIdCards(List<String> idCards);

    /**
     * 從中心庫同步
     * @return
     */
    public int syncCent();

    /**
     * 根據ids從中心庫同步
     * @param factoryIds
     * @return
     */
    public int syncCentByFactoryIds(Long[] factoryIds);

    /**
     * 從中心庫同步
     * @param list
     * @return
     */
    public int syncCentByVnd(List<ManFactory> list, boolean forceUpdate);

}

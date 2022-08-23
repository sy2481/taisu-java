package com.ruoyi.base.mapper;


import com.ruoyi.base.bo.FactoryWorkBO;
import com.ruoyi.base.domain.ManFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 厂商Mapper接口
 *
 * @author ruoyi
 * @date 2022-03-06
 */
@Component
public interface ManFactoryMapper {
    /**
     * 查询厂商
     *
     * @param factoryId 厂商主键
     * @return 厂商
     */
    public ManFactory selectManFactoryByFactoryId(Long factoryId);

    /**
     * 查询厂商
     *
     * @param idCard 身份证
     * @return
     */
    public ManFactory selectManFactoryByIdCard(String idCard);

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
     * 修改厂商
     *
     * @param manFactory 厂商
     * @return 结果
     */
    public int updateManFactory(ManFactory manFactory);

    /**
     * 根据工单号修改厂商
     *
     * @param manFactory 厂商
     * @return 结果
     */
    public int updateManFactoryByWorkNo(ManFactory manFactory);

    /**
     * 删除厂商
     *
     * @param factoryId 厂商主键
     * @return 结果
     */
    public int deleteManFactoryByFactoryId(Long factoryId);

    /**
     * 批量删除厂商
     *
     * @param factoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteManFactoryByFactoryIds(Long[] factoryIds);

    List<FactoryWorkBO> listByWorkNoAndDate(@Param("workNo") String workNo, @Param("date") String date, @Param("workType") Integer workType);

    /**
     * 整合查询，传入参数则生效，null则忽略条件
     *
     * @param idCard         身份证号
     * @param locationCardNo 定位卡编号
     * @param carCardNo      车卡编号
     * @param plateNo        车牌号
     */
    List<ManFactory> getByCommonParams(@Param("idCard") String idCard,
                                       @Param("locationCardNo") String locationCardNo,
                                       @Param("carCardNo") String carCardNo,
                                       @Param("plateNo") String plateNo);

    /**
     * 定时任务修改
     *
     * @return
     */
    int updateCar();

    int deleteFaceByFactoryId(@Param("factoryId") Long factoryId);

    void sendBackStatus(@Param("factoryId") Long factoryId, @Param("sendedStatus") Integer sendedStatus);

    void delFactory();


    /**
     * 为null忽略查询条件
     *
     * @param idCard  身份ID
     * @param plateNo 车牌号
     * @return
     */
    List<ManFactory> selectCangerousCar(@Param("idCard") String idCard, @Param("plateNo") String plateNo);

    /**
     * 根据名字查询
     *
     * @param name
     * @return
     */
    List<ManFactory> selectManFactoryByName(@Param("name") String name);

    /**
     * 保存中心库人脸
     *
     * @param idCard
     */
  //  String selectAllManFactory(@Param("idCard") String idCard);

    /**
     * 根据身份证获取
     *
     * @param idCards 身份证号
     */
    List<ManFactory> selectListFaceByIdCards(@Param("idCards")List<String> idCards);

    /**
     * 根据身份证更新头像
     * @param manFactory
     */
    void updateFaceByIdCard(ManFactory manFactory);
}

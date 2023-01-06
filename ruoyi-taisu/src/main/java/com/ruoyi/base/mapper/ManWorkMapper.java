package com.ruoyi.base.mapper;


import com.ruoyi.base.bo.WorkCarBo;
import com.ruoyi.base.domain.ManWork;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 工单Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-06
 */
@Component
public interface ManWorkMapper 
{
    /**
     * 查询工单
     * 
     * @param workId 工单主键
     * @return 工单
     */
    public ManWork selectManWorkByWorkId(Long workId);

    /**
     * 查询工单列表
     * 
     * @param manWork 工单
     * @return 工单集合
     */
    public List<ManWork> selectManWorkList(ManWork manWork);

    /**
     * 新增工单
     * 
     * @param manWork 工单
     * @return 结果
     */
    public int insertManWork(ManWork manWork);

    /**
     * 修改工单
     * 
     * @param manWork 工单
     * @return 结果
     */
    public int updateManWork(ManWork manWork);

    /**
     * 删除工单
     * 
     * @param workId 工单主键
     * @return 结果
     */
    public int deleteManWorkByWorkId(Long workId);

    /**
     * 批量删除工单
     * 
     * @param workIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteManWorkByWorkIds(Long[] workIds);

    /**
     * 根据工单号查询
     */
    public ManWork selectManWorkByworkNo(String workNo);


    /**
     * 根据工单号查询（模糊查询）
     */

    public List<WorkCarBo> selectManWork(@Param("workNo") String workNo, @Param("date") String date);

    public List<WorkCarBo> selectManWorkNew(@Param("workNo") String workNo, @Param("date") String date,@Param("workType")Integer workType);
    /**
     * 工单下厂商负责人出
     * @param workNo 工单号
     */
    public int xtOut(String workNo);
    /**
     * 工单下厂商负责人进
     * @param workNo 工单号
     */
    public int xtIn(String workNo);

    /**
     * 工单下厂商普通人员出
     * @param workNo 工单号
     */
    public int comOut(String workNo);
    /**
     * 工单下厂商普通人员进
     * @param workNo 工单号
     */
    public int comIn(String workNo);

    @Update("UPDATE man_work SET work_time = #{date}, car_id=null, ip=null, xt_in_num=0, com_in_num=0,car_card=null WHERE len(work_no) <= 8 ")
    void resetLongTimeWork(Date date);

    @Update("UPDATE man_work SET car_card=null, car_id=null WHERE len(work_no) > 8 and work_type=0")
    void resetWorkCarcard();

    @Select("SELECT work_id FROM man_work WHERE len(work_no) <= 8")
    List<Long> listLongTimeWork();

    @Delete("DELETE FROM man_work_factory WHERE work_id = #{id}")
    void deleteMidByWorkId(Long id);

    //TODO 临时方法，后面删掉
    int countDangerPlate(@Param("plateNo") String plateNo, @Param("date")Date date);
}

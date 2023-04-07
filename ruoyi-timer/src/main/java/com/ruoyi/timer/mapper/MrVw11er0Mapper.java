package com.ruoyi.timer.mapper;

import com.ruoyi.timer.domain.MrVw11er0;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MrVw11er0Mapper {
    /**
     * 查询工单
     *
     * @param vhno 工单主键
     * @return 工单
     */
    MrVw11er0 selectMrVw11er0ByVhno(String vhno);

    /**
     * 查询工单列表
     *
     * @param mrVw11er0 工单
     * @return 工单集合
     */
    List<MrVw11er0> selectMrVw11er0List(MrVw11er0 mrVw11er0);

    List<MrVw11er0>  selectMrVw11er0LAllist();

    int selectMrVw11er0Count();

    /**
     * 新增工单
     *
     * @param mrVw11er0 工单
     * @return 结果
     */
  //  int insertMrVw11er0(MrVw11er0 mrVw11er0);

    /**
     * 修改工单
     *
     * @param mrVw11er0 工单
     * @return 结果
     */
  //  int updateMrVw11er0(MrVw11er0 mrVw11er0);



    /**
     * 批量删除工单
     *
     * @param vhnos 需要删除的数据主键集合
     * @return 结果
     */
    // int deleteMrVw11er0ByVhnos(String[] vhnos);
}

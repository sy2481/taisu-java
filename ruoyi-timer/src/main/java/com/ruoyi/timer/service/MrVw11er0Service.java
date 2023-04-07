package com.ruoyi.timer.service;

import com.ruoyi.timer.domain.MrVw11er0;

import java.util.List;

public interface MrVw11er0Service {
    /**
     * 新增工单
     *
     * @param mrVw11er0 工单
     * @return 结果
     */
    //public int insertMrVw11er0(MrVw11er0 mrVw11er0);
    public int selectMrVw11er0Count();

    /**
     * 查询工单
     *
     * @param vhno 工单主键
     * @return 工单
     */
    public MrVw11er0 selectMrVw11er0ByVhno(String vhno);

    /**
     * 查询工单列表
     *
     * @param mrVw11er0 工单
     * @return 工单集合
     */
    public List<MrVw11er0> selectMrVw11er0List(MrVw11er0 mrVw11er0);

    public List<MrVw11er0> selectMrVw11er0AllList();


    /**
     * 修改工单
     *
     * @param mrVw11er0 工单
     * @return 结果
     */
    // public int updateMrVw11er0(MrVw11er0 mrVw11er0);




    /**
     * 删除工单信息
     *
     * @param vhno 工单主键
     * @return 结果
     */
    //public int deleteMrVw11er0ByVhno(String vhno);
}

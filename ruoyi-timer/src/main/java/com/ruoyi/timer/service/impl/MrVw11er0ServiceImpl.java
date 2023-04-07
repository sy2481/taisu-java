package com.ruoyi.timer.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.timer.domain.MrVw11er0;
import com.ruoyi.timer.mapper.MrVw11er0Mapper;
import com.ruoyi.timer.service.MrVw11er0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DataSource(value = DataSourceType.IEMDG)
public class MrVw11er0ServiceImpl implements MrVw11er0Service {
    @Autowired
    private MrVw11er0Mapper mrVw11er0Mapper;

    //@Override
//    public int insertMrVw11er0(MrVw11er0 mrVw11er0)
//    {
//        return mrVw11er0Mapper.insertMrVw11er0(mrVw11er0);
//    }

    @Override
    public int selectMrVw11er0Count(){
        return mrVw11er0Mapper.selectMrVw11er0Count();
    }

    @Override
    public MrVw11er0 selectMrVw11er0ByVhno(String vhno)
    {
        return mrVw11er0Mapper.selectMrVw11er0ByVhno(vhno);
    }

    /**
     * 查询工单列表
     *
     * @param mrVw11er0 工单
     * @return 工单
     */
    @Override
    public List<MrVw11er0> selectMrVw11er0List(MrVw11er0 mrVw11er0)
    {
        return mrVw11er0Mapper.selectMrVw11er0List(mrVw11er0);
    }



    @Override
    public List<MrVw11er0> selectMrVw11er0AllList()
    {
        return mrVw11er0Mapper.selectMrVw11er0LAllist();
    }

    /**
     * 修改工单
     *
     * @param mrVw11er0 工单
     * @return 结果
     */
    //@Override
//    public int updateMrVw11er0(MrVw11er0 mrVw11er0)
//    {
//        return mrVw11er0Mapper.updateMrVw11er0(mrVw11er0);
//    }



    /**
     * 删除工单信息
     *
     * @param vhno 工单主键
     * @return 结果
     */
//    @Override
//    public int deleteMrVw11er0ByVhno(String vhno)
//    {
//        return mrVw11er0Mapper.deleteMrVw11er0ByVhno(vhno);
//    }

}

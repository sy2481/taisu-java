package com.ruoyi.base.service.impl;

import com.ruoyi.base.bo.PlcRelationBO;
import com.ruoyi.base.domain.PlcHikCommand;
import com.ruoyi.base.mapper.PlcHikCommandMapper;
import com.ruoyi.base.service.IPlcHikCommandService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 設備所屬指令Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-02
 */
@Service
public class PlcHikCommandServiceImpl implements IPlcHikCommandService {
    @Autowired
    private PlcHikCommandMapper plcHikCommandMapper;

    /**
     * 查询設備所屬指令
     *
     * @param id 設備所屬指令主键
     * @return 設備所屬指令
     */
    @Override
    public PlcHikCommand selectPlcHikCommandById(Long id) {
        return plcHikCommandMapper.selectPlcHikCommandById(id);
    }

    /**
     * 查询設備所屬指令列表
     *
     * @param plcHikCommand 設備所屬指令
     * @return 設備所屬指令
     */
    @Override
    public List<PlcHikCommand> selectPlcHikCommandList(PlcHikCommand plcHikCommand) {
        return plcHikCommandMapper.selectPlcHikCommandList(plcHikCommand);
    }

    /**
     * 新增設備所屬指令
     *
     * @param plcHikCommand 設備所屬指令
     * @return 结果
     */
    @Override
    public int insertPlcHikCommand(PlcHikCommand plcHikCommand) {
        this.canAddOrEdit(plcHikCommand);

        plcHikCommand.setCreateBy(SecurityUtils.getUsername());
        plcHikCommand.setCreateTime(DateUtils.getNowDate());
        return plcHikCommandMapper.insertPlcHikCommand(plcHikCommand);
    }

    /**
     * 修改設備所屬指令
     *
     * @param plcHikCommand 設備所屬指令
     * @return 结果
     */
    @Override
    public int updatePlcHikCommand(PlcHikCommand plcHikCommand) {
        this.canAddOrEdit(plcHikCommand);

        plcHikCommand.setUpdateBy(SecurityUtils.getUsername());
        plcHikCommand.setUpdateTime(DateUtils.getNowDate());
        return plcHikCommandMapper.updatePlcHikCommand(plcHikCommand);
    }

    /**
     * 能否新增修改
     */
    private void canAddOrEdit(PlcHikCommand vo) {
        List<PlcHikCommand> oldVos = plcHikCommandMapper.existPlcHikCommand(vo.getPlcHikId(), vo.getCode());
        if (oldVos.size() > 0) {
            throw new ServiceException("該設備下已存在編號為【" + vo.getCode() + "】的指令,，操作失敗。");
        }
    }

    /**
     * 批量删除設備所屬指令
     *
     * @param ids 需要删除的設備所屬指令主键
     * @return 结果
     */
    @Override
    public int deletePlcHikCommandByIds(Long[] ids) {
        return plcHikCommandMapper.deletePlcHikCommandByIds(ids);
    }

    /**
     * 删除設備所屬指令信息
     *
     * @param id 設備所屬指令主键
     * @return 结果
     */
    @Override
    public int deletePlcHikCommandById(Long id) {
        return plcHikCommandMapper.deletePlcHikCommandById(id);
    }

    /**
     * 按設備編號，獲取半開半關的指令
     *
     * @param plcRelationBos
     * @param plcHikCommands
     * @return
     */
    public List<PlcRelationBO> addPlcHalfCommand(List<PlcRelationBO> plcRelationBos, List<PlcHikCommand> plcHikCommands) {
        if(plcRelationBos==null || plcRelationBos.size()==0
                || plcHikCommands==null || plcHikCommands.size()==0){
            return plcRelationBos;
        }
        for (PlcRelationBO plcBo : plcRelationBos) {
            //篩選出半開或者半關的指令：003：半開；004：半關；
            List<PlcHikCommand> halfCommandList= plcHikCommands.stream().filter(x->plcBo.getPlcId().equals(x.getPlcHikId())
                    && ("003".equals(x.getCode()) || "004".equals(x.getCode())))
                    .collect(Collectors.toList());
            if(halfCommandList.size()>0){
                plcBo.setPlcCommandHalf(halfCommandList.get(0).getCommand());
            }
        }
        return plcRelationBos;
    }
}

package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.ZqSubscribeMapper;
import com.ruoyi.base.domain.ZqSubscribe;
import com.ruoyi.base.service.IZqSubscribeService;

/**
 * 报预紧信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-07
 */
@Service
public class ZqSubscribeServiceImpl implements IZqSubscribeService {
    @Autowired
    private ZqSubscribeMapper zqSubscribeMapper;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询报预紧信息
     *
     * @param id 报预紧信息主键
     * @return 报预紧信息
     */
    @Override
    public ZqSubscribe selectZqSubscribeById(Long id) {
        return zqSubscribeMapper.selectZqSubscribeById(id);
    }

    /**
     * 查询报预紧信息列表
     *
     * @param zqSubscribe 报预紧信息
     * @return 报预紧信息
     */
    @Override
    public List<ZqSubscribe> selectZqSubscribeList(ZqSubscribe zqSubscribe) {

        //查询当前登录人是否有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList!=null){
            zqSubscribe.setDeptIds(longList);
        }
        List<ZqSubscribe> zqSubscribes = zqSubscribeMapper.selectZqSubscribeList(zqSubscribe);
        zqSubscribes.forEach(zqSubscribe1 -> {
            if (zqSubscribe1.getDeptId()!=null){
                SysDept sysDept = sysDeptService.selectDeptById(zqSubscribe1.getDeptId());
                zqSubscribe1.setDeptName(sysDept.getDeptName());
            }
        });
        return zqSubscribes;
    }

    /**
     * 新增报预紧信息
     *
     * @param zqSubscribe 报预紧信息
     * @return 结果
     */
    @Override
    public int insertZqSubscribe(ZqSubscribe zqSubscribe) {
        zqSubscribe.setCreateTime(DateUtils.getNowDate());
        return zqSubscribeMapper.insertZqSubscribe(zqSubscribe);
    }

    /**
     * 修改报预紧信息
     *
     * @param zqSubscribe 报预紧信息
     * @return 结果
     */
    @Override
    public int updateZqSubscribe(ZqSubscribe zqSubscribe) {
        zqSubscribe.setUpdateTime(DateUtils.getNowDate());
        return zqSubscribeMapper.updateZqSubscribe(zqSubscribe);
    }

    /**
     * 批量删除报预紧信息
     *
     * @param ids 需要删除的报预紧信息主键
     * @return 结果
     */
    @Override
    public int deleteZqSubscribeByIds(Long[] ids) {
        return zqSubscribeMapper.deleteZqSubscribeByIds(ids);
    }

    /**
     * 删除报预紧信息信息
     *
     * @param id 报预紧信息主键
     * @return 结果
     */
    @Override
    public int deleteZqSubscribeById(Long id) {
        return zqSubscribeMapper.deleteZqSubscribeById(id);
    }


    @Override
    public int addWarnInfo(ZqSubscribe zqSubscribe, String buildId) {
        //厂区用建筑ID匹配
        List<SysDept> plant = sysDeptService.getPlantByBuildId(buildId);
        if (plant.size()==0){
            return 0;
        }
        zqSubscribe.setDeptId(plant.get(0).getDeptId());
        return zqSubscribeMapper.insertZqSubscribe(zqSubscribe);
    }
}

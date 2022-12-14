package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.BaseCar;
import com.ruoyi.base.domain.ManFactory;
import com.ruoyi.base.mapper.BaseCarMapper;
import com.ruoyi.base.mapper.ManFactoryMapper;
import com.ruoyi.base.service.IBaseCarService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 車Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-08
 */
@Service
public class BaseCarService implements IBaseCarService {
    @Autowired
    private BaseCarMapper baseCarMapper;
    @Autowired
    private ManFactoryMapper manFactoryMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询車
     *
     * @param id 車主键
     * @return 車
     */
    @Override
    public BaseCar selectBaseCarById(Long id) {
        return baseCarMapper.selectBaseCarById(id);
    }

    /**
     * 查询車列表
     *
     * @param baseCar 車
     * @return 車
     */
    @Override
    public List<BaseCar> selectBaseCarList(BaseCar baseCar) {
        return baseCarMapper.selectBaseCarList(baseCar);
    }

    /**
     * 新增車
     *
     * @param baseCar 車
     * @return 结果
     */
    @Override
    public int insertBaseCar(BaseCar baseCar) {
        baseCar.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
        baseCar.setCreateTime(DateUtils.getNowDate());
        return baseCarMapper.insertBaseCar(baseCar);
    }

    /**
     * 修改車
     *
     * @param baseCar 車
     * @return 结果
     */
    @Override
    public int updateBaseCar(BaseCar baseCar) {
        baseCar.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
        baseCar.setUpdateTime(DateUtils.getNowDate());
        return baseCarMapper.updateBaseCar(baseCar);
    }

    /**
     * 批量删除車
     *
     * @param ids 需要删除的車主键
     * @return 结果
     */
    @Override
    public int deleteBaseCarByIds(Long[] ids) {
        return baseCarMapper.deleteBaseCarByIds(ids);
    }

    /**
     * 删除車信息
     *
     * @param id 車主键
     * @return 结果
     */
    @Override
    public int deleteBaseCarById(Long id) {
        return baseCarMapper.deleteBaseCarById(id);
    }

    /**
     * 查询車
     *
     * @param idCard 車牌
     * @return 車
     */
    public BaseCar selectBaseCarByIdCard(String idCard) {
        return baseCarMapper.selectBaseCarByIdCard(idCard);
    }

    /**
     * 保存車輛表
     *
     * @param baseCar
     * @return
     */
    @Override
    @Transactional
    public int saveBaseCarForManFactory(BaseCar baseCar, Long manFactoryId) {
        int reuslt = 0;
        reuslt += this.saveBaseCar(baseCar);

        //同時更新廠商數據
        if (manFactoryId != null) {
            ManFactory manFactory = new ManFactory();
            manFactory.setEmisStandard(baseCar.getEmisStandard());
            manFactory.setEmisStandardName(baseCar.getEmisStandardName());
            manFactory.setEnvSign(baseCar.getEnvSign());
            manFactory.setFactoryId(manFactoryId);
            reuslt += manFactoryMapper.updateManFactory(manFactory);
        }
        return reuslt;
    }

    /**
     * 保存車輛表
     *
     * @param baseCar
     * @return
     */
    @Override
    @Transactional
    public int saveBaseCarForEmployee(BaseCar baseCar, Long sysUserId) {
        int reuslt = 0;
        reuslt += this.saveBaseCar(baseCar);

        if (sysUserId != null) {
            SysUser sysUser = new SysUser();
            sysUser.setEmisStandard(baseCar.getEmisStandard());
            sysUser.setEmisStandardName(baseCar.getEmisStandardName());
            sysUser.setEnvSign(baseCar.getEnvSign());
            sysUser.setUserId(sysUserId);
            reuslt += sysUserMapper.updateUser(sysUser);
        }
        return reuslt;
    }

    /**
     * 保存車輛表
     *
     * @param baseCar
     * @return
     */
    @Override
    @Transactional
    public int saveBaseCar(BaseCar baseCar) {
        int result = 0;
        BaseCar entity = baseCarMapper.selectBaseCarByIdCard(baseCar.getIdCard());
        Long oldId = null;
        boolean isAdd = false;
        if (entity == null) {
            isAdd = true;
            entity = new BaseCar();
        } else {
            isAdd = false;
            oldId = entity.getId();
        }
        BeanUtils.copyProperties(baseCar, entity);
        if (isAdd) {
            result += this.insertBaseCar(entity);
        } else {
            entity.setId(oldId);
            result += this.updateBaseCar(entity);
        }


        return result;
    }
}

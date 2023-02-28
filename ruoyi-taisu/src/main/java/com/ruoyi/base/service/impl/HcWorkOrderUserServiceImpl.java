package com.ruoyi.base.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.enums.HcUserType;
import com.ruoyi.base.mapper.HcUserMapper;
import com.ruoyi.base.mapper.HcWorkOrderCarMapper;
import com.ruoyi.base.service.IHcUserService;
import com.ruoyi.base.service.ISyncCentHcService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.BatisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.slj.SljSyncUtils;
import com.ruoyi.system.bo.CentMemberBo;
import com.ruoyi.system.mapper.SysDeptMapper;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.HcWorkOrderUserMapper;
import com.ruoyi.base.service.IHcWorkOrderUserService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 危化施工單明細Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcWorkOrderUserServiceImpl implements IHcWorkOrderUserService {
    @Autowired
    private HcWorkOrderUserMapper hcWorkOrderUserMapper;
    @Autowired
    private IHcUserService hcUserService;
    @Autowired
    private HcUserMapper hcUserMapper;
    @Autowired
    private ISyncCentHcService syncCentHcService;
    @Autowired
    private HcWorkOrderCarMapper hcWorkOrderCarMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 厂区编号
     */
    @Value("${factoryCode}")
    private String factoryCode;

    /**
     * 查询危化施工單明細
     *
     * @param id 危化施工單明細主键
     * @return 危化施工單明細
     */
    @Override
    public HcWorkOrderUser selectHcWorkOrderUserById(Long id) {
        return hcWorkOrderUserMapper.selectHcWorkOrderUserById(id);
    }

    /**
     * 查询危化施工單明細列表
     *
     * @param hcWorkOrderUser 危化施工單明細
     * @return 危化施工單明細
     */
    @Override
    public List<HcWorkOrderUser> selectHcWorkOrderUserList(HcWorkOrderUser hcWorkOrderUser) {
        return hcWorkOrderUserMapper.selectHcWorkOrderUserList(hcWorkOrderUser);
    }

    /**
     * 新增危化施工單明細
     *
     * @param hcWorkOrderUser 危化施工單明細
     * @return 结果
     */
    @Override
    @Transactional
    public int insertHcWorkOrderUser(HcWorkOrderUser hcWorkOrderUser) {
        int result = 0;

        //危化工單
        this.canAddEdit(hcWorkOrderUser);
        this.redun(hcWorkOrderUser);

        hcWorkOrderUser.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
        hcWorkOrderUser.setCreateTime(DateUtils.getNowDate());
        result += hcWorkOrderUserMapper.insertHcWorkOrderUser(hcWorkOrderUser);

        //同時保存基礎表hcUser
        hcUserService.buildUser(hcWorkOrderUser);
        //同步到中心庫
        HcUser hcUser = hcUserMapper.selectHcUserByIdNo(hcWorkOrderUser.getIdNo());
        syncCentHcService.sendToCent(hcUser);
        //

        return result;
    }

    /**
     * 修改危化施工單明細
     *
     * @param hcWorkOrderUser 危化施工單明細
     * @return 结果
     */
    @Override
    @Transactional
    public int updateHcWorkOrderUser(HcWorkOrderUser hcWorkOrderUser) {
        int result = 0;

        //危化工單
        this.canAddEdit(hcWorkOrderUser);
        this.redun(hcWorkOrderUser);

        hcWorkOrderUser.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
        hcWorkOrderUser.setUpdateTime(DateUtils.getNowDate());
        result += hcWorkOrderUserMapper.updateHcWorkOrderUser(hcWorkOrderUser);

        //同時保存基礎表hcUser
        hcUserService.buildUser(hcWorkOrderUser);
        //同步到中心庫
        HcUser hcUser = hcUserMapper.selectHcUserByIdNo(hcWorkOrderUser.getIdNo());
        HcUser hcUserSend = new HcUser();
        hcUserSend.setNm(hcUser.getNm());
        hcUserSend.setIdNo(hcUser.getIdNo());
        hcUserSend.setFace(hcUser.getFace());
        hcUserSend.setSex(hcUser.getSex());
        hcUserSend.setPhone(hcUser.getPhone());
        hcUserSend.setAddress(hcUser.getAddress());
        hcUserSend.setBirthday(hcUser.getBirthday());
        hcUserSend.setCompany(hcUser.getCompany());
        hcUserSend.setDriverLicense(hcUser.getDriverLicense());
        hcUserSend.setEscortLicense(hcUser.getEscortLicense());

        syncCentHcService.sendToCent(hcUserSend);

        return result;
    }

    /**
     * 能否新增和修改
     */
    private void canAddEdit(HcWorkOrderUser hcWorkOrderUser) {
        //如果已存在2個人，則不允許新增
        int cnt = hcWorkOrderUserMapper.selectPeopleCntByVhNo(
                hcWorkOrderUser.getVhNo(),
                hcWorkOrderUser.getLicense(),
                hcWorkOrderUser.getIpltTm(),
                hcWorkOrderUser.getId());
        if (cnt >= 2) {
            throw new ServiceException("保存危化人員【" + hcWorkOrderUser.getIdNo() + "-" + hcWorkOrderUser.getNm() + "】失敗，該危化工單下已存在2個以上的人員。");
        }
        //同一個工單、同車輛、同入廠時間下身份證不能重複
        List<HcWorkOrderUser> sameIdnoList = hcWorkOrderUserMapper.existSameIdno(hcWorkOrderUser);
        if (sameIdnoList.size() > 0) {
            HcWorkOrderUser vo = sameIdnoList.get(0);
            throw new ServiceException("保存危化人員【" + hcWorkOrderUser.getIdNo() + "-" + hcWorkOrderUser.getNm() + "】失敗，該危化工單下已存在同身份證號的危化人員。");
        }
        if (hcWorkOrderUser.getUserType() == 1) {
            //同一個工單下，只能存在一個司機
            List<HcWorkOrderUser> driverList = hcWorkOrderUserMapper.existDriver(hcWorkOrderUser);
            if (driverList.size() > 0) {
                HcWorkOrderUser vo = driverList.get(0);
                throw new ServiceException("保存危化人員【" + hcWorkOrderUser.getIdNo() + "-" + hcWorkOrderUser.getNm() + "】失敗，該危化工單下已存在司機，如需修改，請先讓其他同行人員先改為押運員。");
            }
        }

    }

    /**
     * 處理冗餘
     *
     * @param entity
     */
    private void redun(HcWorkOrderUser entity) {
        entity.setFacDorNm(factoryCode);
        SysDept plant = sysDeptMapper.selectDeptByDeptNo(factoryCode);
        entity.setFacNm(plant.getDeptName());

        entity.setUserTypeName(HcUserType.getNameByCode(entity.getUserType()));
        if (Objects.equals(HcUserType.DRIVER.getCode(), entity.getUserType())) {
            entity.setEscortLicense(null);
        } else {
            entity.setDriverLicense(null);
        }
        redunHcWorkOrderCar(entity);
    }

    /**
     * 處理危化工單車輛的司機
     *
     * @param entity
     */
    private void redunHcWorkOrderCar(HcWorkOrderUser entity) {
        HcWorkOrderCar hcWorkOrderCar = hcWorkOrderCarMapper.selectHcWorkOrderCarByVhNoIdNo(entity.getVhNo(), entity.getLicense(),entity.getIpltTm());
        ;
        if (hcWorkOrderCar == null) {
            throw new ServiceException("該人員的車輛數據缺失，請聯繫管理員。");
        }

        //如果是司機，回寫到車輛表
        if (Objects.equals(entity.getUserType(), HcUserType.DRIVER.getCode())) {
            //更新司機信息
            HcWorkOrderCar param = new HcWorkOrderCar();
            param.setId(hcWorkOrderCar.getId());
            param.setDirverIdNo(entity.getIdNo());
            param.setDriverNm(entity.getNm());
            param.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
            param.setUpdateTime(DateUtils.getNowDate());
            hcWorkOrderCarMapper.updateHcWorkOrderCarForDriver(param);

        } else {
            //如果變為押運員，且原來是司機，則清空車輛表的司機信息
            HcWorkOrderUser oldEntity = hcWorkOrderUserMapper.selectHcWorkOrderUserByVhNoIdNo(entity.getVhNo(), entity.getIdNo(),entity.getIpltTm());
            if(oldEntity!=null) {
                if (Objects.equals(oldEntity.getUserType(), HcUserType.DRIVER.getCode())) {
                    //更新司機信息
                    HcWorkOrderCar param = new HcWorkOrderCar();
                    param.setId(hcWorkOrderCar.getId());
                    param.setDirverIdNo(null);
                    param.setDriverNm(null);
                    param.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
                    param.setUpdateTime(DateUtils.getNowDate());
                    hcWorkOrderCarMapper.updateHcWorkOrderCarForDriver(param);
                }
            }
        }
    }


    /**
     * 批量删除危化施工單明細
     *
     * @param ids 需要删除的危化施工單明細主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderUserByIds(Long[] ids) {
        return hcWorkOrderUserMapper.deleteHcWorkOrderUserByIds(ids);
    }

    /**
     * 删除危化施工單明細信息
     *
     * @param id 危化施工單明細主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderUserById(Long id) {
        return hcWorkOrderUserMapper.deleteHcWorkOrderUserById(id);
    }

    /**
     * 獲取實體map
     *
     * @return
     */
    @Override
    public Map<String, HcWorkOrderUser> getEntityMap(List<String> vhNoList) {
        Map<String, HcWorkOrderUser> result = new HashMap<>();
        List<HcWorkOrderUser> list = new ArrayList<>();
        if (vhNoList == null) {
            list = hcWorkOrderUserMapper.selectHcWorkOrderUserList(null);
        } else {
            if (vhNoList.size() > 0) {
                list = hcWorkOrderUserMapper.selectHcWorkOrderUserListByVhNos(vhNoList.toArray(new String[0]));
            }
        }

        for (HcWorkOrderUser item : list) {
            result.put(item.getVhNo() + "_" + item.getIdNo(), item);
        }
        return result;
    }

    /**
     * 從中心庫獲取人臉數據
     */
    @Override
    @Transactional
    public int syncCent() {
        int result = 0;
        boolean forceUpdate = false;

        List<HcWorkOrderUser> boList = new ArrayList<>();
        int pageNum = 1;
        int pageSize = 1000;
        String orderBy = "";
        do {
            PageHelper.startPage(pageNum++, pageSize, orderBy).setReasonable(true);
            boList = hcWorkOrderUserMapper.selectHcWorkOrderUserList(new HcWorkOrderUser());

            //中心庫-->HCUser
            List<String> idNos = boList.stream().map(HcWorkOrderUser::getIdNo).distinct().collect(Collectors.toList());
            result += hcUserService.syncCentByHcUser(idNos, forceUpdate);

            //HCUser->HcWorkOrderUser
            result += this.syncFromHcUserByPage(boList);

        } while (boList.size() >= pageSize);

        return result;
    }


    @Override
    @Transactional
    public int syncCentByIds(Long[] ids) {
        int result = 0;
        boolean forceUpdate = true;
        List<HcWorkOrderUser> hcWorkOrderUserList = hcWorkOrderUserMapper.selectHcWorkOrderUserListByIds(ids);

        //中心庫-->HCUser
        List<String> idNos = hcWorkOrderUserList.stream().map(HcWorkOrderUser::getIdNo).distinct().collect(Collectors.toList());
        result += hcUserService.syncCentByHcUser(idNos, forceUpdate);

        //HCUser->HcWorkOrderUser
        result += this.syncFromHcUserByPage(hcWorkOrderUserList);

        return result;
    }

    @Override
    @Transactional
    public int syncFromHcUserByPage(List<HcWorkOrderUser> hcWorkOrderUserList) {
        int result = 0;
        List<HcWorkOrderUser> updateList = new ArrayList<>();

        List<HcWorkOrderUser> oldList = hcWorkOrderUserList;
        HcWorkOrderUser entity = null;

        //獲取基礎表
        List<String> idNos = oldList.stream().map(HcWorkOrderUser::getIdNo).collect(Collectors.toList());
        List<HcUser> hcUserList = hcUserMapper.selectHcUserListByIdNos(idNos.toArray(new String[0]));

        List<HcWorkOrderUser> newList = new ArrayList<>();
        for (HcWorkOrderUser item : oldList) {
            HcUser hcUserVo = null;
            List<HcUser> tempHcUserList = hcUserList.stream()
                    .filter(x -> StringUtils.nvl(x.getIdNo(), "").equals(StringUtils.nvl(item.getIdNo(), "")))
                    .collect(Collectors.toList());
            if (tempHcUserList.size() > 0) {
                hcUserVo = tempHcUserList.get(0);
            }
            if (hcUserVo != null) {
                entity = new HcWorkOrderUser();
                entity.setNm(hcUserVo.getNm());
                entity.setIdNo(hcUserVo.getIdNo());
                entity.setFace(hcUserVo.getFace());
                entity.setSex(hcUserVo.getSex());
                entity.setPhone(hcUserVo.getPhone());
                entity.setAddress(hcUserVo.getAddress());
                entity.setBirthday(hcUserVo.getBirthday());
                entity.setCompany(hcUserVo.getCompany());
                entity.setDriverLicense(hcUserVo.getDriverLicense());
                entity.setEscortLicense(hcUserVo.getEscortLicense());
                entity.setIpltLic(hcUserVo.getTsNo());
                newList.add(entity);
            }
        }
        List<String> keyFieldNames = Arrays.asList("idNo");
        String primaryFieldName = "id";
        List<String> syncFieldNames = Arrays.asList("nm", "idNo", "face", "sex", "phone", "address", "birthday","company", "driverLicense","escortLicense","ipltLic");

        Map<String, List<HcWorkOrderUser>> oprMap = SljSyncUtils.getOprMap(newList, oldList, keyFieldNames, primaryFieldName, syncFieldNames,
                null,
                null,
                null);
        //addList = oprMap.get("addList");
        updateList = oprMap.get("updateList");

        //批量修改
        if (updateList.size() > 0) {
            List<List<HcWorkOrderUser>> lists = BatisUtils.splitList(updateList, 40);
            for (List<HcWorkOrderUser> ls : lists) {
                result += hcWorkOrderUserMapper.batchUpdateHcWorkOrderUser(ls);
            }
        }

        return result;

    }


}

package com.ruoyi.base.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.domain.HcWorkOrderUser;
import com.ruoyi.base.enums.VndIdNoType;
import com.ruoyi.base.service.ISyncCentHcService;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.BatisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.slj.SljSyncUtils;
import com.ruoyi.system.bo.CentMemberBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.HcUserMapper;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.service.IHcUserService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 危化人員Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcUserServiceImpl implements IHcUserService {
    @Autowired
    private HcUserMapper hcUserMapper;
    @Autowired
    private ISyncCentHcService syncCentHcService;

    /**
     * 查询危化人員
     *
     * @param id 危化人員主键
     * @return 危化人員
     */
    @Override
    public HcUser selectHcUserById(Long id) {
        return hcUserMapper.selectHcUserById(id);
    }

    /**
     * 查询危化人員列表
     *
     * @param hcUser 危化人員
     * @return 危化人員
     */
    @Override
    public List<HcUser> selectHcUserList(HcUser hcUser) {
        return hcUserMapper.selectHcUserList(hcUser);
    }

    /**
     * 新增危化人員
     *
     * @param hcUser 危化人員
     * @return 结果
     */
    @Override
    public int insertHcUser(HcUser hcUser) {
        hcUser.setCreateTime(DateUtils.getNowDate());
        return hcUserMapper.insertHcUser(hcUser);
    }

    /**
     * 修改危化人員
     *
     * @param hcUser 危化人員
     * @return 结果
     */
    @Override
    public int updateHcUser(HcUser hcUser) {
        hcUser.setUpdateTime(DateUtils.getNowDate());
        return hcUserMapper.updateHcUser(hcUser);
    }

    /**
     * 批量删除危化人員
     *
     * @param ids 需要删除的危化人員主键
     * @return 结果
     */
    @Override
    public int deleteHcUserByIds(Long[] ids) {
        return hcUserMapper.deleteHcUserByIds(ids);
    }

    /**
     * 删除危化人員信息
     *
     * @param id 危化人員主键
     * @return 结果
     */
    @Override
    public int deleteHcUserById(Long id) {
        return hcUserMapper.deleteHcUserById(id);
    }

    /**
     * 构造参数
     *
     * @return 结果
     */
    @Override
    public void buildUser(HcWorkOrderUser hcWorkOrderUser) {
        HcUser hcUser = hcUserMapper.selectHcUserByIdNo(hcWorkOrderUser.getIdNo());
        if (hcUser != null) {
            hcUser.setIdNo(hcWorkOrderUser.getIdNo());
            hcUser.setNm(hcWorkOrderUser.getNm());
            hcUser.setFace(hcWorkOrderUser.getFace());
            hcUser.setSex(hcWorkOrderUser.getSex());
            hcUser.setPhone(hcWorkOrderUser.getPhone());
            hcUser.setAddress(hcWorkOrderUser.getAddress());
            hcUser.setBirthday(hcWorkOrderUser.getBirthday());
            hcUser.setCompany(hcWorkOrderUser.getCompany());
            hcUser.setFacDorNm(hcWorkOrderUser.getFacDorNm());
            hcUser.setFacNm(hcWorkOrderUser.getFacNm());
            hcUser.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
            hcUser.setCreateTime(DateUtils.getNowDate());
            hcUser.setDriverLicense(hcWorkOrderUser.getDriverLicense());
            hcUser.setEscortLicense(hcWorkOrderUser.getEscortLicense());
            hcUser.setTsNo(hcWorkOrderUser.getIpltLic());
            hcUserMapper.updateHcUser(hcUser);
        } else {
            hcUser = new HcUser();
            hcUser.setIdNo(hcWorkOrderUser.getIdNo());
            hcUser.setNm(hcWorkOrderUser.getNm());
            hcUser.setFace(hcWorkOrderUser.getFace());
            hcUser.setSex(hcWorkOrderUser.getSex());
            hcUser.setPhone(hcWorkOrderUser.getPhone());
            hcUser.setAddress(hcWorkOrderUser.getAddress());
            hcUser.setBirthday(hcWorkOrderUser.getBirthday());
            hcUser.setCompany(hcWorkOrderUser.getCompany());
            hcUser.setFacDorNm(hcWorkOrderUser.getFacDorNm());
            hcUser.setFacNm(hcWorkOrderUser.getFacNm());
            hcUser.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
            hcUser.setUpdateTime(DateUtils.getNowDate());
            hcUser.setDriverLicense(hcWorkOrderUser.getDriverLicense());
            hcUser.setEscortLicense(hcWorkOrderUser.getEscortLicense());
            hcUser.setTsNo(hcWorkOrderUser.getIpltLic());
            hcUserMapper.insertHcUser(hcUser);
        }
    }

    /**
     * 判斷合約卡、臨時卡的idNo的類型 1 人 2 有牌車  3 無牌車
     *
     * @param idNo
     * @return
     */
    @Override
    public VndIdNoType checkIdNoType(String idNo) {
        //長度大於等於10為人卡（考慮到台胞證）
        //長度小於10，首字符是中文的是有牌車，否則就是無牌車
        if (StringUtils.isEmpty(idNo)) {
            return VndIdNoType.CAR_NOLIC;//算無牌車
        }

        if (idNo.length() >= 10) {
            return VndIdNoType.MAN;
        } else {
            if (StringUtils.checkChineseAll(idNo.substring(0, 1))) {
                return VndIdNoType.CAR_LIC;
            } else {
                return VndIdNoType.CAR_NOLIC;
            }
        }
    }

    /**
     * 從中心庫獲取危化人員人臉數據
     */
    @Override
    public int syncCentByHcUser(List<String> idNoList, boolean forceUpdate) {
        int result = 0;
        List<HcUser> addList = new ArrayList<HcUser>();
        List<HcUser> updateList = new ArrayList<HcUser>();
        HcUser entity = null;

        List<HcUser> hcUsers =new ArrayList<>();
        if(idNoList.size()>0){
            hcUsers = hcUserMapper.selectHcUserListByIdNos(idNoList.toArray(new String[0]));//原來的數據
        }
        //中心庫數據
        Map<String, CentMemberBo> memberBoMap = syncCentHcService.getListFromCent(StringUtils.join(idNoList, ","));

        for (String idNo : idNoList) {
            Optional<HcUser> optHcUser = hcUsers.stream().filter(x -> Objects.equals(x.getIdNo(), idNo)).findFirst();
            HcUser item = new HcUser();
            boolean isAdd = true;
            if (!optHcUser.equals(Optional.empty())) {
                item = optHcUser.get();
                isAdd = false;
            } else {
                item.setIdNo(idNo);
                isAdd = true;
            }

            CentMemberBo memberBo = memberBoMap.get(item.getIdNo());
            if (memberBo == null) {
                continue;
            }

            //是否更新其他數據
            boolean updateInfo = this.isUpdateInfoFromCent(item, memberBo);
            if (updateInfo) {

                entity = new HcUser();
                entity.setIdNo(item.getIdNo());
                if (updateInfo) {
                    entity = this.getHcUserByCentMemberBo(entity, memberBo);
                }
                if (isAdd) {
                    entity.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
                    entity.setCreateTime(DateUtils.getNowDate());
                    addList.add(entity);
                } else {
                    entity.setId(item.getId());
                    entity.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
                    entity.setUpdateTime(DateUtils.getNowDate());
                    updateList.add(entity);
                }
            }
        }

        //批量新增
        if (addList.size() > 0) {
            List<List<HcUser>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcUser> ls : lists) {
                result += hcUserMapper.batchInsertHcUser(ls);
            }
        }
        //批量修改
        if (updateList.size() > 0) {
            List<List<HcUser>> lists = BatisUtils.splitList(updateList, 40);
            for (List<HcUser> ls : lists) {
                result += hcUserMapper.batchUpdateHcUser(ls);
            }
        }
        return result;
    }

    //是否从中心库更新基本信息（人脸除外）
    public boolean isUpdateInfoFromCent(HcUser oldVo, CentMemberBo memberBo) {
        HcUser newVo = new HcUser();
        newVo = getHcUserByCentMemberBo(newVo, memberBo);

        //不相等才更新
        if (!oldVo.toSyncCentCompareString().equals(newVo.toSyncCentCompareString())) {
            return true;
        } else {
            return false;
        }
    }

    //中心库人员转为内部人员数据
    public HcUser getHcUserByCentMemberBo(HcUser newVo, CentMemberBo memberBo) {
        newVo.setNm(memberBo.getName());
        if (!StringUtils.isEmpty(memberBo.getSex())) {
            newVo.setSex(Long.valueOf(memberBo.getSex()));
        } else {
            newVo.setSex(null);
        }
        newVo.setPhone(memberBo.getMobile());
        newVo.setAddress(memberBo.getAddress());
        //newVo.setLcensePlate(memberBo.getLicensePlate());
        newVo.setFace(memberBo.getFace());
        newVo.setBirthday(!StringUtils.isEmpty(memberBo.getBirthday()) ? DateUtils.parseDate(memberBo.getBirthday()) : null);
        newVo.setCompany(memberBo.getCompany());
        newVo.setDriverLicense(memberBo.getDriverLicense());
        newVo.setEscortLicense(memberBo.getEscortLicense());
        return newVo;
    }

    /**
     * 獲取實體map
     *
     * @return
     */
    @Override
    public Map<String, HcUser> getEntityMap(List<String> idNoList) {
        Map<String, HcUser> result = new HashMap<String, HcUser>();
        List<HcUser> list = new ArrayList<HcUser>();
        if (idNoList == null) {
            list = hcUserMapper.selectHcUserList(new HcUser());
        } else {
            if (idNoList.size() > 0) {
                list = hcUserMapper.selectHcUserListByIdNos(idNoList.toArray(new String[0]));
            }
        }

        for (HcUser item : list) {
            result.put(item.getIdNo(), item);
        }
        return result;
    }

    @Override
    public HcUser selectHcUserByIdNo(String idNo) {
        return hcUserMapper.selectHcUserByIdNo(idNo);
    }

    /**
     * 從ERP獲取數據
     *
     * @param boList
     * @return
     */
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant) {
        int result = 0;
        List<HcUser> addList = new ArrayList<>();
        List<HcUser> updateList = new ArrayList<>();

        if(boList.size()==0){
            return result;
        }

        //只導入人員
        List<V0NBRKX5Bo> tempBoList = new ArrayList<>();
        for (V0NBRKX5Bo bo : boList) {
            VndIdNoType vndIdNoType = this.checkIdNoType(bo.getIdno());
            if (VndIdNoType.MAN.equals(vndIdNoType)) {
                tempBoList.add(bo);
            }
        }
        boList = tempBoList;

        //開始導入
        List<String> idNoList = boList.stream().map(V0NBRKX5Bo::getIdno).distinct().collect(Collectors.toList());
        Map<String, HcUser> entityMap = this.getEntityMap(idNoList);

        List<HcUser> newList = new ArrayList<>();
        HcUser vo = null;
        for (V0NBRKX5Bo item : boList) {
            vo = item.toHcUser();
            vo.setFacDorNm(plant.getDeptNo());
            vo.setFacNm(plant.getDeptName());
            newList.add(vo);
        }

        List<HcUser> oldList = entityMap.values().stream().collect(Collectors.toList());
        List<String> keyFieldNames = Arrays.asList("idNo");
        String primaryFieldName = "id";
        List<String> syncFieldNames = Arrays.asList("idNo", "nm", "tsNo");

        Map<String, List<HcUser>> oprMap = SljSyncUtils.getOprMap(newList, oldList, keyFieldNames, primaryFieldName, syncFieldNames,
                null,
                null,
                null);
        addList = oprMap.get("addList");
        updateList = oprMap.get("updateList");

        //批量新增
        if (addList.size() > 0) {
            List<List<HcUser>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcUser> ls : lists) {
                result += hcUserMapper.batchInsertHcUser(ls);
            }
        }
        //批量修改
        if (updateList.size() > 0) {
            List<List<HcUser>> lists = BatisUtils.splitList(updateList, 40);
            for (List<HcUser> ls : lists) {
                result += hcUserMapper.batchUpdateHcUser(ls);
            }
        }

        return result;

    }

    /**
     * 從中心庫更新到hcUser
     *
     * @return
     */
    public void syncCent() {
        List<HcUser> boList = new ArrayList<>();
        int pageNum = 1;
        int pageSize = 1000;
        String orderBy = "";
        do {
            PageHelper.startPage(pageNum++, pageSize, orderBy).setReasonable(true);
            boList = hcUserMapper.selectHcUserList(new HcUser());
            List<String> idNos = boList.stream().map(x -> x.getIdNo()).distinct().collect(Collectors.toList());
            this.syncCentByHcUser(idNos, false);
        } while (boList.size() >= pageSize);

    }


}

package com.ruoyi.base.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.*;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.service.IHcCarService;
import com.ruoyi.base.service.IHcUserService;
import com.ruoyi.base.service.IHcWorkOrderCarService;
import com.ruoyi.base.service.IHcWorkOrderService;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.BatisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.slj.SljSyncUtils;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 危化施工單Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcWorkOrderServiceImpl implements IHcWorkOrderService {
    @Autowired
    private HcWorkOrderMapper hcWorkOrderMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IHcWorkOrderCarService hcWorkOrderCarService;
    @Autowired
    private IHcUserService hcUserService;
    @Autowired
    private IHcCarService hcCarService;
    @Autowired
    private HcWorkOrderCarMapper hcWorkOrderCarMapper;
    @Autowired
    private HcWorkOrderUserMapper hcWorkOrderUserMapper;
    @Autowired
    private HcHrWorkOrderMapper hcHrWorkOrderMapper;
    @Autowired
    private HcHrWorkOrderCarMapper hcHrWorkOrderCarMapper;
    @Autowired
    private HcHrWorkOrderUserMapper hcHrWorkOrderUserMapper;

    /**
     * 厂区编号
     */
    @Value("${factoryCode}")
    private String factoryCode;

    /**
     * 查询危化施工單
     *
     * @param id 危化施工單主键
     * @return 危化施工單
     */
    @Override
    public HcWorkOrder selectHcWorkOrderById(Long id) {
        return hcWorkOrderMapper.selectHcWorkOrderById(id);
    }

    /**
     * 查询危化施工單列表
     *
     * @param hcWorkOrder 危化施工單
     * @return 危化施工單
     */
    @Override
    public List<HcWorkOrder> selectHcWorkOrderList(HcWorkOrder hcWorkOrder) {
        return hcWorkOrderMapper.selectHcWorkOrderList(hcWorkOrder);
    }

    /**
     * 新增危化施工單
     *
     * @param hcWorkOrder 危化施工單
     * @return 结果
     */
    @Override
    public int insertHcWorkOrder(HcWorkOrder hcWorkOrder) {
        hcWorkOrder.setCreateTime(DateUtils.getNowDate());
        return hcWorkOrderMapper.insertHcWorkOrder(hcWorkOrder);
    }

    /**
     * 修改危化施工單
     *
     * @param hcWorkOrder 危化施工單
     * @return 结果
     */
    @Override
    public int updateHcWorkOrder(HcWorkOrder hcWorkOrder) {
        hcWorkOrder.setUpdateTime(DateUtils.getNowDate());
        return hcWorkOrderMapper.updateHcWorkOrder(hcWorkOrder);
    }

    /**
     * 批量删除危化施工單
     *
     * @param ids 需要删除的危化施工單主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderByIds(Long[] ids) {
        return hcWorkOrderMapper.deleteHcWorkOrderByIds(ids);
    }

    /**
     * 删除危化施工單信息
     *
     * @param id 危化施工單主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderById(Long id) {
        return hcWorkOrderMapper.deleteHcWorkOrderById(id);
    }

    /**
     * 從ERP同步數據
     *
     * @param boList
     * @return
     */
    public int syncHcErp(List<V0NBRKX5Bo> boList,String addOrUpdate) {
        int result = 0;
        SysDept plant = sysDeptMapper.selectDeptByDeptNo(factoryCode);

        //只導入本廠數據
        List<V0NBRKX5Bo> tempBoList = new ArrayList<>();
        for (V0NBRKX5Bo bo : boList) {
            //獲取廠區
            if (!StringUtils.isEmpty(bo.getDpid())) {
                SysDept myDept = sysDeptMapper.selectDeptByDeptNo(bo.getDpid().substring(2));
                SysDept myPlant = sysDeptService.getPlantByDept(myDept);
                if (myPlant != null && factoryCode.equals(myPlant.getDeptNo())) {
                    tempBoList.add(bo);
                }
            }
        }
        boList = tempBoList;

        //危化工單
        result += syncErp(boList, plant);
        //危化車輛
        result += hcWorkOrderCarService.syncErp(boList, plant,addOrUpdate);
        //危化車輛基礎表
        result += hcCarService.syncErp(boList, plant);
        //危化人員基礎表
        result += hcUserService.syncErp(boList, plant);
        //從車輛基礎表獲取
        hcWorkOrderCarService.syncFromHcCar();
        //從中心庫更新人員
        hcUserService.syncCent();

        return result;
    }

    /**
     * 從ERP同步數據
     *
     * @param boList
     * @return
     */
    @Override
    @Transactional
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant) {
        int result = 0;
        List<HcWorkOrder> addList = new ArrayList<>();
        List<HcWorkOrder> updateList = new ArrayList<>();

        if(boList.size()==0){
            return result;
        }

        //開始導入
        List<String> vhNoList = boList.stream().map(V0NBRKX5Bo::getVhno).distinct().collect(Collectors.toList());
        Map<String, HcWorkOrder> entityMap = this.getEntityMap(vhNoList);

        List<HcWorkOrder> newList = new ArrayList<>();
        HcWorkOrder vo = null;
        for (V0NBRKX5Bo item : boList) {
            vo = item.toHcWorkOrder();
            vo.setFacDorNm(factoryCode);
            vo.setFacNm(plant.getDeptName());
            vo.setUpdateTime(DateUtils.getNowDate());
            newList.add(vo);
        }

        List<HcWorkOrder> oldList = entityMap.values().stream().collect(Collectors.toList());
        List<String> keyFieldNames = Arrays.asList("vhNo");
        String primaryFieldName = "id";
        List<String> syncFieldNames = Arrays.asList("vhNo", "dataFrom", "facDorNm", "facNm");

        Map<String, List<HcWorkOrder>> oprMap = SljSyncUtils.getOprMap(newList, oldList, keyFieldNames, primaryFieldName, syncFieldNames,
                null,
                null,
                null);
        addList = oprMap.get("addList");
        updateList = oprMap.get("updateList");

        //批量新增
        if (addList.size() > 0) {
            List<List<HcWorkOrder>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcWorkOrder> ls : lists) {
                result += hcWorkOrderMapper.batchInsertHcWorkOrder(ls);
            }
        }
        //批量修改
        if (updateList.size() > 0) {
            List<List<HcWorkOrder>> lists = BatisUtils.splitList(updateList, 40);
            for (List<HcWorkOrder> ls : lists) {
                result += hcWorkOrderMapper.batchUpdateHcWorkOrder(ls);
            }
        }
        return result;
    }

    /**
     * 獲取實體map
     *
     * @return
     */
    @Override
    public Map<String, HcWorkOrder> getEntityMap(List<String> vhNoList) {
        Map<String, HcWorkOrder> result = new HashMap<String, HcWorkOrder>();
        List<HcWorkOrder> list = new ArrayList<>();
        if (vhNoList == null) {
            list = hcWorkOrderMapper.selectHcWorkOrderList(null);
        } else {
            if (vhNoList.size() > 0) {
                list = hcWorkOrderMapper.selectHcWorkOrderListByVhNos(vhNoList.toArray(new String[0]));
            }
        }

        for (HcWorkOrder item : list) {
            result.put(item.getVhNo(), item);
        }
        return result;
    }

    @Override
    public HcWorkOrder selectHcWorkOrderByVhNo(String vhNo) {
        return hcWorkOrderMapper.selectHcWorkOrderByVhNo(vhNo);
    }

    /**
     * 轉入歷史表
     *
     * @return
     */
    @Override
    public int toHr() {
        int result = 0;

        //需要移动的车辆hcWorkOrderCarList
        List<HcWorkOrderCar> hcWorkOrderCarList = hcWorkOrderCarMapper.selectHcWorkOrderCarListToHr();
        if (hcWorkOrderCarList.size() == 0) {
            return result;
        }
        List<String> vhNoList = hcWorkOrderCarList.stream().map(HcWorkOrderCar::getVhNo)
                .distinct().collect(Collectors.toList());


        //移動車
        result += moveToHrWorkOrderCar(hcWorkOrderCarList);
        //移動人
        result += moveToHrWorkOrderUser(hcWorkOrderCarList, vhNoList);
        //移動工單
        result += moveToHrWorkOrder(vhNoList);

        return result;
    }


    //車輛移動到歷史表
    private int moveToHrWorkOrderCar(List<HcWorkOrderCar> hcWorkOrderCarList) {
        int result = 0;
        result += toHrWorkOrderCar(hcWorkOrderCarList);

        //批量刪除
        List<Long> delCarIdList = hcWorkOrderCarList.stream().map(x -> x.getId()).collect(Collectors.toList());
        if (delCarIdList.size() > 0) {
            List<List<Long>> lists = BatisUtils.splitList(delCarIdList, 40);
            for (List<Long> ls : lists) {
                result += hcWorkOrderCarMapper.deleteHcWorkOrderCarByIds(ls.toArray(new Long[0]));
            }
        }
        return result;
    }

    //人員移動到歷史表
    private int moveToHrWorkOrderUser(List<HcWorkOrderCar> hcWorkOrderCarList, List<String> vhNoList) {
        int result = 0;

        //需要移动的人员hcWorkOrderUserList
        List<HcWorkOrderUser> hcWorkOrderUserListAll = hcWorkOrderUserMapper.selectHcWorkOrderUserListByVhNos(vhNoList.toArray(new String[0]));
        List<HcWorkOrderUser> hcWorkOrderUserList = hcWorkOrderUserListAll.stream()
                .filter(x -> checkUserInWorkOrderCars(x, hcWorkOrderCarList))
                .collect(Collectors.toList());

        result += toHrWorkOrderUser(hcWorkOrderUserList);
        //批量刪除
        List<Long> delUserIdList = hcWorkOrderUserList.stream().map(x -> x.getId()).collect(Collectors.toList());
        if (delUserIdList.size() > 0) {
            List<List<Long>> lists = BatisUtils.splitList(delUserIdList, 40);
            for (List<Long> ls : lists) {
                result += hcWorkOrderUserMapper.deleteHcWorkOrderUserByIds(ls.toArray(new Long[0]));
            }
        }
        return result;
    }

    //工單移動到歷史表
    private int moveToHrWorkOrder(List<String> vhNoList) {
        int result = 0;

        //需要移动的工单hcWorkOrderList=涉及的工單-有車的涉及工單
        List<HcWorkOrder> hcWorkOrderListAll = hcWorkOrderMapper.selectHcWorkOrderListByVhNos(vhNoList.toArray(new String[0]));
        //當前車輛表
        List<HcWorkOrderCar> currHcWorkOrderCarList = hcWorkOrderCarMapper.selectHcWorkOrderCarListByVhNos(vhNoList.toArray(new String[0]));
        //依舊有車的工單
        List<String> vhNoListHasCar = currHcWorkOrderCarList.stream().map(x -> x.getVhNo())
                .distinct().collect(Collectors.toList());
        List<HcWorkOrder> hcWorkOrderList = hcWorkOrderListAll.stream()
                .filter(x -> !vhNoListHasCar.contains(x.getVhNo()))
                .collect(Collectors.toList());

        //插入历史表
        result += toHrWorkOrder(hcWorkOrderList);
        //批量刪除
        List<Long> delWorkOrderList = hcWorkOrderList.stream().map(x -> x.getId()).collect(Collectors.toList());
        if (delWorkOrderList.size() > 0) {
            List<List<Long>> lists = BatisUtils.splitList(delWorkOrderList, 40);
            for (List<Long> ls : lists) {
                result += hcWorkOrderMapper.deleteHcWorkOrderByIds(ls.toArray(new Long[0]));
            }
        }

        return result;
    }

    /**
     * 危化工單移動到歷史表
     *
     * @param hcWorkOrderList
     */
    private int toHrWorkOrder(List<HcWorkOrder> hcWorkOrderList) {
        int result = 0;
        List<HcHrWorkOrder> addList = new ArrayList<>();
        HcHrWorkOrder hcHrWorkOrder = null;

        if (hcWorkOrderList.size() == 0) {
            return result;
        }


        //刪除老數據
        List<String> vhNoList = hcWorkOrderList.stream().map(x -> x.getVhNo())
                .distinct().collect(Collectors.toList());
        List<HcHrWorkOrder> oldList = hcHrWorkOrderMapper.selectHcHrWorkOrderListByVhNos(vhNoList.toArray(new String[0]));
        List<Long> delWorkOrderList = oldList.stream().map(x -> x.getId()).collect(Collectors.toList());
        //批量刪除
        if (delWorkOrderList.size() > 0) {
            List<List<Long>> lists = BatisUtils.splitList(delWorkOrderList, 40);
            for (List<Long> ls : lists) {
                result += hcHrWorkOrderMapper.deleteHcHrWorkOrderByIds(ls.toArray(new Long[0]));
            }
        }

        //新增
        for (HcWorkOrder item : hcWorkOrderList) {
            hcHrWorkOrder = new HcHrWorkOrder();
            BeanUtils.copyProperties(item, hcHrWorkOrder);
            hcHrWorkOrder.setId(null);
            addList.add(hcHrWorkOrder);
        }

        //批量新增
        if (addList.size() > 0) {
            List<List<HcHrWorkOrder>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcHrWorkOrder> ls : lists) {
                result += hcHrWorkOrderMapper.batchInsertHcHrWorkOrder(ls);
            }
        }
        return result;
    }

    /**
     * 危化工單車輛移動到歷史表
     *
     * @param hcWorkOrderCarList
     */
    private int toHrWorkOrderCar(List<HcWorkOrderCar> hcWorkOrderCarList) {
        int result = 0;
        List<HcHrWorkOrderCar> addList = new ArrayList<>();
        HcHrWorkOrderCar hcHrWorkOrderCar = null;

        if (hcWorkOrderCarList.size() == 0) {
            return result;
        }

        //刪除老數據
        List<String> vhNoList = hcWorkOrderCarList.stream().map(x -> x.getVhNo())
                .distinct().collect(Collectors.toList());
        List<HcHrWorkOrderCar> oldListAll = hcHrWorkOrderCarMapper.selectHcHrWorkOrderCarListByVhNos(vhNoList.toArray(new String[0]));
        List<HcHrWorkOrderCar> oldList = oldListAll.stream()
                .filter(x -> checkInHcWorkOrderCarForHr(x, hcWorkOrderCarList)).collect(Collectors.toList());
        List<Long> delCarIdList = oldList.stream().map(x -> x.getId()).collect(Collectors.toList());
        //批量刪除
        if (delCarIdList.size() > 0) {
            List<List<Long>> lists = BatisUtils.splitList(delCarIdList, 40);
            for (List<Long> ls : lists) {
                result += hcHrWorkOrderCarMapper.deleteHcHrWorkOrderCarByIds(ls.toArray(new Long[0]));
            }
        }

        //新增
        for (HcWorkOrderCar item : hcWorkOrderCarList) {
            hcHrWorkOrderCar = new HcHrWorkOrderCar();
            BeanUtils.copyProperties(item, hcHrWorkOrderCar);
            hcHrWorkOrderCar.setId(null);
            addList.add(hcHrWorkOrderCar);
        }

        //批量新增
        if (addList.size() > 0) {
            List<List<HcHrWorkOrderCar>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcHrWorkOrderCar> ls : lists) {
                result += hcHrWorkOrderCarMapper.batchInsertHcHrWorkOrderCar(ls);
            }
        }
        return result;
    }

    /**
     * 危化工單人員移動到歷史表
     *
     * @param hcWorkOrderUserList
     */
    private int toHrWorkOrderUser(List<HcWorkOrderUser> hcWorkOrderUserList) {
        int result = 0;
        List<HcHrWorkOrderUser> addList = new ArrayList<>();
        HcHrWorkOrderUser hcHrWorkOrderUser = null;

        if (hcWorkOrderUserList.size() == 0) {
            return result;
        }

        //刪除老數據
        List<String> vhNoList = hcWorkOrderUserList.stream().map(x -> x.getVhNo())
                .distinct().collect(Collectors.toList());
        List<HcHrWorkOrderUser> oldListAll = hcHrWorkOrderUserMapper.selectHcHrWorkOrderUserListByVhNos(vhNoList.toArray(new String[0]));
        List<HcHrWorkOrderUser> oldList = oldListAll.stream()
                .filter(x -> checkInHcWorkOrderUserForHr(x, hcWorkOrderUserList)).collect(Collectors.toList());
        List<Long> delUserIdList = oldList.stream().map(x -> x.getId()).collect(Collectors.toList());
        //批量刪除
        if (delUserIdList.size() > 0) {
            List<List<Long>> lists = BatisUtils.splitList(delUserIdList, 40);
            for (List<Long> ls : lists) {
                result += hcHrWorkOrderUserMapper.deleteHcHrWorkOrderUserByIds(ls.toArray(new Long[0]));
            }
        }
        //新增
        for (HcWorkOrderUser item : hcWorkOrderUserList) {
            hcHrWorkOrderUser = new HcHrWorkOrderUser();
            BeanUtils.copyProperties(item, hcHrWorkOrderUser);
            hcHrWorkOrderUser.setId(null);
            addList.add(hcHrWorkOrderUser);
        }

        //批量新增
        if (addList.size() > 0) {
            List<List<HcHrWorkOrderUser>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcHrWorkOrderUser> ls : lists) {
                result += hcHrWorkOrderUserMapper.batchInsertHcHrWorkOrderUser(ls);
            }
        }
        return result;
    }

    //判斷是否在工單車裡面
    private boolean checkInHcWorkOrderCarForHr(HcHrWorkOrderCar car, List<HcWorkOrderCar> list) {
        long cnt = list.stream()
                .filter(x -> Objects.equals(x.getVhNo(), car.getVhNo())
                        && Objects.equals(x.getIdNo(), car.getIdNo())
                        && Objects.equals(x.getIpltTm(),car.getIpltTm()))
                .count();
        return cnt > 0 ? true : false;
    }

    //判斷是否在工單車裡面
    private boolean checkInHcWorkOrderUserForHr(HcHrWorkOrderUser user, List<HcWorkOrderUser> list) {
        long cnt = list.stream()
                .filter(x -> Objects.equals(x.getVhNo(), user.getVhNo())
                        && Objects.equals(x.getIdNo(), user.getIdNo())
                        && Objects.equals(x.getIpltTm(),user.getIpltTm()))
                .count();
        return cnt > 0 ? true : false;
    }

    /**
     * 判断危化人员是否在危化车辆下面
     *
     * @param user
     * @param hcWorkOrderCarList
     * @return
     */
    private boolean checkUserInWorkOrderCars(HcWorkOrderUser user, List<HcWorkOrderCar> hcWorkOrderCarList) {
        long cnt = hcWorkOrderCarList.stream()
                .filter(x -> Objects.equals(x.getVhNo(), user.getVhNo())
                        && Objects.equals(x.getIdNo(), user.getLicense())
                        && Objects.equals(x.getIpltTm(),user.getIpltTm()))
                .count();
        return cnt > 0 ? true : false;
    }


}

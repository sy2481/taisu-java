package com.ruoyi.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.FactoryWorkBO;
import com.ruoyi.base.bo.workCarBo;
import com.ruoyi.base.domain.ManFactory;
import com.ruoyi.base.domain.ManWork;
import com.ruoyi.base.domain.ManWorkFactory;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.mapper.ManFactoryMapper;
import com.ruoyi.base.mapper.ManWorkFactoryMapper;
import com.ruoyi.base.mapper.ManWorkMapper;
import com.ruoyi.base.service.IManFactoryService;
import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 厂商Service业务层处理
 *
 * @author ruoyi
 * @date 2022-03-06
 */
@Service
public class ManFactoryServiceImpl implements IManFactoryService {
    private static final Logger log = LoggerFactory.getLogger(ManFactoryServiceImpl.class);
    @Autowired
    private ManFactoryMapper manFactoryMapper;
    @Autowired
    private ManWorkFactoryMapper manWorkFactoryMapper;
    @Autowired
    private ManWorkMapper manWorkMapper;
    @Autowired
    protected Validator validator;
    @Autowired
    private ApiService apiService;
    @Autowired
    private PersonSendService personSendService;

    /**
     * 查询厂商
     */
    @Override
    public ManFactory selectManFactoryByFactoryId(Long factoryId) {
        return manFactoryMapper.selectManFactoryByFactoryId(factoryId);
    }

    /**
     * 查询厂商列表
     */
    @Override
    public List<ManFactory> selectManFactoryList(ManFactory manFactory) {

        //查询当前登录人是否有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList != null) {
            manFactory.setFactoryIdList(longList);
        }
        return manFactoryMapper.selectManFactoryList(manFactory);
    }

    /**
     * 新增厂商
     */
    @Override
    public int insertManFactory(ManFactory manFactory) {
        List<ManFactory> byCommonParams = manFactoryMapper.getByCommonParams(manFactory.getIdCard(), null, null, null);
        if (byCommonParams.size() == 0) {
            //添加厂商
            ManWork manWork = manWorkMapper.selectManWorkByworkNo(manFactory.getWorkNo());
            manFactory.setDeptId(manWork.getDeptId());
            int factory = manFactoryMapper.insertManFactory(manFactory);
            //添加关联表信息
            ManWorkFactory manWorkFactory = new ManWorkFactory();
            manWorkFactory.setWorkId(manWork.getWorkId());
            manWorkFactory.setFactoryId(manFactory.getFactoryId());
            manWorkFactory.setEffectiveTime(new Date().toString());
            manWorkFactoryMapper.insertManWorkFactory(manWorkFactory);
            return factory;
        }
        return -1;
    }

    /**
     * 修改厂商
     */
    @Override
    public int updateManFactory(ManFactory manFactory) {
        //修改时若修改了工单号，需要修改中间表
        ManFactory manFactory1 = manFactoryMapper.selectManFactoryByFactoryId(manFactory.getFactoryId());
        if (manFactory.getIdCard().equals(manFactory1.getIdCard())) {
            //身份証號未修改
            //修改工单号
            if (!manFactory.getWorkNo().equals(manFactory1.getWorkNo())) {
                //删除历史关联表
                manWorkFactoryMapper.deleteWorkFactory(manFactory.getFactoryId());
                //修改中间表
                ManWork manWork = manWorkMapper.selectManWorkByworkNo(manFactory.getWorkNo());
                manFactory.setDeptId(manWork.getDeptId());
                ManWorkFactory manWorkFactory1 = new ManWorkFactory();

                BeanUtils.copyProperties(manWork, manWorkFactory1);

                manWorkFactory1.setFactoryId(manFactory.getFactoryId());
                manWorkFactoryMapper.updateManWorkByFactoryId(manWorkFactory1);
            }
            return manFactoryMapper.updateManFactory(manFactory);
        } else {
            List<ManFactory> byCommonParams = manFactoryMapper.getByCommonParams(manFactory.getIdCard(), null, null, null);
            if (byCommonParams.size() == 0) {
                //修改工单号
                if (!manFactory.getWorkNo().equals(manFactory1.getWorkNo())) {
                    //修改中间表
                    ManWork manWork = manWorkMapper.selectManWorkByworkNo(manFactory.getWorkNo());
                    ManWorkFactory manWorkFactory1 = new ManWorkFactory();
                    BeanUtils.copyProperties(manWork, manWorkFactory1);
                    manWorkFactory1.setFactoryId(manFactory.getFactoryId());
                    manWorkFactoryMapper.updateManWorkByFactoryId(manWorkFactory1);
                }
                return manFactoryMapper.updateManFactory(manFactory);
            }

        }
        return -1;
    }

    /**
     * 批量删除厂商
     */
    @Override
    public int deleteManFactoryByFactoryIds(Long[] factoryIds) {
        for (Long factoryId : factoryIds) {
            ManFactory factory = manFactoryMapper.selectManFactoryByFactoryId(factoryId);
            personSendService.downSendDeletePerson(factory.getIdCard());
        }
        return manFactoryMapper.deleteManFactoryByFactoryIds(factoryIds);
    }

    /**
     * 删除厂商信息
     */
    @Override
    public int deleteManFactoryByFactoryId(Long factoryId) {
        return manFactoryMapper.deleteManFactoryByFactoryId(factoryId);
    }

    /**
     * 并且組合數據
     */
    @Override
    public List<FactoryWorkBO> listByWorkNoAndDate(String workNo, String date, Integer workType) {
        List<FactoryWorkBO> factoryWork = manFactoryMapper.listByWorkNoAndDate(workNo, date, workType);
        if (workType == 1) {
            List<workCarBo> manWorkList = manWorkMapper.selectManWork(workNo, date);
            if (factoryWork.size() > 0) {
                factoryWork.get(0).setWorkCarList(manWorkList);
            }
        }
        return factoryWork;
    }


    /**
     * 导入廠商人員数据
     *
     * @param factoryList     廠商人員数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importManFactory(List<ManFactory> factoryList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(factoryList) || factoryList.size() == 0) {
            throw new ServiceException("导入廠商人員不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (ManFactory manFactory : factoryList) {
            try {
                //
                ManFactory factory = manFactoryMapper.selectManFactoryByIdCard(manFactory.getIdCard());
                if (StringUtils.isNull(factory)) {
                    BeanValidators.validateWithException(validator, manFactory);
                    this.insertManFactoryExel(manFactory);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、厂商人员 " + manFactory.getName() + " 导入成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、厂商人员 " + manFactory.getName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public int updateCar() {
        return manFactoryMapper.updateCar();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delFactory() {
        //删除厂商
        manFactoryMapper.delFactory();
        //删除中间表
        manWorkFactoryMapper.delTimeFactory();
    }

    @Override
    public List<ManFactory> selectCangerousCar(String idCard, String plateNo) {
        return manFactoryMapper.selectCangerousCar(idCard, plateNo);
    }

    @Override
    public int deleteFaceByFactoryId(Long factoryId) {
        //修改状态
        manFactoryMapper.sendBackStatus(factoryId, 0);
        ManFactory factory = manFactoryMapper.selectManFactoryByFactoryId(factoryId);
        //发送请求
        if (StringUtils.isNotBlank(factory.getIdCard())) {
            personSendService.downSendDeletePerson(factory.getIdCard());
        }
        return manFactoryMapper.deleteFaceByFactoryId(factoryId);
    }

    /**
     * 新增厂商（exel导入插入数据）
     *
     * @param manFactory 厂商
     * @return 结果
     */
    @Override
    @Transactional
    public int insertManFactoryExel(ManFactory manFactory) throws ParseException {
//        String bitrhday = manFactory.getBirthDay();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date parse = df.parse(bitrhday);
//        manFactory.setBirthDay(parse);
        int rows = manFactoryMapper.insertManFactory(manFactory);
//        if (rows>0){
//            ManWork manWork = new ManWork();
//            manWork.setWorkNo(manFactory.getWorkNo());
//            manWork.setProjectNo(manFactory.getThisNumber());
//            manWork.setMngName(manFactory.getMngName());
//            manWork.setMngTime(manFactory.getMngTime());
//            manWork.setWorkTime(manFactory.getWorkTime());
//            manWork.setCarId(manFactory.getLcensePlate());
//            int i = manWorkMapper.insertManWork(manWork);
//            if (i>0){
//                log.info("厂商ID：{}",manFactory.getFactoryId());
//                log.info("工单ID：{}",manWork.getWorkId());
//                log.info("工单有效期：{}",manWork.getWorkTime());
//                ManWorkFactory manWorkFactory = new ManWorkFactory(manWork.getWorkId(),manFactory.getFactoryId(),manWork.getWorkTime());
//                int j = manWorkFactoryMapper.insertManWorkFactory(manWorkFactory);
//                return j;
//            }
//        }
        return rows;
    }

    @Override
    @Transactional
    public List<FactoryWorkBO> saveForNoFace(List<FactoryWorkBO> list, Map<String, JSONObject> map) {
        for (FactoryWorkBO item : list) {
            String idCard = item.getIdCard();
            if (map.containsKey(idCard)) {
                JSONObject jObj = map.get(idCard);
                String face = jObj.getString("face");
                String phone = jObj.getString("phone");
                String address = jObj.getString("address");

                item.setFace(face);//从中心库获得头像
                item.setPhone(phone);
                item.setAddress(address);

                ManFactory manFactory=new ManFactory();
                manFactory.setIdCard(idCard);
                manFactory.setFace(face);
                manFactory.setPhone(phone);
                manFactory.setAddress(address);
                manFactoryMapper.updateFaceByIdCard(manFactory);//更新无头像的人员
            }
        }
        return list;
    }

    /**
     * 保存中心库人脸
     *
     * @param manFactory
     */
    public void saveFaceForCenter(ManFactory manFactory) {
        //通过身份证查找人员
        ManFactory manFactoryCenter = manFactoryMapper.selectManFactoryByIdCard(manFactory.getIdCard());
        if (manFactoryCenter == null) {//无数据则新增
            manFactoryCenter = new ManFactory();
            BeanUtils.copyProperties(manFactory, manFactoryCenter);
            manFactoryCenter.setFactoryId(null);
            manFactoryMapper.insertManFactory(manFactoryCenter);

        } else {//有数据
            manFactoryCenter.setFace(manFactory.getFace());
            manFactoryCenter.setPhone(manFactory.getPhone());
            manFactoryCenter.setAddress(manFactory.getAddress());
            manFactoryMapper.updateManFactory(manFactoryCenter);
        }
    }

    /**
     * 根据身份证获取
     *
     * @param idCards 身份证号
     */
    public Map<String, ManFactory> getListByIdCards(List<String> idCards) {
        Map<String, ManFactory> map = new HashMap<String, ManFactory>();
        List<ManFactory> list = manFactoryMapper.selectListFaceByIdCards(idCards);
        for (ManFactory item : list) {
            String key = item.getIdCard();
            if (!map.containsKey(key)) {
                map.put(key, item);
            }
        }
        return map;
    }

}

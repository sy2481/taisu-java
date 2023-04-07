package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.BaseErp;
import com.ruoyi.base.domain.InOutLogBasicData;
import com.ruoyi.base.domain.InOutUserStatus;
import com.ruoyi.base.domain.PeakInfo;
import com.ruoyi.base.service.*;
import com.ruoyi.base.vo.*;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DynamicBoardServiceImpl implements DynamicBoardService {

    @Autowired
    private InOutLogBasicDataService inOutLogBasicDataService;

    @Autowired
    private PeakInfoService peakInfoService;

    @Autowired
    private InOutUserStatusService inOutUserStatusService;

    @Autowired
    private BaseErpService baseErpService;

    @Override
    public List<DayInAndOutInfoVO> getDayInAndOutInfo() {
        //获取尖峰人数和车数
        PeakInfo personPeakInfo = new PeakInfo();
        personPeakInfo.setPeakType("peakPerson");
        Integer personPeakCount = peakInfoService.selectPeakInfoList(personPeakInfo).get(0).getPeakCount();

        PeakInfo carPeakInfo = new PeakInfo();
        carPeakInfo.setPeakType("peakCar");
        Integer carPeakCount = peakInfoService.selectPeakInfoList(carPeakInfo).get(0).getPeakCount();

        //未出场的厂商人数
        Integer personInCount = inOutLogBasicDataService.selectPersonInNum();

        Integer carInCount = inOutLogBasicDataService.selectCarInNum();

        Integer userInCount = inOutUserStatusService.selectUserInCount();

        List<DayInAndOutInfoVO> dayInAndOutInfoVOS = new ArrayList<>();

        DayInAndOutInfoVO peakPersonDayInAndOutInfoVO = new DayInAndOutInfoVO();
        peakPersonDayInAndOutInfoVO.setInfoType("peakPerson");
        peakPersonDayInAndOutInfoVO.setInfoCount(personPeakCount);
        dayInAndOutInfoVOS.add(peakPersonDayInAndOutInfoVO);

        DayInAndOutInfoVO peakCarDayInAndOutInfoVO = new DayInAndOutInfoVO();
        peakCarDayInAndOutInfoVO.setInfoType("peakCar");
        peakCarDayInAndOutInfoVO.setInfoCount(carPeakCount);
        dayInAndOutInfoVOS.add(peakCarDayInAndOutInfoVO);

        DayInAndOutInfoVO allPersonDayInAndOutInfoVO = new DayInAndOutInfoVO();
        allPersonDayInAndOutInfoVO.setInfoType("allPersonInCount");
        allPersonDayInAndOutInfoVO.setInfoCount(personInCount + userInCount);
        dayInAndOutInfoVOS.add(allPersonDayInAndOutInfoVO);

        DayInAndOutInfoVO allCarDayInAndOutInfoVO = new DayInAndOutInfoVO();
        allCarDayInAndOutInfoVO.setInfoType("allCarInCount");
        allCarDayInAndOutInfoVO.setInfoCount(carInCount);
        dayInAndOutInfoVOS.add(allCarDayInAndOutInfoVO);

        DayInAndOutInfoVO personInCountDayInAndOutInfoVO = new DayInAndOutInfoVO();
        personInCountDayInAndOutInfoVO.setInfoType("personInCount");
        personInCountDayInAndOutInfoVO.setInfoCount(personInCount);
        dayInAndOutInfoVOS.add(personInCountDayInAndOutInfoVO);

        DayInAndOutInfoVO carInCountDayInAndOutInfoVO = new DayInAndOutInfoVO();
        carInCountDayInAndOutInfoVO.setInfoType("carInCount");
        carInCountDayInAndOutInfoVO.setInfoCount(carInCount);
        dayInAndOutInfoVOS.add(carInCountDayInAndOutInfoVO);
        DayInAndOutInfoVO userInCountDayInAndOutInfoVO = new DayInAndOutInfoVO();
        userInCountDayInAndOutInfoVO.setInfoType("userInCount");
        userInCountDayInAndOutInfoVO.setInfoCount(userInCount);
        dayInAndOutInfoVOS.add(userInCountDayInAndOutInfoVO);

        DayInAndOutInfoVO userCarInCountDayInAndOutInfoVO = new DayInAndOutInfoVO();
        userCarInCountDayInAndOutInfoVO.setInfoType("userCarInCount");
        userCarInCountDayInAndOutInfoVO.setInfoCount(0);
        dayInAndOutInfoVOS.add(userCarInCountDayInAndOutInfoVO);
        return dayInAndOutInfoVOS;
    }

    @Override
    public List<GetAllDayInAndOutInfoVO> getAllDayInAndOutInfo() {
        List<GetAllDayInAndOutInfoVO> getAllDayInAndOutInfoVOList = new ArrayList<>();
        Date today = DateUtils.parseDate(DateUtils.getDate());
        String todayDateTime = DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS0, today);

        List<Map<String, Object>> allAreas = inOutLogBasicDataService.selectAllAreaInOutLogBasicDataList(todayDateTime);


        for (Map<String, Object> data : allAreas) {
            StringBuffer oEBuffer = new StringBuffer();
            Set<String> allOprEnvts = new HashSet<>();
            List<InOutLogBasicData> inOutLogBasicDataList = inOutLogBasicDataService.selectInOutLogBasicDataListByArea(todayDateTime, data.get("area_no").toString());
            Map<String, List<InOutLogBasicData>> persons = inOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getIdno));
            Map<String, List<InOutLogBasicData>> vhnos = inOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getVhNo));
            for (String vhno : vhnos.keySet()) {
                BaseErp baseErp = new BaseErp();
                baseErp.setVhNo(vhno);
                BaseErp baseErpInfo = baseErpService.selectBaseErpList(baseErp).get(0);
                String[] oprEnvts = baseErpInfo.getOprEnvt().split(";");
                for (String oprEnvt : oprEnvts) {
                    allOprEnvts.add(oprEnvt.split("\\.")[1].split("-")[0]);
                }
            }


            for (String o : allOprEnvts) {
                oEBuffer.append(o).append(";");
            }

            List<InOutLogBasicData> carInOutLogBasicDataList = inOutLogBasicDataService.selectCarInOutLogBasicDataListByArea(todayDateTime, data.get("area_no").toString());
            Map<String, List<InOutLogBasicData>> car = carInOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getIdno));
            GetAllDayInAndOutInfoVO getAllDayInAndOutInfoVO = new GetAllDayInAndOutInfoVO();
            getAllDayInAndOutInfoVO.setCarCount(car.size());
            getAllDayInAndOutInfoVO.setPersonCount(persons.size());
            String oE = oEBuffer.toString();
            getAllDayInAndOutInfoVO.setOprEnvts(oE.substring(0, oE.length()));
            getAllDayInAndOutInfoVO.setWorkCount(vhnos.size());
            getAllDayInAndOutInfoVO.setAreaName(data.get("area_no").toString());
            getAllDayInAndOutInfoVOList.add(getAllDayInAndOutInfoVO);
        }
        return getAllDayInAndOutInfoVOList;
    }

//    @Override
//    public List<GetAllDayInAndOutInfoVO> getAllDayInAndOutInfo() {
//        List<GetAllDayInAndOutInfoVO> getAllDayInAndOutInfoVOList = new ArrayList<>();
//        Date today = DateUtils.parseDate(DateUtils.getDate());
//        String todayDateTime = DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS0, today);
//
//        List<Map<String, Object>> allAreas = inOutLogBasicDataService.selectAllAreaInOutLogBasicDataList(todayDateTime);
//
//
//        for (Map<String, Object> data : allAreas) {
//            StringBuffer oEBuffer = new StringBuffer();
//            Set<String> allOprEnvts = new HashSet<>();
//            List<InOutLogBasicData> inOutLogBasicDataList = inOutLogBasicDataService.selectInOutLogBasicDataListByArea(todayDateTime, data.get("area_no").toString());
//            Map<String, List<InOutLogBasicData>> persons = inOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getIdno));
//            Map<String, List<InOutLogBasicData>> vhnos = inOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getVhNo));
//            for (String vhno : vhnos.keySet()) {
//                BaseErp baseErp = new BaseErp();
//                baseErp.setVhNo(vhno);
//                BaseErp baseErpInfo = baseErpService.selectBaseErpList(baseErp).get(0);
//                String[] oprEnvts = baseErpInfo.getOprEnvt().split(";");
//                for (String oprEnvt : oprEnvts) {
//                    allOprEnvts.add(oprEnvt.split("\\.")[1].split("-")[0]);
//                }
//            }
//
//
//            for (String o : allOprEnvts) {
//                oEBuffer.append(o).append(";");
//            }
//
//            List<InOutLogBasicData> carInOutLogBasicDataList = inOutLogBasicDataService.selectCarInOutLogBasicDataListByArea(todayDateTime, data.get("area_no").toString());
//            Map<String, List<InOutLogBasicData>> car = carInOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getIdno));
//            GetAllDayInAndOutInfoVO getAllDayInAndOutInfoVO = new GetAllDayInAndOutInfoVO();
//            getAllDayInAndOutInfoVO.setCarCount(car.size());
//            getAllDayInAndOutInfoVO.setPersonCount(persons.size());
//            String oE = oEBuffer.toString();
//            getAllDayInAndOutInfoVO.setOprEnvts(oE.substring(0, oE.length()));
//            getAllDayInAndOutInfoVO.setWorkCount(vhnos.size());
//            getAllDayInAndOutInfoVO.setAreaName(data.get("area_no").toString());
//            getAllDayInAndOutInfoVOList.add(getAllDayInAndOutInfoVO);
//        }
//        return getAllDayInAndOutInfoVOList;
//    }

    @Override
    public GetDayInAndOutInfoByAreaVO getDayInAndOutInfoByArea(String area) {
        Date today = DateUtils.parseDate(DateUtils.getDate());
        String todayDateTime = DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS0, today);

        StringBuffer oEBuffer = new StringBuffer();

        List<InOutLogBasicData> inOutLogBasicDataList = inOutLogBasicDataService.selectInOutLogBasicDataListByArea(todayDateTime, area);
        List<GetDayInAndOutInfoByAreaPerson> getDayInAndOutInfoByAreaPeople = new ArrayList<>();
        List<GetDayInAndOutInfoByAreaCar> getDayInAndOutInfoByAreaCars = new ArrayList<>();
        List<GetDayInAndOutInfoByAreaBaseErp> erps = new ArrayList<>();
        Map<String, List<InOutLogBasicData>> persons = inOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getIdno));
        Map<String, List<InOutLogBasicData>> vhnos = inOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getVhNo));
        for (String vhno : vhnos.keySet()) {
            BaseErp baseErp = new BaseErp();
            baseErp.setVhNo(vhno);
            BaseErp baseErpInfo = baseErpService.selectBaseErpList(baseErp).get(0);
            String optEventString = optEventString(baseErpInfo.getOprEnvt());

            List<Map<String, Object>> personInfos = inOutLogBasicDataService.selectPersonAreaInOutLogBasicDataListByArea(todayDateTime, vhno);
            for (Map<String, Object> personInfo : personInfos) {
                GetDayInAndOutInfoByAreaPerson person = new GetDayInAndOutInfoByAreaPerson();
                person.setFactoryName(personInfo.get("tkVnd").toString());
                person.setEgName(personInfo.get("egName").toString());
                person.setIdCard(personInfo.get("idNo").toString());
                person.setPersonName(personInfo.get("name").toString());
                Date endTime = DateUtils.parseDate(personInfo.get("endTime").toString());
                Date outTime = null;
                if (personInfo.get("outTime") != null) {
                    outTime = DateUtils.parseDate(personInfo.get("outTime").toString());
                }
                if (outTime != null) {
                    if (endTime.before(outTime)) {
                        person.setInTime(personInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", outTime) + ")");
                    } else {
                        person.setInTime(personInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");
                    }
                } else {
                    person.setInTime(personInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");

                }
                getDayInAndOutInfoByAreaPeople.add(person);
            }

            List<Map<String, Object>> carInfos = inOutLogBasicDataService.selectCarAreaInOutLogBasicDataListByArea(todayDateTime, vhno);
            for (Map<String, Object> carInfo : carInfos) {
                GetDayInAndOutInfoByAreaCar car = new GetDayInAndOutInfoByAreaCar();
                car.setFactoryName(carInfo.get("tkVnd").toString());
                car.setEgName(carInfo.get("egName").toString());
                car.setCarNumber(carInfo.get("idNo").toString());
                Date endTime = DateUtils.parseDate(carInfo.get("endTime").toString());
                Date outTime = null;
                if (carInfo.get("outTime") != null) {
                    outTime = DateUtils.parseDate(carInfo.get("outTime").toString());
                }
                if (outTime != null) {
                    if (endTime.before(outTime)) {
                        car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", outTime) + ")");
                    } else {
                        car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");
                    }
                } else {
                    car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");

                }
//                if (endTime.before(outTime)) {
//                    car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", outTime) + ")");
//                } else {
//                    car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");
//                }
                getDayInAndOutInfoByAreaCars.add(car);
            }


            GetDayInAndOutInfoByAreaBaseErp erp = new GetDayInAndOutInfoByAreaBaseErp();
            erp.setFactoryName(baseErpInfo.getTkVnd());
            erp.setOprEnvt(optEventString);
            erp.setVhNo(baseErpInfo.getVhNo());
            erp.setPersonCount(personInfos.size());
            erp.setCarCount(carInfos.size());
            erp.setAplDp(baseErpInfo.getAplDp());
            erp.setCnstSite(baseErpInfo.getCnstSite());
            erp.setEgNo(baseErpInfo.getEgNo());
            erp.setEgNm(baseErpInfo.getEgNm());
            erp.setCnstNum(baseErpInfo.getCnstNum());
            erp.setTkVndSafBos(baseErpInfo.getTkVndSafBos());
            erp.setTaskMaster(baseErpInfo.getTaskMaster());
            erp.setInspector(baseErpInfo.getInspector());
            erp.setCnstTime(baseErpInfo.getCnstTime());
            erp.setOprEnvtErp(baseErpInfo.getOprEnvt());
            erps.add(erp);
        }
        GetDayInAndOutInfoByAreaVO getDayInAndOutInfoByAreaVO = new GetDayInAndOutInfoByAreaVO();
        getDayInAndOutInfoByAreaVO.setGetDayInAndOutInfoByAreaPersons(getDayInAndOutInfoByAreaPeople);
        getDayInAndOutInfoByAreaVO.setGetDayInAndOutInfoByAreaCars(getDayInAndOutInfoByAreaCars);
        getDayInAndOutInfoByAreaVO.setGetDayInAndOutInfoByAreaBaseErps(erps);

        return getDayInAndOutInfoByAreaVO;
    }

    public GetAllPersonAndCarDayInAndOutInfoVo getAllPersonAndCarDayInAndOutInfo() {
        Date today = DateUtils.parseDate(DateUtils.getDate());
        String todayDateTime = DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS0, today);


        List<InOutLogBasicData> inOutLogBasicDataList = inOutLogBasicDataService.selectTodayInOutLogBasicDataList(todayDateTime);
        List<GetDayInAndOutInfoByAreaPerson> getDayInAndOutInfoByAreaPeople = new ArrayList<>();
        List<GetDayInAndOutInfoByAreaCar> getDayInAndOutInfoByAreaCars = new ArrayList<>();
        Map<String, List<InOutLogBasicData>> vhnos = inOutLogBasicDataList.stream().collect(Collectors.groupingBy(InOutLogBasicData::getVhNo));
        for (String vhno : vhnos.keySet()) {


            List<Map<String, Object>> personInfos = inOutLogBasicDataService.selectPersonAreaInOutLogBasicDataListByArea(todayDateTime, vhno);
            for (Map<String, Object> personInfo : personInfos) {
                GetDayInAndOutInfoByAreaPerson person = new GetDayInAndOutInfoByAreaPerson();
                person.setFactoryName(personInfo.get("tkVnd").toString());
                person.setEgName(personInfo.get("egName").toString());
                person.setIdCard(personInfo.get("idNo").toString());
                person.setPersonName(personInfo.get("name").toString());
                Date endTime = DateUtils.parseDate(personInfo.get("endTime").toString());
                Date outTime = null;
                if (personInfo.get("outTime") != null) {
                    outTime = DateUtils.parseDate(personInfo.get("outTime").toString());
                }
                if (outTime != null) {
                    if (endTime.before(outTime)) {
                        person.setInTime(personInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", outTime) + ")");
                    } else {
                        person.setInTime(personInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");
                    }
                } else {
                    person.setInTime(personInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");

                }
                getDayInAndOutInfoByAreaPeople.add(person);
            }

            List<Map<String, Object>> carInfos = inOutLogBasicDataService.selectCarAreaInOutLogBasicDataListByArea(todayDateTime, vhno);
            for (Map<String, Object> carInfo : carInfos) {
                GetDayInAndOutInfoByAreaCar car = new GetDayInAndOutInfoByAreaCar();
                car.setFactoryName(carInfo.get("tkVnd").toString());
                car.setEgName(carInfo.get("egName").toString());
                car.setCarNumber(carInfo.get("idNo").toString());
                Date endTime = DateUtils.parseDate(carInfo.get("endTime").toString());
                Date outTime = null;
                if (carInfo.get("outTime") != null) {
                    outTime = DateUtils.parseDate(carInfo.get("outTime").toString());
                }
                if (outTime != null) {
                    if (endTime.before(outTime)) {
                        car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", outTime) + ")");
                    } else {
                        car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");
                    }
                } else {
                    car.setInTime(carInfo.get("opltTime").toString() + " (預定出廠：" + DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", endTime) + ")");

                }
                getDayInAndOutInfoByAreaCars.add(car);
            }


        }
        InOutUserStatus inOutUserStatus = new InOutUserStatus();
        inOutUserStatus.setInOrOutFlag(1);
        List<InOutUserStatus> inOutUserStatuses = inOutUserStatusService.selectInOutUserStatusList(inOutUserStatus);
        List<GetDayInAndOutInfoUser> getDayInAndOutInfoUsers = new ArrayList<>();
        for (InOutUserStatus in : inOutUserStatuses) {
            GetDayInAndOutInfoUser getDayInAndOutInfoUser = new GetDayInAndOutInfoUser();
            getDayInAndOutInfoUser.setUserName(in.getUsername());
            getDayInAndOutInfoUser.setDeptName(in.getDeptName());
            getDayInAndOutInfoUser.setInTime(DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", in.getInTime()));
            getDayInAndOutInfoUsers.add(getDayInAndOutInfoUser);
        }


        GetAllPersonAndCarDayInAndOutInfoVo getAllPersonAndCarDayInAndOutInfoVo = new GetAllPersonAndCarDayInAndOutInfoVo();
        getAllPersonAndCarDayInAndOutInfoVo.setGetDayInAndOutInfoByAreaPersons(getDayInAndOutInfoByAreaPeople);
        getAllPersonAndCarDayInAndOutInfoVo.setGetDayInAndOutInfoByAreaCars(getDayInAndOutInfoByAreaCars);
        getAllPersonAndCarDayInAndOutInfoVo.setGetDayInAndOutInfoUsers(getDayInAndOutInfoUsers);

        return getAllPersonAndCarDayInAndOutInfoVo;
    }

    private static String optEventString(String before) {
        StringBuffer oEBuffer = new StringBuffer();
        Set<String> allOprEnvts = new HashSet<>();
        String[] oprEnvts = before.split(";");
        for (String oprEnvt : oprEnvts) {
            allOprEnvts.add(oprEnvt.split("\\.")[1].split("-")[0]);
        }
        for (String o : allOprEnvts) {
            oEBuffer.append(o).append(";");
        }
        String oE = oEBuffer.toString();
        return oE.substring(0, oE.length());
    }
}

package com.ruoyi.base.taskHandler;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.domain.EqPing;
import com.ruoyi.base.domain.EqStateRecord;
import com.ruoyi.base.enums.EqDeviceStatus;
import com.ruoyi.base.enums.EqType;
import com.ruoyi.base.service.IEqDeviceService;
import com.ruoyi.base.service.IEqPingService;
import com.ruoyi.base.service.IEqStateRecordService;
import com.ruoyi.base.utils.ZJFConverter;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.taskHandler.ActionTimerTask;
import com.ruoyi.common.taskHandler.PingConfig;
import com.ruoyi.common.taskHandler.TaskQueueHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component("PingHandler")
public class PingHandler extends TaskQueueHandler {

    private final PingConfig config;
    @Autowired
    private IEqDeviceService eqDeviceService;
    @Autowired
    IEqPingService eqPingService;
    @Autowired
    IEqStateRecordService eqStateRecordService;

    /**
     * 全部设备信息以及检测信息
     * <p>key：心跳检测信息主键, value： a：设备信息，b：检测时间，c：检测次数，d：在线状态</p>
     */
    private static final ConcurrentMap<Long, PingState> equipmentStateMap = new ConcurrentHashMap<>();

    public PingHandler(PingConfig config) {
        super("设备心跳检测模块", LoggerFactory.getLogger(PingHandler.class));
        this.config = config;
    }


    /**
     * 启动
     */
    public void start() {
        if (!config.isEnable())
            return;

        try {
            //初次启动时查询所有的设备信息并添加至集合
            List<EqDevice> eqDeviceList= eqDeviceService.getList();
            eqDeviceList.forEach(this::addEquipment);
        } catch (Exception ex) {
            logger.warn(ZJFConverter.SimToTra("添加设备失败"),
                    ex);
        }

        super.start();
    }

    private void addEquipment(EqDevice vo) {
        EqPing eqPing = eqPingService.getEqPingByEqDevice(vo);
        Long pingId = eqPing.getId();

        EqStateRecord eqStateRecord=eqStateRecordService.selectLastByEqDevice(vo.getEqType(), vo.getEqId());
        Long lastStateRecordStatus = eqStateRecord!=null?
                eqStateRecord.getStatus():null;
        Map<String, PingState> map = new HashMap<>();
        PingState pingState = new PingState();
        pingState.setEqDevice(vo);
        pingState.setLastStateRecordStatus(lastStateRecordStatus);
        pingState.setPingCnt(0);
        equipmentStateMap.put(pingId, pingState);

        //添加心跳检测任务
        super.addTask(pingId,
                false);

        //推送实时信息
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("info",
                "新增设备");
        sendUpdateData(vo.getEqType(),
                vo.getEqId(),
                sendData);

    }

    /**
     * 发送更新数据
     *
     * @param type 设备类型
     * @param key  设备主键
     * @param data 更新的数据
     */
    private void sendUpdateData(String type,
                                Long key,
                                Map<String, Object> data) {
        //异步执行
        CompletableFuture.runAsync(() -> {
            //TODO 推送实时信息

        });
    }


    @Override
    protected void processingTask(Object taskKey) {
        Long taskKey_resolve = Long.valueOf(taskKey.toString());

        PingState data = equipmentStateMap.get(taskKey_resolve);
        EqDevice eqDevice = data.getEqDevice();
        if (data == null) return;

        String equipmentKey = getEquipmentKey(eqDevice.getEqType(),
                eqDevice.getEqId());

        //判断设备是否已被占用
        if (super.containsConcurrentTask(equipmentKey)) {
            //延时添加至队列，等待下次处理
            super.addScheduleTask(new ActionTimerTask<>(super::addTask,
                            taskKey),
                    500L);
            return;
        }

        //异步执行
        super.putConcurrentTask(equipmentKey,
                () -> this.handlerTask(taskKey_resolve,
                        equipmentKey),
                x -> super.removeConcurrentTask(equipmentKey));
    }

    /**
     * 获取设备Key
     *
     * @param type 设备类型
     * @param key  设备主键
     */
    private String getEquipmentKey(String type,
                                   Long key) {
        return String.format("%s_%s",
                type,
                key);
    }

    /**
     * 异步处理子任务
     *
     * @param taskKey      任务Key
     * @param equipmentKey 设备Key
     */
    private void handlerTask(Long taskKey,
                             String equipmentKey) {
        PingState data = equipmentStateMap.get(taskKey);
        if (data == null) return;
        EqDevice eqDevice = data.getEqDevice();

        try {
           /* //获取设备最后更新时间
            Date lastUpdateTime = deviceService.lastUpdateTime(data.a.getType(),
                    data.a.getKey(),
                    false);

            if (lastUpdateTime == null) {
                //设备信息已被移除
                equipmentStateMap.remove(taskKey);
                return;
            } else if (!lastUpdateTime.equals(data.a.getUpdateTime()))
                //设备信息已更新
                update(data.a);*/

            //检测结果
            boolean flag;

            switch (eqDevice.getEqType()) {
                case "hik_equipment":
                case "pcl_equipment":
                    flag = eqDeviceService.getPingStatus(eqDevice.getIp());
                    break;
                default:
                    flag = false;
                    break;
            }

            //记录检测时间
            Date pingTime = new Date();
            eqPingService.pinged(taskKey,pingTime,flag,data);
            data.setLastStateRecordStatus(flag?EqDeviceStatus.ON_LINE.getCode() : EqDeviceStatus.OFF_LINE.getCode());

        } catch (Exception ex) {
            logger.error(String.format("心跳檢測時發生異常, type: %s，key: %s",
                            eqDevice.getEqType(),
                            eqDevice.getEqId()),
                    ex);

            data.setPingCnt(data.getPingCnt()+1);
            if (data.getPingCnt() > config.getRetry())
                logger.error(String.format("重試次數已達上限, type: %s，key: %s，等待下次處理",
                                eqDevice.getEqType(),
                                eqDevice.getEqId()),
                        ex);
            else {
                //获取或创建心跳检测数据
                EqPing eqPing= eqPingService.getEqPingByEqDevice(eqDevice);
                Long pingId=eqPing.getId();

                equipmentStateMap.remove(taskKey);
                equipmentStateMap.put(pingId, data);

                //添加定时任务等待一段时间后再次尝试
                super.addScheduleTask(new ActionTimerTask<>(this::addTask,
                                pingId),
                        1000L);
                return;
            }
        }

        //添加定时任务等待一段时间后再次检测
        super.addScheduleTask(new ActionTimerTask<>(this::addTask,
                        taskKey),
                config.getInterval() * 1000L);
    }
}

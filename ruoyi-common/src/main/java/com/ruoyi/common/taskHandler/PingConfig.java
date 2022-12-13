package com.ruoyi.common.taskHandler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 设备服务配置
 * <p>心跳检测</p>
 *
 * @author LCTR
 * @date 2022-10-10
 */
@Component("PingConfig")
@ConfigurationProperties(prefix = "equipment.ping")
public class PingConfig {
    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 线程池大小
     */
    private int threadPoolSize;

    /**
     * 检测时间间隔(s)
     */
    private int interval;

    /**
     * 检测结果有效时长(s)
     * <p>超过此时长则判定设备已离线</p>
     */
    private int effective;

    /**
     * 单次重试次数
     * <p>检测失败时在短时间内重试的次数，</p>
     * <p>如果重试依然失败，且次数达到了上限，那么将会间隔一段时间（interval）后再次检测。</p>
     */
    private int retry;

    /**
     * 是否启用
     */
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * 线程池大小
     */
    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    /**
     * 检测时间间隔(s)
     */
    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 检测结果有效时长(s)
     * <p>超过此时长则判定设备已离线</p>
     */
    public int getEffective() {
        return effective;
    }

    public void setEffective(int effective) {
        this.effective = effective;
    }

    /**
     * 单次重试次数
     */
    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }
}

package com.ruoyi.common.taskHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * 定时任务
 *
 * @author LCTR
 * @date 2022-08-01
 */
public class ActionTimerTask<T>
        extends TimerTask {
    /**
     * @param action    方法
     * @param parameter 参数
     */
    public ActionTimerTask(Consumer<T> action,
                           T parameter) {
        this.action = action;
        this.parameter = parameter;
    }

    /**
     * 方法
     */
    private final Consumer<T> action;

    /**
     * 参数
     */
    private final T parameter;

    /**
     * 日志组件
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 运行
     */
    @Override
    public void run() {
        try {
            action.accept(parameter);
        } catch (Exception ex) {
            logger.error("執行定時任務時發生異常",
                         ex);
        }
    }
}

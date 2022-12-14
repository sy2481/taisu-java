package com.ruoyi.web.core.config;

import com.ruoyi.base.taskHandler.PingHandler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 运行服务
 *
 * @author LCTR
 * @date 2022-10-09
 */
@Component
@Order(value = 1)
public class RunService
        implements ApplicationRunner {
    public RunService(PingHandler pingHandler) {
        this.pingHandler = pingHandler;
    }

    private final PingHandler pingHandler;

    /**
     * 运行各个服务
     *
     * @param args 参数
     */
    @Override
    public void run(ApplicationArguments args)
            throws
            Exception {
        pingHandler.start();
    }
}

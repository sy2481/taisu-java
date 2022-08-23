package com.ruoyi.base.domain;

public class SendPlcCommandParam {
    /**
     * ip
     */
    private String ip;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 指令
     */
    private String command;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

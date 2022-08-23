package com.ruoyi.base.domain;

import lombok.Data;

@Data
public class CommandType {

    public String openCloseMode;
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOpenCloseMode() {
        return openCloseMode;
    }

    public void setOpenCloseMode(String openCloseMode) {
        this.openCloseMode = openCloseMode;
    }
}

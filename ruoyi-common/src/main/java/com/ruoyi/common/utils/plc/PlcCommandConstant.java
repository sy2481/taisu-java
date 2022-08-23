package com.ruoyi.common.utils.plc;

public class PlcCommandConstant {
    /**
     * 开门指令
     * 必须先下发关门指令，然后再下发开门指令
     */
//    public static final String OPEN_DOOR_COMMAND = "02FF0A00%s000000204D010010";
    public static final String OPEN_DOOR_COMMAND = "02FF0A00%s0000204D010010";

    /**
     * 关门指令
     * 复位指令。复位继电器的状态
     */
//    public static final String CLOSE_DOOR_COMMAND = "02FF0A00%s000000204D010000";
    public static final String CLOSE_DOOR_COMMAND = "02FF0A00%s0000204D010000";
}

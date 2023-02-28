package com.ruoyi.base.domain;


import java.io.Serializable;

/**
 * 危化通道逻辑相关结构体
 */
public class HCPassageway {
    /**
     * 是否开门 参数结构体
     */
    public static class HCPassagewayParamBody implements Serializable{
        private String deviceIp;
        /**
         * 唯一標識內容 ->車牌號/身份證
         */
        private String content;
        /**
         * 車牌號/身份證
         */
        private String type;

        public String getDeviceIp() {
            return deviceIp;
        }

        public void setDeviceIp(String deviceIp) {
            this.deviceIp = deviceIp;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    /**
     * 是否开门 返回结构体
     */
    public static class HCPassagewayResultBody{

        /**
         * 是否能夠找到該車或人
         */
        private Boolean find;
        /**
         * 是否开门
         */
        private Boolean openDoor;

        /**
         * 提示内容
         */
        private String msg;

        /**
         * 車牌號
         */
        private String carPlate;
        /**
         * 歷史車牌號(模糊匹配專用)
         */
        private String carPlateHistory;
        /**
         * 司機身份證
         */
        private String idCard;
        /**
         * 押運員身份證
         */
        private String idCard2;
        /**
         * 設備IP集合(,分割  ,左側為人臉機  右側為抓拍機)
         */
        private String DeviceIps;


        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCarPlate() {
            return carPlate;
        }

        public void setCarPlate(String carPlate) {
            this.carPlate = carPlate;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCard2() {
            return idCard2;
        }

        public void setIdCard2(String idCard2) {
            this.idCard2 = idCard2;
        }

        public String getCarPlateHistory() {
            return carPlateHistory;
        }

        public void setCarPlateHistory(String carPlateHistory) {
            this.carPlateHistory = carPlateHistory;
        }

        public String getDeviceIps() {
            return DeviceIps;
        }

        public void setDeviceIps(String deviceIps) {
            DeviceIps = deviceIps;
        }

        public Boolean getFind() {
            return find;
        }

        public void setFind(Boolean find) {
            this.find = find;
        }

        public Boolean getOpenDoor() {
            return openDoor;
        }

        public void setOpenDoor(Boolean openDoor) {
            this.openDoor = openDoor;
        }
    }

}

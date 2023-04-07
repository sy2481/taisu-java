package com.ruoyi.common.utils.plc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.Thread.sleep;

public class PlcSocket {
    public enum CODE_TYPE {
        ASCII, //普通文本发送
        HEX    //十六进制发送
    }

    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 数据长度（对于定长值为0）
     */
    private int dataLength;
    /**
     * 编码方式
     */
    private String codeType;
    /**
     * 等待时间
     */
    private static final int DEFAULT_RANGE_FOR_SLEEP = 50;

    /**
     * 超时时间
     */
    private static final int DEFAULT_RANGE_TIME_OUT = 1000 * 60;

    /**
     * socket
     */
    private Socket socket = null;

    OutputStream os = null;
    OutputStreamWriter opsw = null;
    BufferedWriter bw = null;
    InputStream inputStream = null;

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(PlcSocket.class);

    public PlcSocket(String ip, int port, int dataLength, String codeType) {
        this.ip = ip;
        this.port = port;
        this.dataLength = dataLength;
        this.codeType = codeType;

        try {
            Integer bindPort = changeIPToBindPort(ip);
            //修改方法 替换socket绑定端口
            socket = new Socket();
            socket.setSoLinger(true, 0);
//            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(bindPort));
            socket.connect(new InetSocketAddress(ip, port));
            //socket = new Socket(ip, port);
            socket.setKeepAlive(true);
        } catch (Exception e) {
            logger.error("socket连接失败", ip + "--->" + port);
        }

    }

    public synchronized void close() {
        logger.info("socket close start");
        try {

            if (os != null) {
                os.close();
            }
            if (opsw != null) {
                opsw.close();
            }
            if (bw != null) {
                bw.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {

        }
        logger.info("socket close end");
    }

    public synchronized String sendComm(String command) {

        logger.info("start send message " + command);

        if (socket == null) {
//            int i = 0;
//            while (socket == null && i < 3) {
            try {
                socket = new Socket(ip, port);
                socket.setKeepAlive(true);
            } catch (IOException e) {
                logger.error("socket连接失败", ip + "--->" + port);
            }
            try {
                sleep(DEFAULT_RANGE_FOR_SLEEP);
            } catch (InterruptedException e) {
                logger.error("系统内部错误", e);
            }
//                i++;
//            }
        }

        //超时
        try {
            socket.setSoTimeout(DEFAULT_RANGE_TIME_OUT);
        } catch (SocketException e) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            socket = null;
            return "";
        }

        //向网口发送数据

        try {
            os = socket.getOutputStream();
            if (codeType.equals(CODE_TYPE.HEX.name())) {
                os.write(CrcUtils.hexStringToByte(command));
            } else {
                opsw = new OutputStreamWriter(os);
                bw = new BufferedWriter(opsw);
                bw.write(command);
            }
            os.flush();
        } catch (Exception e) {
            logger.error("向PLC发送命令失败!", e);
            return "";
        }
//            }finally {
//                try {
//                    if(opsw!=null){
//                        opsw.close();
//                    }
//                    if(bw!=null){
//                        bw.close();
//                    }
//                }catch (Exception e){
//
//                }
//
//            }

        //读取数据
        String result = "";

        try {
            sleep(DEFAULT_RANGE_FOR_SLEEP);
            inputStream = socket.getInputStream();
//                if (dataLength == 0) {
//                    //定长有换行符
//            InputStreamReader ipsr = new InputStreamReader(inputStream);
//            BufferedReader br = new BufferedReader(ipsr);
//                               result = br.readLine();
//                } else {
//                    byte[] buffer = new byte[dataLength];
//                    int len = -1;
//                    if ((len = inputStream.read(buffer)) != -1) {
//                        result = CrcUtils.bytesToHexString(buffer);
//                    }
//                }
        } catch (Exception e) {
            logger.error("读取Socket信息失败", e);
        }
//            finally {
//                try {
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                    if(os!=null) {
//                        os.close();
//                    }
//                }catch (Exception e){
//
//                }
//            }
        return result;
    }

    public boolean connect() {
        return true;
    }


    public static void sentNewMessage(String ip, String port, String index) {
        String[] commands = index.split(",");
        if (ip != null && (ip.equals("") || (ip.equals("127.0.0.1")) || commands.length == 0)) {
            return;
        }
        PlcSocket plc = new PlcSocket(ip, 6000, 0, PlcSocket.CODE_TYPE.HEX.name());

        if (commands.length > 0) {
            for (String command : commands) {
                plc.sendComm(command);
//        plc.sendComm("02FF0A0000000000204D010000");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        plc.close();
    }


//        if (ip != null && (ip.equals("") || (ip.equals("127.0.0.1")))) {
//            return;
//        }
//        if (!index.equals("")) {
//            PlcSocket plc = new PlcSocket(ip, 6000, 0, PlcSocket.CODE_TYPE.HEX.name());
//            // byte[] bytes = CrcUtils.hexStringToByte(String.format(PlcCommandConstant.CLOSE_DOOR_COMMAND, index));
//            plc.sendComm(String.format(PlcCommandConstant.CLOSE_DOOR_COMMAND, index));
////        plc.sendComm("02FF0A0000000000204D010000");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////        plc.sendComm("02FF0A0000000000204D010010");
//            //byte[] bytes1 = CrcUtils.hexStringToByte(String.format(PlcCommandConstant.OPEN_DOOR_COMMAND, index));
//            plc.sendComm(String.format(PlcCommandConstant.OPEN_DOOR_COMMAND, index));
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////        plc.sendComm("02FF0A0000000000204D010000");
//            plc.sendComm(String.format(PlcCommandConstant.CLOSE_DOOR_COMMAND, index));
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            plc.close();
//        }


    public static void sentMessage(String ip, String port, String index) {
        if (ip != null && (ip.equals("") || (ip.equals("127.0.0.1")))) {
            return;
        }
        if (!index.equals("")) {
            PlcSocket plc = new PlcSocket(ip, 6000, 0, PlcSocket.CODE_TYPE.HEX.name());
            // byte[] bytes = CrcUtils.hexStringToByte(String.format(PlcCommandConstant.CLOSE_DOOR_COMMAND, index));
            plc.sendComm(String.format(PlcCommandConstant.CLOSE_DOOR_COMMAND, index));
//        plc.sendComm("02FF0A0000000000204D010000");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        plc.sendComm("02FF0A0000000000204D010010");
            //byte[] bytes1 = CrcUtils.hexStringToByte(String.format(PlcCommandConstant.OPEN_DOOR_COMMAND, index));
            plc.sendComm(String.format(PlcCommandConstant.OPEN_DOOR_COMMAND, index));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        plc.sendComm("02FF0A0000000000204D010000");
            plc.sendComm(String.format(PlcCommandConstant.CLOSE_DOOR_COMMAND, index));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            plc.close();
        }

    }

    public static Integer changeIPToBindPort(String ip) {
        String changePortString = ip.replace("0.", "").replace(".", "").substring(6);
        return Integer.parseInt(changePortString);
    }


    public static void main(String[] args) {
        PlcSocket plc = new PlcSocket("192.168.1.202", 4000, 0, CODE_TYPE.HEX.name());
        System.out.println(plc.sendComm("03FF0A0000000000204D01000000"));

        System.out.println(plc.sendComm("03FF0A0000000000204D01000100"));
        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }
        System.out.println(plc.sendComm("03FF0A0000000000204D01000000"));
//        PlcSocket plc=new PlcSocket("192.168.70.152",6000,0,CODE_TYPE.HEX.name());
//        System.out.println(plc.sendComm("02FF0A0001000000204D010000"));
//        System.out.println(plc.sendComm("02FF0A0001000000204D010010"));
//        System.out.println(plc.sendComm("02FF0A0001000000204D010000"));
        plc.close();
//        try {
//            Thread.sleep(15000);
//        }catch (Exception e){
//
//        }
//        plc=new PlcSocket("192.168.70.152",6000,0,CODE_TYPE.HEX.name());
//        System.out.println(plc.sendComm("02FF0A0000000000204D010000"));
//        System.out.println(plc.sendComm("02FF0A0000000000204D010010"));
//        System.out.println(plc.sendComm("02FF0A0000000000204D010000"));
//        plc.close();
    }
}

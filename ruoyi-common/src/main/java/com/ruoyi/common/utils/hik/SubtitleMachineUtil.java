package com.ruoyi.common.utils.hik;

import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

@UtilityClass
public class SubtitleMachineUtil {
    //    /**
//     * 默认空数组
//     */
//    private static final byte[] DEFAULT_EMPNY_ARRAY = new byte[1];

    /**
     * 通过消息获取指令
     * @param message
     * @return
     */
    public static byte[] getCommand(String message){
        byte[] msg = null;
        try {
            msg = message.getBytes("BIG5");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(21);
        //设置默认格式起始命令
        buffer.put(SubtitleMachineConstant.DEFAULT_SEND_FORMAT);
        buffer.put(msg);
        if(msg.length < 10){
            for (int i = 0; i < 10-msg.length; i++) {
                buffer.put((byte) 0x20);
            }
        }
        buffer.put(SubtitleMachineConstant.DEFAULT_BACK_FUNCTION);

        sign(buffer);
        buffer.put(SubtitleMachineConstant.END);

        byte[] bytes = new byte[buffer.position()];
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            bytes[i] = buffer.get();
        }
        return bytes;

    }



    /**
     * 加上校验
     * @param buffer
     */
    public static void sign(ByteBuffer buffer){
        int position = buffer.position();
        buffer.position(1);
        int check = 0;
        for (int i = 1; i < position ; i++) {
            check = check ^ ubyte(buffer.get());
        }
        buffer.put((byte)check);
    }


    /**
     * 把byte转换为无符号的int
     * @param data
     * @return
     */
    public static int  ubyte(byte data){
        if(data >=0 )
            return data;
        return 128+data+128;
    }
}

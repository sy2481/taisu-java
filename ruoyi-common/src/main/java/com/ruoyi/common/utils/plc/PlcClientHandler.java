package com.ruoyi.common.utils.plc;

import com.ruoyi.common.utils.hik.CrcUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PlcClientHandler extends ChannelInboundHandlerAdapter {
    private plcClientUtils plcClient;

    public PlcClientHandler(plcClientUtils client) {
        this.plcClient = client;
    }
    private final static Logger logger = LoggerFactory.getLogger(plcClientUtils.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String ipAddress = getIpAddress(ctx);
        ByteBuf buf = (ByteBuf) msg;
        logger.info(ipAddress + ":" + CrcUtils.bytesToHexString(buf));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            logger.warn("exceptionCaught:客户端[{}]和远程断开连接", ctx.channel().localAddress());
        } else {
            logger.error(cause.getMessage());
        }
        ctx.pipeline().remove(this);
        reconnectionAsync(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("channelInactive:{}", ctx.channel().localAddress());
        ctx.channel().close();
        String ipAddress = this.getIpAddress(ctx);
        plcClient.removeClientChannelFuture(ipAddress);
        reconnectionAsync(ctx);
    }


    private void reconnectionAsync(ChannelHandlerContext ctx) {
        logger.info("5s之后重新建立连接");
        String ipAddress =  this.getIpAddress(ctx);
        Integer port =  this.getPort(ctx);

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                plcClient.connectAsync(ipAddress,port);
            }
        }, 5, TimeUnit.SECONDS);
    }


    /**
     * 通过ip地址获取key
     * @param ctx
     * @return
     */
    private String getIpAddress(ChannelHandlerContext ctx){
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return socketAddress.getAddress().getHostAddress();
    }

    /**
     * 通过ip地址获取key
     * @param ctx
     * @return
     */
    private Integer getPort(ChannelHandlerContext ctx){
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return socketAddress.getPort();
//        return null;
    }

    /**
     * 通过ip地址获取key
     * @param ctx
     * @return
     */
    private String getLocalAddress(ChannelHandlerContext ctx){
        String address = ctx.channel().localAddress().toString();
        return address.substring(1, address.lastIndexOf(":"));
    }
}

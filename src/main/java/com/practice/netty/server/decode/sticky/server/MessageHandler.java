package com.practice.netty.server.decode.sticky.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf msgBuf = (ByteBuf) msg;
        int readableBytes = msgBuf.readableBytes();
        // 1. byte array
        byte[] bytes = new byte[readableBytes];
        msgBuf.readBytes(bytes);
        String msgStr = new String(bytes);
        // 2. bytebuf
//        ByteBuf readBuf =  msgBuf.readBytes(readableBytes);
//        String msgStr = new String(readBuf.array());
        System.out.println(msgStr);
    }
}

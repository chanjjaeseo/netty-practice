package com.practice.netty.server.decode.sticky.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SendMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0; i < 100; i++) {
            ByteBuf sendMsg = Unpooled.copiedBuffer("Hello netty".getBytes());
            ctx.writeAndFlush(sendMsg);
        }
    }

}

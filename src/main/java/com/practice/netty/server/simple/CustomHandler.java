package com.practice.netty.server.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext context, HttpObject httpObject) throws Exception {

        Channel channel = context.channel();

        System.out.println("remote address:" + channel.remoteAddress());

        ByteBuf content = Unpooled.copiedBuffer("Hello world", CharsetUtil.UTF_8);

        // 构建返回的响应对象
        FullHttpResponse fullHttpResponse =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK , content);

        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");

        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        // 把响应写入客户端
        context.writeAndFlush(fullHttpResponse);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 处于活跃状态 ...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 处于不活跃状态 ... ");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 被注册 ...");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 被注销 ... ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel 正在read ... ");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel read完成 ... ");
    }

}

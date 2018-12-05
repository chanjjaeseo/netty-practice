package com.practice.netty.server.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


public class SimpleServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {

        ChannelPipeline pipeline = socketChannel.pipeline();

        // 编解码
        pipeline.addLast("HttpServerCodeC", new HttpServerCodec());

        // 处理客户端连接
        pipeline.addLast("CustomHandler", new CustomHandler());
    }

}

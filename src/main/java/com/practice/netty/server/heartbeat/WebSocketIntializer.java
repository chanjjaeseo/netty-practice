package com.practice.netty.server.heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


public class WebSocketIntializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 编解码器
        pipeline.addLast(new HttpServerCodec());
        // 写大数据流支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage 进行聚合
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // websocket 指定访问路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 60秒未发生读写操作，则触发Idle事件
        pipeline.addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS));
        // 负责捕获IdelStateHandler 发出的事件
        pipeline.addLast(new HeartBeatHandler());
        // 指定处理器
        pipeline.addLast(new MessageHandler());
    }

}

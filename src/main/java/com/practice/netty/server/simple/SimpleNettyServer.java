package com.practice.netty.server.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleNettyServer {

    private static EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();

    private static int port = 8888;

    public static void run() throws InterruptedException {
        ServerBootstrap server = new ServerBootstrap();

        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleServerInitializer());

        // 同步绑定端口同时启动server
        ChannelFuture channelFuture = server.bind(port).sync();

        // 监听关闭的channel
        channelFuture.channel().closeFuture().sync();
    }

    public static void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        try{
            run();
        } finally {
            close();
        }
    }


}

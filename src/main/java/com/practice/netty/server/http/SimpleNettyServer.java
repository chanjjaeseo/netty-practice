package com.practice.netty.server.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleNettyServer {

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private static int port = 8888;

    public void run() throws InterruptedException {
        ServerBootstrap server = new ServerBootstrap();

        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleServerInitializer());

        // 同步绑定端口同时启动server
        ChannelFuture channelFuture = server.bind(port).sync();

        // 监听关闭的channel
        channelFuture.channel().closeFuture().sync();
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleNettyServer server = new SimpleNettyServer();
        try{
            server.run();
        } finally {
            server.close();
        }
    }


}

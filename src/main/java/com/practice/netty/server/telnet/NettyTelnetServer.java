package com.practice.netty.server.telnet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyTelnetServer {

    // 指定端口号
    private static final int PORT = 8888;
    private static ServerBootstrap serverBootstrap;

    private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();

    public static void run() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();

        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTenetInitializer());
        Channel channel = serverBootstrap.bind(PORT).sync().channel();

        channel.closeFuture().sync();

    }

    public static void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        run();
        // sleep 3 minutes
        Thread.sleep(60 * 3);
        close();
    }

}

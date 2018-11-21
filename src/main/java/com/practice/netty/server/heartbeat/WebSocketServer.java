package com.practice.netty.server.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer {

    private final static int port = 9080;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final NioEventLoopGroup boss = new NioEventLoopGroup();
    private final NioEventLoopGroup worker = new NioEventLoopGroup();

    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketIntializer())
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future;
        try{
            future = bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            logger.error("error: bind port", e);
            return;
        }
        try{
            future.channel().closeFuture().sync();
        } catch (InterruptedException e){
            logger.error("error: close listener", e);
        }
    }

    public void shutdown(){
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    public static void main(String[] args) {
        WebSocketServer socketServer = new WebSocketServer();
        try{
            socketServer.start();
        } finally {
            socketServer.shutdown();
        }
    }

}

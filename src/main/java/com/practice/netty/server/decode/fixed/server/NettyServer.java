package com.practice.netty.server.decode.fixed.server;

import com.practice.netty.server.decode.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class NettyServer {

    private NioEventLoopGroup boss = new NioEventLoopGroup();

    private NioEventLoopGroup worker = new NioEventLoopGroup();

    public void initServer() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
//                        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                        new FixedLengthFrameDecoder();
//                        new DelimiterBasedFrameDecoder();
                        channel.pipeline().addLast(new MessageHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.bind(8888).addListener(
                new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("服务器已启动");
                    }
                }
        );
        channelFuture.channel().closeFuture().sync();
    }

    public void closeServer() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        try {
            nettyServer.initServer();
        } finally {
            nettyServer.closeServer();
        }
    }

}

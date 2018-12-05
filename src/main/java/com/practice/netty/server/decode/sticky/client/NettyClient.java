package com.practice.netty.server.decode.sticky.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private NioEventLoopGroup boss = new NioEventLoopGroup();


    public void initClient() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(boss)
                .remoteAddress("127.0.0.1", 8888)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new SendMessageHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect().sync().addListener(
                new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("客户端已启动");
                    }
                }
        );
    }

    public void closeClient() {
        boss.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient();
        try {
            client.initClient();
        } finally {
            client.closeClient();
        }
    }

}

package com.practice.netty.server.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

public class Server {

    public void run(){
        // boss组：负责接收客户端连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // worker组：负责对接收内容进行操作
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)// 指定eventLoop 组
                    .channel(NioServerSocketChannel.class) //指定Channel类型
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue") // 绑定属性
                    .handler(new ServerHandler())
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) {
                                    socketChannel.pipeline().addLast(new ServerHandler());
                                }
                            }
                    );
            // 绑定端口
            ChannelFuture f = bootstrap.bind(8889).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            //
        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}

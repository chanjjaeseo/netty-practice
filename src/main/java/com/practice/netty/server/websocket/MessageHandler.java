package com.practice.netty.server.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup clientGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        clientGroup.add(channel);
        logger.info("接收连接 channelId:" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        clientGroup.remove(ctx.channel());
        logger.info("断开连接 channelId:" + ctx.channel().id().asLongText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();
        logger.info("recived message :" + content);

        for(Channel channel : clientGroup) {
            channel.writeAndFlush(
                    new TextWebSocketFrame("服务器在 ["
                            + LocalDateTime.now()
                            + "]接收到消息: " + content));
        }
        // 与for循环遍历方式效果一样
//        clientGroup.writeAndFlush(
//                new TextWebSocketFrame("服务器在 ["
//                        + LocalDateTime.now()
//                        + "]接收到消息: " + content));
    }
}

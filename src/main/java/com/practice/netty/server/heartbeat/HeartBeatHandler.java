package com.practice.netty.server.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当空闲时间过长IdelStateHandler将触发事件
 * 我们只需要发送一条信息再次确认客户端是否在线
 * 如果未发送成功证明客户端已经离线，回调事件将关闭channel
 */
public final class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEART_BEAT", CharsetUtil.UTF_8));

    private static final TextWebSocketFrame text = new TextWebSocketFrame(HEARTBEAT_SEQUENCE);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 在这里发送消息
            // listener检测释放发送成功，如果未发送成功证明客户端已离线，则关闭channel
            logger.info("向客户端发送心跳确认, channelId = " + ctx.channel().id().asLongText());
            HEARTBEAT_SEQUENCE.readerIndex(0);
//            TextWebSocketFrame text = new TextWebSocketFrame(HEARTBEAT_SEQUENCE.duplicate());
            ctx.writeAndFlush(text).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}

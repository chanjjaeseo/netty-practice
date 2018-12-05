package com.practice.netty.server.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SendMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        String messageContent = "\"Hello netty\\r\\n\"";
        String messageContent =
                "即返回，所以我们需要一种用于在之后的某个时间点确定其结果的方法。为此，Netty 提供了\n" +
                "ChannelFuture 接口，其 addListener()方法注册了一个 ChannelFutureListener，以\n" +
                "便在某个操作完成时（无论是否成功）得到通知。\n" +
                "关于 ChannelFuture 的更多讨论 可以将 ChannelFuture 看作是将来要执行的操作的结果的\n" +
                "占位符。它究竟什么时候被执行则可能取决于若干的因素，因此不可能准确地预测，但是可以肯\n" +
                "定的是它将会被执行。此外，所有属于同一个 Channel 的操作都被保证其将以它们被调用的顺序\n" +
                "被执行。\n" +
                "我们将在第 7 章中深入地讨论 EventLoop 和 EventLoopGroup。\n" +
                "3.2 ChannelHandler 和 ChannelPipeline\n" +
                "现在，我们将更加细致地看一看那些管理数据流以及执行应用程序处理逻辑的组件。\n" +
                "3.2.1 ChannelHandler 接口\n" +
                "从应用程序开发人员的角度来看，Netty 的主要组件是 ChannelHandler，它充当了所有\n" +
                "处理入站和出站数据的应用程序逻辑的容器。这是可行的，因为 ChannelHandler 的方法是\n" +
                "由网络事件（其中术语“事件”的使用非常广泛）触发的。事实上，ChannelHandler 可专\n" +
                "门用于几乎任何类型的动作，例如将数据从一种格式转换为另外一种格式，或者处理转换过程\n" +
                "中所抛出的异常。\n" +
                "举例来说，ChannelInboundHandler 是一个你将会经常实现的子接口。这种类型的\n" +
                "ChannelHandler 接收入站事件和数据，这些数据随后将会被你的应用程序的业务逻辑所处\n" +
                "理。当你要给连接的客户端发送响应时，也可以从 ChannelInboundHandler 冲刷数据。你\n" +
                "的应用程序的业务逻辑通常驻留在一个或者多个 ChannelInboundHandler 中。";
        messageContent += messageContent;
        messageContent += messageContent;
        messageContent += messageContent;
        for(int i=0; i < 1; i++) {
            ByteBuf sendMsg = Unpooled.copiedBuffer(messageContent.getBytes());
            ctx.writeAndFlush(sendMsg);
        }
    }

}

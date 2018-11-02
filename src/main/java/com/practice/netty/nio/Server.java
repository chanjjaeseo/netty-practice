package com.practice.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private final String HOST = "127.0.0.1";

    private final int PORT = 8080;

    private Selector selector;

    // heap buffer  && direct buffer
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    public void initServer(){
        try {

            selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            // 设置为非阻塞的(select 只接收no-blocking的channel)
            serverSocketChannel.configureBlocking(false);

            // 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(HOST, PORT));

            // 注册到selector上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true){
                // 阻塞：可用通道数
                int n = selector.select();

                if(n > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();

                    Iterator<SelectionKey> selectionKeyIterator = keys.iterator();

                    while (selectionKeyIterator.hasNext()) {

                       SelectionKey key =  selectionKeyIterator.next();

                       if (key.isAcceptable()) {
                            handlerAccepte(key);
                       }

                       // 如果数据很大, 一个channel会有多次isReadable事件
                       if (key.isReadable()) {
                            handlerRead(key);
                       }
                    }
                    // 处理完毕从selector中移除
                    selectionKeyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void handlerRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        while (true) {

            if (socketChannel.read(buffer) == 0) {
                break;
            }

            // 切换成读模式
            buffer.flip();

            System.out.println(new String(buffer.array()));

            // 清掉数据
            buffer.clear();
        }

        socketChannel.close();
    }

    private void handlerAccepte(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        // maybe null
        // 如果不accept, 它一直处于 isAcceptable状态
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 不阻塞
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.initServer();
    }

}

package com.practice.netty.bio.blocking;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("初始化服务端Socket失败");
        }
    }

    public void start() {
        new Thread(
                () -> { doStart(); }
        ).start();
    }

    private void doStart() {
        while (true) {
            try {
                // 接收客户端连接
                Socket client = serverSocket.accept();
                // 交给另一个线程处理
                executor.execute(new ClientHandler(client));
            } catch (IOException e) {
                System.out.println("接收客户端连接失败");
            }
        }
    }

    public static void main(String[] args) {
        Server server =  new Server(8090);
        server.start();
    }

}

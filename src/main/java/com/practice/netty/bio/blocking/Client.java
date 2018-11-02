package com.practice.netty.bio.blocking;


import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8889;

    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        new Thread(
                ()->{
                    System.out.println("客户端启动成功");

                    while (true) {
                        String message = "hello world!";
                        try {
                            // 向服务器发送消息
                            socket.getOutputStream().write(message.getBytes());
                            socket.getOutputStream().flush();
                        } catch (IOException e) {
                            System.out.println("写入数据失败!");
                        }
                        // 暂停5s
                        sleep();
                    }
                }
        ).start();

    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }


}

package com.practice.netty.bio.trueblocking;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(8080);
            while (true){
                // 阻塞
                Socket client =  socket.accept();
                // 处理客户端连接
                // 阻塞: 不能再接收新的客户端连接
                handlerClient(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handlerClient(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buff = new byte[1024];
        int len;
        while ((len = inputStream.read(buff)) != -1){
            String message = buff.toString();
            System.out.println(message);
            socket.getOutputStream().write(buff);
        }
    }

}

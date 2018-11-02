package com.practice.netty.bio.blocking;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] buff = new byte[1024];
            while (inputStream.read(buff) != -1){
                String message = new String(buff);
                System.out.println(message);
                socket.getOutputStream().write(buff);
            }
        } catch (Exception e){
            System.out.println("error!");
        }
    }
}

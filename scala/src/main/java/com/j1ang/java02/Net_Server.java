package com.j1ang.java02;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author J1aNgis0coo1
 * @create 2020-05-19 19:30
 */
public class Net_Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //step1: 创建服务器  ★ 包含占用的端口号  ★ 服务器会阻塞等待客户端的访问
        ServerSocket serverSocket = new ServerSocket(9999);

        //step2: 接收客户端的请求       相当于已经建立连接，是Client中的
        Socket client = serverSocket.accept();

        //反序列化
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        User user = (User) ois.readObject();


        System.out.println("服务器已接收到数据为" + user.name);

    }
}

package com.j1ang.java02;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author J1aNgis0coo1
 * @create 2020-05-19 19:34
 */
public class Net_Client {

    public static void main(String[] args) throws IOException {

        //step1: 创建客户端 Socket   ★ 连接地址：本机 localhost ★ 连接端口号：server端的9999
        Socket socket = new Socket("localhost",9999);

        //step2：向server端传入数据 是OutputStream

        User user = new User();
        user.name = "j1ang";

        //对对象进行序列化

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(user); //放入对象

        System.out.println("客户端发送数据为j1ang");
        //step3: 传输完数据后关闭流
        socket.close();
    }
}

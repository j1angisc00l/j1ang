package com.j1ang.summer_framework

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.{ServerSocket, Socket}

import com.j1ang.summer_framework.core.TApplication

/**
 * @author J1aNgis0coo1
 * @create 2020-05-24 19:36
 */
object server extends TApplication {

  def main(args: Array[String]): Unit = {
    start("serverSocket"){

      val serverSocket : ServerSocket = envData.asInstanceOf[ServerSocket]
      //因为envData是Any且已知是serversocket类型，所以给他instanceof

      //让服务器来不断接收请求
      while(true){
        var Client : Socket = serverSocket.accept()

        //拿到客户端后启动线程
        new Thread(
          new Runnable {
            override def run(): Unit = {

              //获取数据 in
              val inObject = new ObjectInputStream(Client.getInputStream)
              val task = inObject.readObject().asInstanceOf[Task]  //通过传一个类过去比对象好

              Client.shutdownInput()

              //输入数据 out
              val outObject = new ObjectOutputStream(Client.getOutputStream)

              //输入到逻辑里去获得对象
              val result = task.compute()
              outObject.writeObject(result)

              Client.shutdownOutput()

              if(!Client.isClosed){
                Client.close()
              }
              Client = null  //明确的将对象的底层释放掉，更便于GC的回收
            }
          }
        ).start()
      }
    }
  }

}

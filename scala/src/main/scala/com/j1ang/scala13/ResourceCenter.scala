package com.j1ang.scala13

import java.io.ObjectInputStream
import java.net.{ServerSocket, Socket}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-01 22:45
 */
object ResourceCenter {

  def main(args: Array[String]): Unit = {

    //创建接收资源请求的服务
    val serverSocket = new ServerSocket(9999)
    println("资源中心已启动...")

    while (true){
      //接收来自Driver转过来的数据信息
      val driverRef: Socket = serverSocket.accept()

      //因为可能会有多个请求，所以采用线程的形式
      new Thread(
        new Runnable {
          override def run(): Unit = {
            //创建接收流接收来自Driver转过来的数据信息
            val driverRefIn = new ObjectInputStream(driverRef.getInputStream)
            //接收信息
            val driverMessage: Message = driverRefIn.readObject().asInstanceOf[Message]
            //因为Message传过来的是一串字符串
            // Message(s"executorCount=$executorCount&driverHost=$myHost&driverPort=$myPort")
            // 所以对字符串进行处理
            // executorCount
            // 5
            // driverHost
            // localhost
            // driverPort
            // 1234
            // 分解字符串，应该同时使用 =，&
            // 使用正则表达式分解字符串
            val datas: Array[String] = driverMessage.content.split("=|&")
            //拿到执行器的个数
            val executorCount: Int = datas(1).toInt
            val driverHost: String = datas(3)
            val driverPort: Int = datas(5).toInt

            //知道了执行器的个数，便可以来创建执行器
            for(i <- 1 to executorCount){
              val executor = new Executor(i,driverHost,driverPort)
              //启动执行器
              executor.start()
            }

          }
        }
      ).start()


    }

  }

}

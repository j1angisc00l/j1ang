package com.j1ang.scala13

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.{ServerSocket, Socket}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-01 22:47
 */
object Driver {

  def main(args: Array[String]): Unit = {
    // TODO 第一阶段
    //数据准备
    val executorCount = 5
    val myHost = "localhost"
    val myPort = 1234

    //连接资源中心
    val rcSocket = new Socket("localhost",9999)
    println("资源中心已连接，数据准备中...")

    //传输流的建立
    val outputrcStream = new ObjectOutputStream(rcSocket.getOutputStream)

    //传送资源
    outputrcStream.writeObject(Message(s"executorCount=$executorCount&driverHost=$myHost&driverPort=$myPort"))

    //刷新一下就发过去了
    outputrcStream.flush()

    //此时就可以关闭和资源中心的连接了
    rcSocket.close()

    // TODO 接收Executor端的数据
    // 线程安全问题 sum = sum+ result 是不安全的
    //因为是并发的，我们拿到的sum很有可能没有变化，然后又加上别的数（对共享数据的修改是会导致不安全的）
    //思路：创建一个来储存各个线程计算的结果，且轮询的访问各个线程是否已经产生了结果，如果有一个还没有值，则继续等待，过一会再重新轮询
    //var sum = 0;
    val results: Array[Int] = Array.fill(executorCount)(-1) //填充executorCount个数的-1

    //TODO 第二阶段
    //接收Executor端的数据
    val receiver = new ServerSocket(myPort)
    //统计结果的线程，只要线程有一个-1，就代表仍有线程没执行完
    new Thread(
      new Runnable {
        override def run(): Unit = {
          var flag = true
          while (flag){
            if(results.contains(-1)){
              Thread.sleep(100)
            }else{
              //所有线程执行完毕
              println("计算完毕，结果为" + results.sum)
              flag = false
              System.exit(0)
            }
          }

        }
      }
    ).start()
    while (true){
      val executorRef: Socket = receiver.accept()
      //println("执行器已经连接上了")
      new Thread(
        new Runnable {
          override def run(): Unit = {
            //向executor发送计算任务
              val executorRefOut = new ObjectOutputStream(executorRef.getOutputStream)
            //创建需要传输的任务
            val task = new Task()
            task.logic= _*2
            executorRefOut.writeObject(task)
            executorRefOut.flush()
            executorRef.shutdownOutput()

            //获取Executor端的计算结果
            val executorRefIn = new ObjectInputStream(executorRef.getInputStream)

            val result: Message = executorRefIn.readObject().asInstanceOf[Message]
            val datas: Array[String] = result.content.split("=|&")
            //Message(s"executorId=${i}&result=${i1}"

            //println("获取计算结果" + result)
            //results()=result

            //因为数组从0开始 索引要减一        存在数组
            results(datas(1).toInt - 1)=datas(3).toInt
          }
        }
      ).start()
    }


  }

}

package com.j1ang.scala13

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket

/**
 * @author J1aNgis0coo1
 * @create 2020-06-01 22:48
 */
case class Executor(i:Int,host:String,port:Int) {
  // TODO 第一阶段
  //启动执行器
  def start(): Unit ={
    println(s"执行器$i,开始启动")

    println(s"执行器$i,开始链接驱动器")
    val driverRef = new Socket(host,port)

    // TODO 第二阶段
    //接收Driver传过来的计算任务
    val driverRefIn = new ObjectInputStream(driverRef.getInputStream)

    val task: Task = driverRefIn.readObject().asInstanceOf[Task]
    driverRef.shutdownInput()
    //计算结果
    val i1: Int = task.logic(i)

    //将计算结果返回给Driver端,创建流
    val driverRefOut = new ObjectOutputStream(driverRef.getOutputStream)
    //把结果传输过去
    driverRefOut.writeObject(Message(s"executorId=${i}&result=${i1}"))

    driverRefOut.flush()
    driverRef.close()
  }



}

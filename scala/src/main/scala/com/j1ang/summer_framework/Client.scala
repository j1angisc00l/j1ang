package com.j1ang.summer_framework

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket

import com.j1ang.summer_framework.core.TApplication

/**
 * @author J1aNgis0coo1
 * @create 2020-05-24 20:30
 */
object Client extends TApplication {

  def main(args: Array[String]): Unit = {
    start("Socket"){
      val socket : Socket= envData.asInstanceOf[Socket]

      val outObject = new ObjectOutputStream(socket.getOutputStream)

      val task = new Task()
      task.data = 10
      task.logic = _*2

      //传入的函数对象   是函数类   已经序列化了
      // 函数对象
      // Scala中的类默认都是已经序列化

      //向服务器发送  out  -> 传函数过去，序列化
      outObject.writeObject(task)   //传对象
      outObject.flush()
      socket.shutdownOutput()   //单独的关掉out


      //得到服务器传来的结果  in
      val inObject = new ObjectInputStream(socket.getInputStream)
      val result : Int = inObject.readObject().asInstanceOf[Int]

      println("结果是" + result)
      socket.shutdownInput()
    }
  }

}

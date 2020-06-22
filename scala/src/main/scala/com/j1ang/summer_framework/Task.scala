package com.j1ang.summer_framework

/**
 * @author J1aNgis0coo1
 * @create 2020-05-24 22:38
 */
class Task extends Serializable {

  // 因为客户端传入的类型影响了服务器的类型，耦合度高
  var data:Int = 0
  var logic : Int => Int = null

  def compute():Int={
    logic(data)
  }
}

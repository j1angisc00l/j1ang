package com.j1ang.scala13

/**
 * @author J1aNgis0coo1
 * @create 2020-06-01 22:48
 */
class Task extends Serializable {
  var data : Int = 0
  var logic : Int => Int  = null

  def compute() = {
    logic(data)
  }
}

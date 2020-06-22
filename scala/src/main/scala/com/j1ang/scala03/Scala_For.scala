package com.j1ang.scala03

import scala.util.control.Breaks._

/**
 * @author J1aNgis0coo1
 * @create 2020-05-20 19:38
 */
object Scala_For {

  def main(args: Array[String]): Unit = {

    def test(i:Int): Int ={
      i*2
    }

    def test1(i:Int): Any ={
      i + "j1ang"
    }

    def test2(a:Int,b:Int,c:Int) ={
      (a + b)*c
    }

    def test3(s:String): Unit = {
      val strings = s.split(" ") //数据集
      for(word <- strings){

      }

    }
  }
}

package com.j1ang.scala

import scala.io.Source

/**
 * @author J1aNgis0coo1
 * @create 2020-05-19 1:10
 */
object Scala_String {
  def main(args: Array[String]): Unit = {

    //从相对路径来读取文件
    val files : Iterator[String]= Source.fromFile("input/j1ang.txt").getLines()
    while(files.hasNext){
      println(files.next())
    }


  }
}

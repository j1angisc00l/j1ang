package com.j1ang.scala08

import java.time.LocalDate

/**
 * @author J1aNgis0coo1
 * @create 2020-06-21 19:38
 */
  object test_09 {
    def main(args: Array[String]): Unit = {


    val list = List(1,2,3,4)
    list match {
      case a::rest => println(rest) //List(2, 3, 4)
                      println(a)  //1
    }

}
}


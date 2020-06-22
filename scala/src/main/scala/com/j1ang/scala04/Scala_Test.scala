package com.j1ang.scala04

import scala.util.control.Breaks.{break, breakable}

/**
 * @author J1aNgis0coo1
 * @create 2020-05-22 9:01
 */
object Scala_Test {

  def main(args: Array[String]): Unit = {

  def test : String ={
    println("j1ang")
    "j1ang is so cool"
  }

    lazy val slogan = test

    println("==================")
    println("slogan = " + slogan)

  }
}

package com.j1ang.scala07


import scala.collection.{immutable, mutable}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * @author J1aNgis0coo1
 * @create 2020-05-27 20:48
 */
object Test {
  def main(args: Array[String]): Unit = {

    val list1 = List((List("aaa,bbb"), 2), (List("aaa,ddd"), 3))

    val list: List[(List[String], Int)] = list1.map((kv) => {(kv._1.flatMap(_.split(",")).toList,kv._2)})

    val list2: List[List[(String, Int)]] = list.map((kv) => {kv._1.map((k) => (k,kv._2))})

    val flatten: List[(String, Int)] = list2.flatten
    //List((aaa,2), (bbb,2), (aaa,3), (ddd,3))

    val stringToTuples: Map[String, List[(String, Int)]] = flatten.groupBy((k) => k._1)

    val stringToInt: Map[String, Int] = stringToTuples.map((kv)=> {(kv._1,kv._2.map((k) => {k._2}).toList.sum)})

    println(stringToInt)





  }
}

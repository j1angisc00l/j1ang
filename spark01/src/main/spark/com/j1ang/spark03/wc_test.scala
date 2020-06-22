package com.j1ang.spark03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-04 20:15
 */
object wc_test {
  def main(args: Array[String]): Unit = {
    val j1angConf: SparkConf = new SparkConf().setMaster("local").setAppName("j1ang")
    val sc = new SparkContext(j1angConf)

    val rdd: RDD[String] = sc.textFile("input/wc.txt")



    val rdd1: RDD[String] = rdd.flatMap(data => data.split(" "))

//    rdd1.foreach(
//      line => {
//        println(line.mkString(","))
//      }
//    )

//    val map: Map[String, List[String]] = rdd1.collect().toList.groupBy(word => word)
//
//    val stringToInt: Map[String, Int] = map.mapValues(_.size)
//
//    println(stringToInt)

    val rdd2: RDD[(String, Iterable[String])] = rdd1.groupBy(word => word)

    println(rdd2.map(tuple => (tuple._1, tuple._2.size)).collect().mkString("，"))







  }
}

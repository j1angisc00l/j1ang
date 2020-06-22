package com.j1ang.spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-07 15:09
 */
object wc_test04 {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("wc-04")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("input/agent.log")

    val rdd1: RDD[(String, List[(String, Int)])] = rdd.map(list => {
      val arr: Array[String] = list.split(" ")
      ((arr(1), arr(4)), 1)
    }).reduceByKey(_ + _)
      .map(kv => {
        (kv._1._1, (kv._1._2, kv._2))
      }).groupByKey()
      .mapValues(v => v.toList.sortWith(
        (left, right) => {
          left._2 > right._2
        }
      ).take(3))
    rdd1.collect().foreach(println)
  }
}

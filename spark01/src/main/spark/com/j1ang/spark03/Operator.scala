package com.j1ang.spark03

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-05 22:49
 */
object Operator {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("File -RDD")
    val sc = new SparkContext(conf)

    val rdd: RDD[String] = sc.textFile("input/wc.txt")

    val rdd1: RDD[(String, Int)] = rdd.flatMap(_.split(" "))
      .groupBy(word => word)
      .mapValues(_.size)
      .aggregateByKey(0)(
        (x, y) => x + y,
        (x, y) => x + y
      )
    println(rdd1.collect().mkString(","))

  }
}

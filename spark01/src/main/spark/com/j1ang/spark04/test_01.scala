package com.j1ang.spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-07 10:55
 */
object test_01 {
  def main(args: Array[String]): Unit = {

    // Spark 依赖关系
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,6,7,8))


    println(rdd.collect().mkString(","))

  }


}

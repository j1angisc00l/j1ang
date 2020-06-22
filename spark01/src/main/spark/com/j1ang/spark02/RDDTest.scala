package com.j1ang.spark02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-02 21:10
 */
object RDDTest {
  def main(args: Array[String]): Unit = {

    val j1angConf: SparkConf = new SparkConf().setMaster("local").setAppName("j1ang")
    val sc = new SparkContext(j1angConf)

    val fileRDD: RDD[String] = sc.textFile("input/wc*.txt")
    println(fileRDD.collect().mkString(","))

    //sc.parallelize()

    //sc.stop()

  }
}

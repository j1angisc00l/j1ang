package com.j1ang.spark03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-03 20:31
 */
object RDD_Test {
  def main(args: Array[String]): Unit = {

    val j1angSparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("j1ang")
    val sc = new SparkContext(j1angSparkConf)

    val rdd: RDD[String] = sc.textFile("input/apache.log")
    val rdd1: RDD[String] = rdd.map(line => {
      val arrays: Array[String] = line.split(" ")
      arrays(3)
    })

    val rdd2: RDD[String] = rdd1.map(line => {
      val arr2: Array[String] = line.split(":")
      arr2(0)
    })

    val rdd3: RDD[(String, Iterable[String])] = rdd2.groupBy(line => line)

    val rdd4: RDD[(String, Int)] = rdd3.map((date) => (date._1,date._2.size))
    println(rdd4.collect().mkString(","))

    //(17/05/2015,1632),(19/05/2015,2896),(18/05/2015,2893),(20/05/2015,2579)


  }
}

package com.j1ang

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-06 10:59
 */
object Project_01 {
  def main(args: Array[String]): Unit = {
   val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("Project_test01")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("input/agent.log")

    val rdd1: RDD[(String, Iterable[(String, Int)])] = rdd.map(line => {
      val array: Array[String] = line.split(" ")
      ((array(1), array(4)), 1)
    })
      .reduceByKey(_ + _)
      .map(kv => {
        (kv._1._1, (kv._1._2, kv._2))
      })
      .groupByKey()
        .mapValues(iter => {
          iter.toList.sortWith(
            (left,right) =>{
              left._2 > right._2
            }
          ).take(3)
        })
        //.map(kv=>(kv._1,kv._2.toList.sortBy(kv=> -kv._2).take(3)))
      //.sortBy(v => v._2, false)//.map(kv => (kv._1, kv._2.take(3)))  //排序错了   排序整个拉稀！！！！！！！！
    rdd1


    rdd1.foreach(println)

  }
}

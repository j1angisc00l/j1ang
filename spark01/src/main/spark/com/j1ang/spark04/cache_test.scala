package com.j1ang.spark04

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-08 20:45
 */
object cache_test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("File - RDD")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1,2,3,4))

    val mapRDD: RDD[(Int, Int)] = rdd.map( num=>{
      println("map......")
      (num,1)
    } )

    // TODO cache操作在行动算子执行后，会在血缘关系中增加和缓存相关的依赖
    //      cache操作不会切断血缘，一旦发生错误，可以重新执行。
    val cacheRDD: RDD[(Int, Int)] = mapRDD.cache()
    println(cacheRDD.toDebugString)
    println(cacheRDD.collect().mkString(","))
    println(cacheRDD.toDebugString)


    sc.stop()
  }
}

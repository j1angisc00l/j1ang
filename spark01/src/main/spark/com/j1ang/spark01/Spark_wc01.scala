package com.j1ang.spark01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-02 1:14
 */
object Spark_wc01 {

  def main(args: Array[String]): Unit = {
    // TODO 1. 准备Spark环境
    //setMaster: 设置Spark环境的位置
    //setAppname：起名
    val sparkConf = new SparkConf().setMaster("local").setAppName("wordCound")

    // TODO 2. 建立和Spark的连接
    // jdbc : connection
    val sc = new SparkContext(sparkConf)

    // TODO 3. 实现业务操作
    //3.1 读取文件
    //  参数path可以指向单一的文件也可以指向文件目录
    //  RDD : 更适合并行计算的数据模型。
    val fileRdd: RDD[String] = sc.textFile("input")

    //3.2切分单词
    val wordRdd: RDD[String] = fileRdd.flatMap(_.split(" "))

    //3.3分组
    val groupRdd: RDD[(String, Iterable[String])] = wordRdd.groupBy(word => word)

    //3.4聚合
    val mapRdd: RDD[(String, Int)] = groupRdd.map {
      case (word, iter) => {
        (word, iter.size)
      }
    }
    //3.5打印到控制台
    val wcArray: Array[(String, Int)] = mapRdd.collect()
    println(wcArray.mkString(","))

    // TODO 4. 释放连接
    sc.stop()
  }
}

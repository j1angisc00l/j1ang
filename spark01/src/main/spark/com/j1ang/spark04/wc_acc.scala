package com.j1ang.spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, CollectionAccumulator, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * @author J1aNgis0coo1
 * @create 2020-06-08 15:11
 */
object wc_acc {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("wc_acc")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(List("hello scala", "hello", "spark", "scala"))

    //创建累加器
    val acc = new MyWordcountAccumulator

    //注册累加器
    sc.register(acc)

    //使用累加器
    rdd.flatMap(word => word.split(" ")).foreach(
      word => {
        acc.add(word)
      }
    )

    println(acc.value)

    sc.stop()


  }
  // TODO 自定义累加器
  //  1. 继承 AccumulatorV2，定义泛型 [IN, OUT]
  //         IN   : 累加器输入值的类型
  //         OUT : 累加器返回结果的类型
  //  2. 重写方法(6)
  //  3. copyAndReset must return a zero value copy

class MyWordcountAccumulator extends AccumulatorV2[String,mutable.Map[String,Int]]{
  //创建储存结果可变的空集合
  var map = mutable.Map[String,Int]()

  //累加器是否初始化
  override def isZero: Boolean = {
    map.isEmpty
  }
  // TODO 复制累加器
  override def copy(): AccumulatorV2[String,mutable.Map[String,Int]] = {
    new MyWordcountAccumulator
  }
  // TODO 重置累加器
  override def reset(): Unit = {
    map.clear()
  }
  // TODO 向累加器中增加值
  override def add(word: String): Unit = {
    map.update(word,map.getOrElse(word,0) + 1)
  }
  // TODO 合并当前累加器和其他累加器
  //      合并累加器
  override def merge(other: AccumulatorV2[String,mutable.Map[String,Int]]): Unit = {
   val map1 = map
    val map2 = other.value
    map = map1.foldLeft(map2)(
      (map,kv) => {
        map(kv._1) = map.getOrElse(kv._1,0) + kv._2
        map
      }
    )
  }
  // TODO 返回累加器的值（Out）
  override def value: mutable.Map[String, Int] = {map}
}

}

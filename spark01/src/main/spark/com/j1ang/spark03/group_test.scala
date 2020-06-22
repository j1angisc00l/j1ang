package com.j1ang.spark03

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-04 20:58
 */
object group_test {
  def main(args: Array[String]): Unit = {
    /**
    1.算子：combineByKey

     2.形参：传递3个参数
              第一个参数表示的就是将计算的第一个值转换结构
              第二个参数表示分区内的计算规则
              第三个参数表示分区间的计算规则

     3.返回值：

     4.作用：最通用的对key-value型rdd进行聚集操作的聚集函数（aggregation function）
            类似于aggregate()，combineByKey()允许用户返回值的类型与输入不一致。

     5.如何选择：计算时需要将value的格式发生改变，只需要第一个v发生改变结构即可
                如果计算时发现相同的key的value不符合计算规则的格式的话，那么选择combineByKey
     */
    val j1angConf: SparkConf = new SparkConf().setMaster("local").setAppName("j1ang")
    val sc = new SparkContext(j1angConf)

    val rdd = sc.makeRDD(
      List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)),2
    )

    //小练习：将数据List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98))求每个key的平均值
    val result: RDD[(String, (Int, Int))] = rdd.combineByKey(
      v => (v, 1), //(88,1) (95,1)
      (t: (Int, Int), y) => {
        (t._1 + y, t._2 + 1)
      },
      (t1: (Int, Int), t2: (Int, Int)) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )

    val rdd1: RDD[(String, Int)] = result.mapValues(kv => kv._1/kv._2)


    println(rdd1.collect().mkString(","))


  }
}

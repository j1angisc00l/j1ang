package com.sql

import org.apache.spark.{SparkConf}
import org.apache.spark.sql.{Encoder, Encoders, SparkSession, TypedColumn}
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.expressions.Aggregator

/**
 * @author J1aNgis0coo1
 * @create 2020-06-12 23:36
 */
object sql_test02 {
  def main(args: Array[String]): Unit = {
    // TODO 创建环境对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._

    val rdd = spark.sparkContext.makeRDD(List(
      (1, "zhangsan", 30L),
      (2, "lisi", 20L),
      (3, "wangwu", 40L)
    ))

    val df = rdd.toDF("id", "name", "age")
    val ds = df.as[User]

    val f = new MyAvgNameUDAF

    val column: TypedColumn[User, Long] = f.toColumn

    ds.select(column).show()



  }
  //输入数据的类型
  case class User(id:Long,name:String,age:Long)
  //缓存数据的类型
  case class AgeBuffer(var sum : Long,var count : Long)

  //创建UDAF类  要填写 输入类型，缓冲区类型 ，输出类型
  class MyAvgNameUDAF extends Aggregator[User,AgeBuffer,Long]{

    //初始值
    override def zero: AgeBuffer = {
      AgeBuffer(0L,0L)
    }

    //
    override def reduce(b: AgeBuffer, a: User): AgeBuffer = {
      b.sum = b.sum + a.age
      b.count = b.count + 1
      b
    }

    override def merge(b1: AgeBuffer, b2: AgeBuffer): AgeBuffer = {
      b1.sum = b1.sum + b2.sum
      b1.count = b1.count + b2.count
      b1
    }

    override def finish(reduction: AgeBuffer): Long = {
      reduction.sum.toLong / reduction.count.toLong
    }

    override def bufferEncoder: Encoder[AgeBuffer] = {
      Encoders.product
    }

    override def outputEncoder: Encoder[Long] = {
      Encoders.scalaLong
    }
  }



}

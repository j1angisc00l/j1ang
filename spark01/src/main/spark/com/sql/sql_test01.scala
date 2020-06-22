package com.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-12 21:21
 */
object sql_test01 {
  def main(args: Array[String]): Unit = {
    val sqlTest: SparkConf = new SparkConf().setMaster("local").setAppName("sqlTest")

    //创建SparkSession对象
    val sparkSession: SparkSession = SparkSession.builder().config(sqlTest).getOrCreate()

    //RDD=>DataFrame=>DataSet转换需要引入隐式转换规则，否则无法转换
    //spark不是包名，是上下文环境对象名
    import sparkSession.implicits._

    val rdd: RDD[(Int, String, Int)] = sparkSession.sparkContext.makeRDD(List(
      (1, "zhangsan", 30),
      (2, "lisi", 20),
      (3, "wangwu", 40)))

    val df = rdd.toDF("id", "name", "age")
    df.createOrReplaceTempView("user")

    val f = new MyAvgAgeUDAF

    sparkSession.udf.register("avgAge",f)

    sparkSession.sql("select avgAge(age) as avgAge from user").show()


  }
  // 3区  ===>  输入、缓冲（计算）、输出
  class MyAvgAgeUDAF extends UserDefinedAggregateFunction{
    override def inputSchema: StructType = {
      //底层有结构 就是 StructType(Array(StructField()))  其中StructType和StructField都是样例类，可以不用new
      //LongType ---> 父类就是DataType，他的结果就是得填DataType类的（抽象类）
      StructType(Array(StructField("age",LongType)))
    }

    override def bufferSchema: StructType = {
      //缓冲区中属于需要做计算的数据   sum  / count
      StructType(Array(StructField("sum",LongType),StructField("count",LongType)))
    }

    //返回值的数据类型
    override def dataType: DataType = {
      LongType
    }

    //对于相同的输入是否返回相同的输出
    override def deterministic: Boolean = true

    //初始化 --> 对应缓冲区的两个参数
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
      buffer(1) = 0L
    }

    //更新数据
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      buffer(0) = buffer.getLong(0) + input.getLong(0)
      buffer(1) = buffer.getLong(1) + 1L
    }

    //合并缓冲区
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
      buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }

    //做计算
    override def evaluate(buffer: Row): Any = {
      buffer.getLong(0) / buffer.getLong(1)
    }
  }
}

package com.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-13 0:23
 */
object sql_mysql {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")

    //创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    import spark.implicits._

    val frame: DataFrame = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://hadoop104:3306/spark-sql")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "123456")
      .option("dbtable", "user")
      .load()

    frame.write.format("jdbc")
      .option("url", "jdbc:mysql://hadoop104:3306/spark-sql")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "123456")
      .option("dbtable", "user1")
        .save()

    spark.stop()
  }
}

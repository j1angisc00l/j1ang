package com.streaming

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

import scala.collection.mutable

/**
 * @author J1aNgis0coo1
 * @create 2020-06-14 16:51
 */
object streaming_test01 {

  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("reduceByKeyAndWindow")
    val ssc = new StreamingContext(sparkConf,Seconds(3))


    val ds: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop104",9999)

    val t = new Thread()
    t.start()
    //t.stop()  不推荐使用 ---> 数据不安全
    //因为stop（）会让整个线程直接停止  --->
    //    导致业务执行中会有数据损失 eg（i++  中间过程打断  （非原子性）），long，double


  }
}

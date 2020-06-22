package com.j1ang.summer_framework.core

import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-09 14:06
 */
object EnvUtil {

    private val scLocal = new ThreadLocal[SparkContext]
  private val sscLocal = new ThreadLocal[StreamingContext]

  def getStreamingEnv(implicit time : Duration = Seconds(3)) ={
    //从当前线程内存中去获取环境对象
    val ssc: StreamingContext = sscLocal.get()

    //对获取的对象进行判断
    if(ssc == null){
      //获取不到的话，那么就自己创建一个对象
      val sc: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming")
      val ssc = new StreamingContext(sc,time)

      //将重新创建的对象保存在共享内存中
      sscLocal.set(ssc)
    }
    ssc
  }

  def getEnv(): SparkContext ={
    //从当前线程内存中去获取环境对象
    val sc: SparkContext = scLocal.get()

    //对获取的对象进行判断
    if(sc == null){
      //获取不到的话，那么就自己创建一个对象
      val hOT_wcSparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("HOT_wc")
      val sc = new SparkContext(hOT_wcSparkConf)

      //将重新创建的对象保存在共享内存中
      scLocal.set(sc)
    }
    sc
  }

  def clean(): Unit ={
    getEnv.stop()
    // 将共享内存中的数据清除掉
    scLocal.remove()
  }
}

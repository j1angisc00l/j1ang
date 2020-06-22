package com.j1ang.req_wc_summer.controller

import com.j1ang.req_wc_summer.app.WCApplication.envData
import com.j1ang.req_wc_summer.service.WCService
import com.j1ang.summer_framework.core.TController
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-09 0:54
 */
class WCController extends TController{

  //走了controller后应该走Service
  private val WCservice = new WCService

  //所以这里应该是调Service里面的逻辑
  override def execute(): Unit = {
    val wcArray: Array[(String, Int)] = WCservice.analysis()
    println(wcArray.mkString(","))

  }
}

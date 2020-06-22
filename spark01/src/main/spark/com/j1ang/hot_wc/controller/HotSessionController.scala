package com.j1ang.hot_wc.controller

import com.j1ang.hot_wc.bean
import com.j1ang.hot_wc.service.{HotSessionService, HotTop10Service}
import com.j1ang.summer_framework.core.TController

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 11:06
 */
class HotSessionController extends TController{

  //需要得到需求一 中的数据，所以要去获取需求一中的service
  private val top10Service = new HotTop10Service
  private val service = new HotSessionService

  override def execute(): Unit = {
    val categories: List[bean.HOTCategory] = top10Service.analysis()
    val result = service.analysis(categories)

    result.foreach(println)
  }
}

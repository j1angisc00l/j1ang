package com.j1ang.hot_wc.controller

import com.j1ang.hot_wc.service.HotTop10Service
import com.j1ang.summer_framework.core.TController

/**
 * @author J1aNgis0coo1
 * @create 2020-06-09 14:19
 */
class HotTop10Controller extends TController{

  private val service = new HotTop10Service
  override def execute(): Unit = {

    val result = service.analysis()

    result.foreach(println)

  }
}

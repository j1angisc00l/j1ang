package com.j1ang.hot_wc.controller

import com.j1ang.hot_wc.service.TimeService
import com.j1ang.summer_framework.core.TController

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 10:22
 */
class TimeController extends TController{

  private val service = new TimeService

  override def execute()= {

    val result = service.analysis()


  }
}

package com.testforJD.module1.controller

import com.j1ang.summer_framework.core.TController
import com.testforJD.module1.service.Top10Service

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 20:12
 */
class Top10Controller extends TController{
  private val service = new Top10Service
  override def execute() = {
    val result = service.analysis()
    result.foreach(println)
  }
}

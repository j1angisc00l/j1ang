package com.testforJD.module1.controller

import com.j1ang.summer_framework.core.TController
import com.testforJD.module1.bean
import com.testforJD.module1.service.{SessionTop10Service, Top10Service}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 23:03
 */
class SessionTop10Controller extends TController{

  private val service = new SessionTop10Service
  private val service1 = new Top10Service
  override def execute(): Unit = {
    val categories: List[bean.HOTCategory] = service1.analysis()
    val result = service.analysis(categories)

    result.foreach(println)

  }
}

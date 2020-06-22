package com.testforJD.module1.app

import com.j1ang.summer_framework.core.TApplication
import com.testforJD.module1.controller.SessionTop10Controller

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 23:03
 */
object SessionTop10App extends App with TApplication{

  start("Spark"){
    val controller = new SessionTop10Controller
    controller.execute()
  }
}

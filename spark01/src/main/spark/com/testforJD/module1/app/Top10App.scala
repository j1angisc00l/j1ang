package com.testforJD.module1.app

import com.j1ang.summer_framework.core.TApplication
import com.testforJD.module1.controller.Top10Controller

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 20:12
 */
object Top10App extends App with TApplication{

  start("Spark"){
    val controller = new Top10Controller
    controller.execute()
  }

}

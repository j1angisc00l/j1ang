package com.j1ang.hot_wc.app

import com.j1ang.hot_wc.controller.HotSessionController
import com.j1ang.summer_framework.core.TApplication

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 11:06
 */
object HotSessionApp extends App with TApplication{

  start("Spark"){
    val controller = new HotSessionController
    controller.execute()
  }
}

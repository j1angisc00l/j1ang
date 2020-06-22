package com.j1ang.hot_wc.app

import com.j1ang.hot_wc.controller.TimeController
import com.j1ang.summer_framework.core.TApplication

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 10:22
 */
object TimeApp extends App with TApplication{

  start("Spark"){
  val controller = new TimeController
    controller.execute()
  }
}

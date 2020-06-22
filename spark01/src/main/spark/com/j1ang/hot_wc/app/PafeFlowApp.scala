package com.j1ang.hot_wc.app

import com.j1ang.hot_wc.controller.PageFlowController
import com.j1ang.summer_framework.core.TApplication

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 22:34
 */
object PafeFlowApp extends App with TApplication{

  start("Spark"){
    val controller = new PageFlowController
    controller.execute()
  }
}

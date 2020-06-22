package com.j1ang.hot_wc.app

import com.j1ang.hot_wc.controller.HotTop10Controller
import com.j1ang.summer_framework.core.TApplication

/**
 * @author J1aNgis0coo1
 * @create 2020-06-09 14:18
 */
object HotTop10App extends App with TApplication{

  start("Spark"){
    val controller = new HotTop10Controller
    controller.execute()
  }

}

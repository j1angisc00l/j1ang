package com.streaming.req.app

import com.j1ang.summer_framework.core.TApplication
import com.streaming.req.controller.MockDataController

/**
 * @author J1aNgis0coo1
 * @create 2020-06-15 22:34
 */
object MockDataApp extends App with TApplication{

  start("SparkStreaming"){

    val controller = new MockDataController
    controller.execute()
  }

}

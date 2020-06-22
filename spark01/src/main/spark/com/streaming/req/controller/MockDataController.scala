package com.streaming.req.controller

import com.j1ang.summer_framework.core.TController
import com.streaming.req.service.MockDataService

/**
 * @author J1aNgis0coo1
 * @create 2020-06-15 23:03
 */
class MockDataController extends TController{

  private val service = new MockDataService
  override def execute(): Unit = {
    val result = service.analysis()
  }
}

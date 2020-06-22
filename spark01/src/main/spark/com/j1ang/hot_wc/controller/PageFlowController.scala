package com.j1ang.hot_wc.controller

import com.j1ang.hot_wc.service.PageFlowService
import com.j1ang.summer_framework.core.TController

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 22:35
 */
class PageFlowController extends TController{
  private val service = new PageFlowService
  override def execute()= {
    val result = service.analysis()


  }
}

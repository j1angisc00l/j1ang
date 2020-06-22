package com.j1ang.req_wc_summer.app

import com.j1ang.req_wc_summer.controller.WCController
import com.j1ang.summer_framework.core.TApplication
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-08 23:59
 */
object WCApplication extends App with TApplication{  //继承了APP的类就可以不用写main方法了，可以直接运行了

  start("Spark"){

    //先走controller
    val controller = new WCController
    controller.execute()

    val sc: SparkContext = envData.asInstanceOf[SparkContext]


  }


}

package com.j1ang.hot_wc.DAO

import com.j1ang.hot_wc.bean.UserVisitAction
import com.j1ang.summer_framework.core.TDAO
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 22:35
 */
class PageFlowDAO extends TDAO{

  def getUserVisitAction( path:String ) = {
    val rdd: RDD[String] = readFile(path)
    rdd.map(
      line => {
        val datas = line.split("_")
        UserVisitAction(
          datas(0),
          datas(1).toLong,
          datas(2),
          datas(3).toLong,
          datas(4),
          datas(5),
          datas(6).toLong,
          datas(7).toLong,
          datas(8),
          datas(9),
          datas(10),
          datas(11),
          datas(12).toLong
        )
      }
    )
  }
}

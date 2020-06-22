package com.j1ang.req_wc_summer.service

import com.j1ang.req_wc_summer.DAO.WCDAO
import com.j1ang.req_wc_summer.app.WCApplication.envData
import com.j1ang.summer_framework.core.TService
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-09 0:57
 */
class WCService extends TService{

//走了service后下一个就应该走DAO了
  private val wcdao = new WCDAO

  /**
   * 数据分析
   *
   * @return
   */
  override def analysis()= {
    val sc: SparkContext = envData.asInstanceOf[SparkContext]
    val fileRdd: RDD[String] = sc.textFile("input/wc.txt")
    val wordRdd: RDD[String] = fileRdd.flatMap(_.split(" "))
    val groupRdd: RDD[(String, Iterable[String])] = wordRdd.groupBy(word => word)
    val mapRdd: RDD[(String, Int)] = groupRdd.map {
      case (word, iter) => {
        (word, iter.size)
      }
    }
    val wcArray: Array[(String, Int)] = mapRdd.collect()
    wcArray  //返回的结果
  }
}

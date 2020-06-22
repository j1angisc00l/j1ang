package com.j1ang.hot_wc.service

import java.io

import com.j1ang.hot_wc.DAO.HotTop10DAO
import com.j1ang.hot_wc.bean.HOTCategory
import com.j1ang.hot_wc.help.HOTAccumulator
import com.j1ang.summer_framework.core.{EnvUtil, TService}
import org.apache.spark.rdd.RDD

import scala.collection.mutable
import scala.collection.mutable.ArrayOps

/**
 * @author J1aNgis0coo1
 * @create 2020-06-09 14:21
 */
class HotTop10Service extends TService{
  private val dao = new HotTop10DAO
  /**
   * 数据分析
   *
   * @return
   */
  override def analysis()= {
    val rdd: RDD[String] = dao.readFile("input/user_visit_action.txt")

    //声明累加器，注册累加器
    val acc = new HOTAccumulator
    EnvUtil.getEnv().register(acc)

    rdd.foreach(
      actLine =>{
        val datas: Array[String] = actLine.split("_")
        if(datas(6) != "-1"){
          acc.add(datas(6),"click")
        }else if(datas(8) != "null"){
          val line1: Array[String] = datas(8).split(",")
          line1.foreach(id => {
            acc.add(id,"order")
          })
        }else if (datas(10) != "null"){
          val line1: Array[String] = datas(10).split(",")
          line1.foreach(
            id =>{
              acc.add(id,"pay")
            }
          )
        }else {
          Nil
        }
      }
    )

    //获取累加器计算后的值
    val accValue: mutable.Map[String, HOTCategory] = acc.value
    val categories: mutable.Iterable[HOTCategory] = accValue.map(_._2)

    val result: List[HOTCategory] = categories.toList.sortWith(
      (left, right) => {

        if (left.clickCount > right.clickCount) {
          true
        } else if (left.clickCount == right.clickCount) {
          if (left.orderCount > right.orderCount) {
            true
          } else if (left.orderCount == right.orderCount) {
            left.payCount > right.payCount
          } else {
            false
          }
        } else {
          false
        }
      }
    ).take(10)
    result

  }
}

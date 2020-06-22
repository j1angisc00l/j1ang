package com.j1ang.hot_wc.help

import com.j1ang.hot_wc.bean.HOTCategory
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 0:07
 */
class HOTAccumulator extends AccumulatorV2[(String, String),mutable.Map[String, HOTCategory]]{

  private val map = mutable.Map[String, HOTCategory]()

  override def isZero: Boolean = {
    map.isEmpty
  }

  override def copy(): AccumulatorV2[(String, String), mutable.Map[String, HOTCategory]] = {
    new HOTAccumulator
  }

  override def reset(): Unit = {
    map.clear()
  }

  override def add(v: (String, String)): Unit = {
    val cid = v._1
    val action = v._2

    //如果是第一次，则是没有key数据的，则创建一个，如果存在了，则跳过
    val hOTCategory: HOTCategory = map.getOrElse(cid,HOTCategory(cid,0,0,0))

    action match {
      case "click" => hOTCategory.clickCount += 1
      case "order" => hOTCategory.orderCount += 1
      case "pay" => hOTCategory.payCount += 1
      case _ =>
    }

    map(cid) = hOTCategory

  }

  override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, HOTCategory]]): Unit = {
    other.value.foreach{
      case (cid,hotCategory) => {
        val hc: HOTCategory = map.getOrElse(cid,HOTCategory(cid,0,0,0))

        hc.clickCount += hotCategory.clickCount
        hc.orderCount += hotCategory.orderCount
        hc.payCount += hotCategory.payCount

        map(cid) = hc
      }
    }
  }

  override def value: mutable.Map[String, HOTCategory] = {map}
}

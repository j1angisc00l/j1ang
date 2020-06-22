package com.testforJD.module1.help

import com.testforJD.module1.bean.HOTCategory
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 21:34
 */
class HOTAccumulator_test extends AccumulatorV2[(String, String),(mutable.Map[String, HOTCategory])]{

  //private val map1 = mutable.Map[String, HOTCategory]
  private val map = mutable.Map[String, HOTCategory]()
  override def isZero: Boolean = {
    map.isEmpty
    //map1.is
  }

  override def copy(): AccumulatorV2[(String, String), mutable.Map[String, HOTCategory]] = {
    new HOTAccumulator_test
  }

  override def reset(): Unit = {
    map.clear()
  }

  override def add(v: (String, String)): Unit = {
    val pid = v._1
    val action = v._2

    val hotvalue: HOTCategory = map.getOrElse(pid,HOTCategory(pid,0,0,0))
    action match {
      case "click" => hotvalue.clickCount += 1
      case "order" => hotvalue.orderCount += 1
      case "pay" => hotvalue.payCount += 1
      case _ =>
    }

    map(pid)=hotvalue
  }

  override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, HOTCategory]]): Unit = {
    other.value.foreach {
      case (pid, hotvalue) => {
        val hc: HOTCategory = map.getOrElse(pid,HOTCategory(pid,0,0,0))

        hc.clickCount += hotvalue.clickCount
        hc.orderCount += hotvalue.orderCount
        hc.payCount += hotvalue.payCount

        map(pid) = hc
      }
    }
  }

  override def value: mutable.Map[String, HOTCategory] = {map}
}
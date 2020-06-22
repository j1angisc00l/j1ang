package com.testforJD.module1.service

import com.j1ang.hot_wc.bean
import com.j1ang.summer_framework.core.{EnvUtil, TService}
import com.testforJD.module1.DAO.Top10DAO
import com.testforJD.module1.bean.HOTCategory
import com.testforJD.module1.help.HOTAccumulator_test
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 20:13
 */
class Top10Service extends TService{

  private val dao = new Top10DAO
  //需求分析 ：
  // step1 - 获取数据
  // step2 - 将数据根据点击数，下单数，支付数分别展示出 （品类，（1,0,0）） （品类，（0,1,0）） （品类，（0,0,1））
  // step3 - 用union聚合起来
  // step4 - 排序取值
  override def analysis()= {

    // step1 - 获取数据
    val actionRDD: RDD[bean.UserVisitAction] = dao.getUserVisitAction("input/user_visit_action.txt")

    val acc = new HOTAccumulator_test
    EnvUtil.getEnv().register(acc)

    actionRDD.foreach(
      actline =>{
        if(actline.click_category_id != -1){
          acc.add(actline.click_category_id.toString,"click")
        }else if (actline.order_category_ids != "null"){
          val line1: Array[String] = actline.order_category_ids.split(",")
          line1.foreach(
            data => acc.add(data,"order")
          )
        }else if(actline.pay_category_ids != "null"){
          val line2: Array[String] = actline.pay_category_ids.split(",")
          line2.foreach(
            data => acc.add(data,"pay")
          )
        }else{
          Nil
        }
      }
    )

    val value: mutable.Map[String, HOTCategory] = acc.value

    val categories: mutable.Iterable[HOTCategory] = value.map(_._2)

    val result: List[HOTCategory] = categories.toList.sortWith(
      (left, right) => {
        if (left.clickCount > right.clickCount) {
          true
        } else if (left.clickCount == right.clickCount) {
          if (left.orderCount > right.orderCount) {
            true
          } else if (left.orderCount == right.orderCount) {
            left.payCount > right.payCount
          }else{
            false
          }

        }else{
          false
        }

      }
    ).take(10)
    result


  }

}

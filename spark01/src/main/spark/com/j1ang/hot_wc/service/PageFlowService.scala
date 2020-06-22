package com.j1ang.hot_wc.service

import com.j1ang.hot_wc.DAO.PageFlowDAO
import com.j1ang.hot_wc.bean
import com.j1ang.summer_framework.core.TService
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 22:36
 */
class PageFlowService extends TService{

  private val dao = new PageFlowDAO

  override def analysis(): Any = {

    val flowid = List(1,2,3,4,5,6)
    val okflowids: List[String] = flowid.zip(flowid.tail).map(t => (t._1 + "-" + t._2))

    val actionRDD: RDD[bean.UserVisitAction] = dao.getUserVisitAction("input/user_visit_action.txt")
    actionRDD.cache()

    //1 计算分母 就是一个wc  (首页, 1)
    val filtRDD: RDD[bean.UserVisitAction] = actionRDD.filter(action => {
      flowid.init.contains(action.page_id.toInt)
    })

    val mapRDD: RDD[(Long, Int)] = filtRDD.map(action => {
      (action.page_id, 1)
    })


    //1.1 总和
    val reduceRDD: RDD[(Long, Int)] = mapRDD.reduceByKey(_+_)
    val pageCountArray: Array[(Long, Int)] = reduceRDD.collect()

    //2 计算分子   根据用户session排序
    val groupRDD: RDD[(String, Iterable[bean.UserVisitAction])] = actionRDD.groupBy(action => {
      action.session_id
    })

    //2.1 根据时间进行排序
    val mapVRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(iter => {
      val actions: List[bean.UserVisitAction] = iter.toList.sortWith(
        (left, right) => {
          left.action_time < right.action_time
        }
      )
      val pageid: List[Long] = actions.map(action => {
        action.page_id
      })

      // TODO 将转换后的结果进行格式的转换
      // 1，2，3，4
      // 2，3，4
      // （1-2），（2-3），（3-4）

      val tuples: List[(Long, Long)] = pageid.zip(pageid.tail)

      val tuples1: List[(String, Int)] = tuples.map(kv => {
        (kv._1 + "-" + kv._2, 1)
      }).filter(s => {
        okflowids.contains(s._1)
      })

      tuples1

    })


    val pidRDD: RDD[List[(String, Int)]] = mapVRDD.map(_._2)

    val pidRDD1: RDD[(String, Int)] = pidRDD.flatMap(list => list)

    val pidSumRDD: RDD[(String, Int)] = pidRDD1.reduceByKey(_+_)

    pidSumRDD.foreach(
      line =>{
        val pid = line._1.split("-")(0)
        val v = pageCountArray.toMap.getOrElse(pid.toLong,1)
        println("页面跳转【" + line._1 + "】的转换率为" + (line._2.toDouble / v))
      }
    )


  }
}

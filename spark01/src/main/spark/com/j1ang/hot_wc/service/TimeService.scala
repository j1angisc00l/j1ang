package com.j1ang.hot_wc.service

import java.text.SimpleDateFormat
import java.time.Duration

import com.j1ang.hot_wc.DAO.TimeDao
import com.j1ang.hot_wc.bean
import com.j1ang.summer_framework.core.TService
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 10:53
 */
class TimeService extends TService{

  private val dao = new TimeDao

  override def analysis(): Any = {

    val actionRDD: RDD[bean.UserVisitAction] = dao.getUserVisitAction("input/user_visit_action.txt")

    actionRDD.cache()
    //println("========")
    //按用户的session分组
    val groupRDD: RDD[(String, Iterable[bean.UserVisitAction])] = actionRDD.groupBy(action => action.session_id)

    val mvgRDD: RDD[(String, List[(Long, Long)])] = groupRDD.mapValues(iter => {
      //按时间分组，从小到大
      val sortRDD: List[bean.UserVisitAction] = iter.toList.sortWith(
        (lift, right) => {
          lift.action_time < right.action_time
        }
      )

      //转换格式（page_id，action_time）
      val tuples: List[(Long, String)] = sortRDD.map(datas => {
        (datas.page_id, datas.action_time)
      })

      //对时间的格式进行转换
      val tuples1: List[(Long, Long)] = tuples.map(datas => {
        val time: Long = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datas._2).getTime
        (datas._1, time)
      })
      //进行结构的转换 （pageid，（time2-time1））
      val tuples2: List[(Long, Long)] = tuples1.zip(tuples1.tail).map(data => {
        (data._1._1, (data._2._2 - data._1._2))
      })

      tuples2
    })
    //转换形式 （pageid，（time2-time1））
    val timeRDD: RDD[(Long, Long)] = mvgRDD.map(_._2).flatMap(list =>list)

    //对pageid分组，这样可以求得 总的时间，和pageid的个数
    val time2RDD: RDD[(Long, Iterable[Long])] = timeRDD.groupByKey()

    time2RDD.foreach {
      case (pageid, iter) => {
        val count = iter.size
        val sum: Long = iter.sum

        println("当前页面为" + pageid + ",其停留时间的平均值是" + sum/count)
      }
    }
  }
}

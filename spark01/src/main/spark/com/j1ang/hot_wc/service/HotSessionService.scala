package com.j1ang.hot_wc.service

import com.j1ang.hot_wc.DAO.HotSessionDAO
import com.j1ang.hot_wc.bean
import com.j1ang.summer_framework.core.{EnvUtil, TService}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD

/**
 * @author J1aNgis0coo1
 * @create 2020-06-10 11:07
 */
class HotSessionService extends TService{

  private val dAO = new HotSessionDAO

  override def analysis(datas: Any)= {
    //将传过来的数据类型统一   需求一的数据
    val top10: List[bean.HOTCategory] = datas.asInstanceOf[List[bean.HOTCategory]]

    //val top10Ids: List[String] = top10.map(_.categoryId)

    // TODO 使用广播变量实现数据的传播
    //val bcList: Broadcast[List[String]] = EnvUtil.getEnv().broadcast(top10Ids)

    //获取用户行为数据
    val actionRdd = dAO.getUserAction("input/user_visit_action.txt")

    //对数据进行过滤处理
    //对用户的点击行为进行过滤    -->  有点击行为的数据被获取
    val filterRdd: RDD[bean.UserVisitAction] = actionRdd.filter(
      action => {
        if (action.click_category_id != -1) {
          //bcList.value.contains(action.click_category_id.toString)
          var flag = false
          top10.foreach(
            hc =>{
              if(hc.categoryId.toLong == action.click_category_id){
                flag = true
              }
            }
          )
          flag
        } else {
          false
        }
      }
    )
    //将过滤后的数据进行处理
    //  (品类-session，1)  --》(品类-session，sum)
    val reduceRDD: RDD[(String, Int)] = filterRdd.map(
      action => {
        (action.click_category_id + "_" + action.session_id, 1)
      }
    ).reduceByKey(_ + _)

    //数据的转换
    //(品类,（session，sum）)
    val mapRDD: RDD[(String, (String, Int))] = reduceRDD.map {
      case (clickIDAndSession, sum) => {
        val line: Array[String] = clickIDAndSession.split("_")
        (line(0), (line(1), sum))
      }
    }


    //将数据分组
    //(品类，Iterator[(session1, sum1), (session2, sum2)])
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = mapRDD.groupByKey()

    //将分组后的数据排序取前10
    val result: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(iter => {
      iter.toList.sortWith(
        (left, right) => {
          left._2 > right._2
        }
      ).take(10)
    })

    result.collect()

  }
}

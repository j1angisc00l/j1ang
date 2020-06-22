package com.testforJD.module1.service


//import com.j1ang.hot_wc.bean
import com.j1ang.summer_framework.core.TService
import com.testforJD.module1.DAO.SessionTop10DAO
import com.testforJD.module1.bean
import org.apache.spark.rdd.RDD


/**
 * @author J1aNgis0coo1
 * @create 2020-06-11 23:03
 */
class SessionTop10Service extends TService{
  private val dao = new SessionTop10DAO

  /*Top10热门品类中每个品类的Top10活跃Session统计
    1.将需求一的数据拿到
    2.过滤不是点击的数据   过滤！！！！（1、不是点击的数据 2、不在top10的数据）
    3.转换结构（品类-session，1）
    4.（品类-session，count）
    5.（品类，（session，count））
    6.分组
    7.排序
   */
  override def analysis(datas: Any) = {
    //将需求一的数据拿到
    val top10: List[bean.HOTCategory] = datas.asInstanceOf[List[bean.HOTCategory]]

    //获取数据
    val actionRDD = dao.getUserVisitAction("input/user_visit_action.txt")

    val filterRDD = actionRDD.filter(
      datas => {
        if (datas.click_category_id != -1) {
          var flag = false
          top10.foreach(
            data => {
              if (data.categoryId.toLong == datas.click_category_id) {
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
    //转换结构（品类&session，1）
    val map1RDD: RDD[(String, Int)] = filterRDD.map(datas => {
      (datas.click_category_id + "&" + datas.session_id, 1)
    })
    //转换结构（品类&session，count）
    val countRDD: RDD[(String, Int)] = map1RDD.groupByKey().mapValues(_.size)

    //转换结构（品类,(session，count)）
    val map2RDD: RDD[(String, (String, Int))] = countRDD.map {
      case (pidandss, count) => {
        val line: Array[String] = pidandss.split("&")
        (line(0), (line(1), count))
      }
    }
    //
    val result: RDD[(String, List[(String, Int)])] = map2RDD.groupByKey().mapValues(
      iter => {
        iter.toList.sortWith(
          (left, right) => {
            left._2 > right._2
          }
        ).take(10)
      }
    )

    result.collect()
  }
}

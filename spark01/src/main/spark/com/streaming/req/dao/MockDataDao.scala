package com.streaming.req.dao

import java.util.Random

import com.j1ang.summer_framework.core.TDAO

import scala.collection.mutable.ArrayBuffer

/**
 * @author J1aNgis0coo1
 * @create 2020-06-15 23:03
 */
class MockDataDao extends TDAO{
  // TODO ********************数据准备*************************
  /**
   *
   * 城市信息表
   *
   * @param city_id     城市id
   * @param city_name   城市名称
   * @param area        城市所在大区
   */
  case class CityInfo (city_id:Long,
                       city_name:String,
                       area:String)

  import scala.collection.mutable.ListBuffer
  import scala.util.Random

  case class RanOpt[T](value: T, weight: Int)

  object RandomOptions {

    def apply[T](opts: RanOpt[T]*): RandomOptions[T] = {
      val randomOptions = new RandomOptions[T]()
      for (opt <- opts) {
        randomOptions.totalWeight += opt.weight
        for (i <- 1 to opt.weight) {
          randomOptions.optsBuffer += opt.value
        }
      }
      randomOptions
    }
  }

  class RandomOptions[T](opts: RanOpt[T]*) {

    var totalWeight = 0
    var optsBuffer = new ListBuffer[T]

    def getRandomOpt: T = {
      val randomNum: Int = new Random().nextInt(totalWeight)
      optsBuffer(randomNum)
    }
  }

  // TODO ********************数据生成*************************
  implicit def genMockData():Seq[String] ={
    val array: ArrayBuffer[String] = ArrayBuffer[String]()   // 准备可变数组
    val CityRandomOpt = RandomOptions(RanOpt(CityInfo(1, "北京", "华北"), 30),
      RanOpt(CityInfo(2, "上海", "华东"), 30),
      RanOpt(CityInfo(3, "广州", "华南"), 10),
      RanOpt(CityInfo(4, "深圳", "华南"), 20),
      RanOpt(CityInfo(5, "天津", "华北"), 10))

    val random = new Random()// 随即生成数据
    // 模拟实时数据：
    // timestamp province city userid adid
    for (i <- 0 to 50) {

      val timestamp: Long = System.currentTimeMillis()
      val cityInfo: CityInfo = CityRandomOpt.getRandomOpt
      val city: String = cityInfo.city_name
      val area: String = cityInfo.area
      val adid: Int = 1 + random.nextInt(6)
      val userid: Int = 1 + random.nextInt(6)

      // 拼接实时数据
      //array += timestamp + " " + area + " " + city + " " + userid + " " + adid
      // string = " 时间戳 区域 城市 用户ID  广告ID "
      // string = " 1111111 河北 北京 1  1 "
      array.append(timestamp + " " + area + " " + city + " " + userid + " " + adid)
    }
    array.toSeq
  }
}

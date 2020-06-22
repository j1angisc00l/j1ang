package com.j1ang.wc

import scala.io.Source

/**
 * @author J1aNgis0coo1
 * @create 2020-05-28 15:33
 */
object wc_test {

  def main(args: Array[String]): Unit = {

    //step1:按行读取文件信息  Source！！
    val datalist: List[String] = Source.fromFile("input/wc.txt").getLines().toList

    //step2:切割
    val strings: List[String] = datalist.flatMap((s:String) => {s.split(" ")})

    //step3:分组
    val stringToStrings: Map[String, List[String]] = strings.groupBy((s:String) => {s})

    //step4:统计
    val stringToInt: Map[String, Int] = stringToStrings.map((kv) =>{(kv._1,kv._2.length)})

    //step5：排序 （这里Map  要转换成 List）
    val tuples: List[(String, Int)] = stringToInt.toList.sortBy((kv) => { -kv._2})

    //step6: 取前几名的计算
    val list: List[(String, Int)] = tuples.take(3)
    println(list)
  }

}

package com.j1ang.wc

/**
 * @author J1aNgis0coo1
 * @create 2020-05-28 16:01
 */
object wc_test1 {
  def main(args: Array[String]): Unit = {

    val tuples = List(
      ("hello", 4),
      ("hello spark", 3),
      ("hello spark scala", 2),
      ("hello spark scala hive", 1)
    )
    //step1:展开
    val list: List[String] = tuples.map((kv) => {(kv._1 + " ") * kv._2})

    //step2：去掉字符串最后一个字符
    val list1: List[String] = list.map((word) => {word.substring(0,word.length - 1)})

    //step3:切割字符串
    val list2: List[String] = list1.flatMap((word) => {word.split(" ")})

    //step4:分组
    val stringToStrings: Map[String, List[String]] = list2.groupBy((word) => {word})

    //step5:统计
    val list3: List[(String, Int)] = stringToStrings.toList.map((kv) => {(kv._1,kv._2.length)})

    //step6:取前3名
    val list4: List[(String, Int)] = list3.take(3)
    println(list4)



  }
}

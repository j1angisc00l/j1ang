package com.j1ang.wc

/**
 * @author J1aNgis0coo1
 * @create 2020-05-28 16:30
 */
object wc_test2 {
  def main(args: Array[String]): Unit = {

    val tuples = List(
      ("hello", 4),
      ("hello spark", 3),
      ("hello spark scala", 2),
      ("hello spark scala hive", 1)
    )
    //TODO 切割数据变为一个个的字符串
    val list: List[(List[String], Int)] = tuples.map((kv)=>{(kv._1.split(" ").toList,kv._2)})
    //List((List(hello),4), (List(hello, spark),3), (List(hello, spark, scala),2), (List(hello, spark, scala, hive),1))

    //TODO 每个字符串对应相应的个数
    val list2: List[List[(String, Int)]] = list.map((kv) => {kv._1.map((k) => (k,kv._2))})
    //List(List((hello,4)), List((hello,3), (spark,3)), List((hello,2), (spark,2), (scala,2)), List((hello,1), (spark,1), (scala,1), (hive,1)))

    //TODO 扁平化
    val flatten: List[(String, Int)] = list2.flatten
    //List((hello,4), (hello,3), (spark,3), (hello,2), (spark,2), (scala,2), (hello,1), (spark,1), (scala,1), (hive,1))

    //TODO 分组
    val stringToTuples: Map[String, List[(String, Int)]] = flatten.groupBy((k) => k._1)
    //Map(spark -> List((spark,3), (spark,2), (spark,1)), scala -> List((scala,2), (scala,1))
    //    ,hive -> List((hive,1)), hello -> List((hello,4), (hello,3), (hello,2), (hello,1)))

    //TODO 合并kv中的v的个数 （转换成list处理）
    val stringToInt: Map[String, Int] = stringToTuples.map((kv)=> {(kv._1,kv._2.map((k) => {k._2}).toList.sum)})
    //Map(spark -> 6, scala -> 3, hive -> 1, hello -> 10)

    //TODO 排序
    val list1: List[(String, Int)] = stringToInt.toList.sortBy((kv)=> -kv._2)
    //List((hello,10), (spark,6), (scala,3), (hive,1))

    //TODO 取值
    val list3: List[(String, Int)] = list1.take(3)
    println(list3)


  }
}

package com.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, MapType, StringType, StructField, StructType}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-13 20:36
 */
object sql_project2 {
  def main(args: Array[String]): Unit = {

    System.setProperty("HADOOP_USER_NAME", "root")

    val sql_project: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sql_project")

    val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(sql_project).getOrCreate()

    spark.sql("use spark_sql0613")

    val udaf = new CityRemarkUDAF
    spark.udf.register("cityremark",udaf)
    spark.sql(
      """
        |select *
        |from (
        |   select *,rank() over(partition by t2.area order by t2.clickCount desc) as rank
        |   from (
        |       select t1.area,t1.product_name,count(*) as clickCount,cityRemark(city_name)
        |       from (
        |           select a.*,c.area,c.city_name,p.product_name
        |           from user_visit_action a
        |           join city_info c on a.city_id = c.city_id
        |           join product_info p on p.product_id = a.click_product_id
        |           where a.click_product_id > -1
        |           )t1
        |       group by t1.area,t1.product_name
        |       )t2
        |   )t3
        |where rank <= 3
      """.stripMargin).show()

    spark.stop()
  }

  class CityRemarkUDAF extends UserDefinedAggregateFunction{

    //输入的类型
    override def inputSchema: StructType = {
      StructType(Array(StructField("cityname",StringType)))
    }

    //缓冲区数据 总和 、 map（城市，点击数量）
    override def bufferSchema: StructType = {
      StructType(Array(
        StructField("totalcount",LongType),
        StructField("citymap",MapType(StringType,LongType))
      ))
    }

    //返回城市备注的字符串
    override def dataType: DataType = {
      StringType
    }

    override def deterministic: Boolean = true

    //初始化
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
      buffer(1) = Map[String,Long]()
    }

    //更新
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      buffer(0) = buffer.getLong(0) + 1
      val cityname: String = input.getString(0)

      val citymap: Map[String, Long] = buffer.getAs[Map[String,Long]](1)

      val newCount: Long = citymap.getOrElse(cityname,0L) + 1

      buffer(1) = citymap.updated(cityname,newCount)

    }
    //合并
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)

      val map1: Map[String, Long] = buffer1.getAs[Map[String,Long]](1)
      val map2: Map[String, Long] = buffer2.getAs[Map[String,Long]](1)

      buffer1(1) = map1.foldLeft(map2){
        case (map,(k,v)) => {
          val newV: Long = map.getOrElse(k,0L) + v
          map.updated(k,newV)
        }
      }


    }
    //计算
    override def evaluate(buffer: Row): Any = {
      val totalcount = buffer.getLong(0)
      val citymap = buffer.getAs[Map[String,Long]](1)

      //排序
      val countlist: List[(String, Long)] = citymap.toList.sortWith((left, right) => left._2 > right._2).take(2)

      var rest = 0L
      val s = new StringBuilder  //创建可变的String类型用来接收 城市备注
      countlist.foreach{
        case (city,count) => {
          val r1: Long = count * 100 / totalcount
           //百分比
          s.append(city + " " + r1 + "%,")
          rest = rest + r1
        }
      }

      s.toString() +"其他 " + (100L - rest) + "%"
    }
  }
}

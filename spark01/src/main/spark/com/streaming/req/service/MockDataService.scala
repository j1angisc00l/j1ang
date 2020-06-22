package com.streaming.req.service

import java.util.Properties

import com.j1ang.summer_framework.PropertiesUtil
import com.j1ang.summer_framework.core.TService
import com.streaming.req.dao.MockDataDao
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
 * @author J1aNgis0coo1
 * @create 2020-06-15 23:03
 */
class MockDataService extends TService{
  private val dao = new MockDataDao
  override def analysis()= {


    //TODO 生成模拟数据
    import dao._
    val data: Seq[String] = dao.genMockData()
    //TODO 发送数据

    dao.writeTOKafka(data)


  }
}

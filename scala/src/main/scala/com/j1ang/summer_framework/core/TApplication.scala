package com.j1ang.summer_framework.core

import java.net.{ServerSocket, Socket}

import com.j1ang.summer_framework.PropertiesUtil
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author J1aNgis0coo1
 * @create 2020-05-24 19:39
 */
trait TApplication {

var envData : Any = null  //为了让server\Client 可以获取到环境Socket

  def start(t:String = "jdbc")(op: =>Unit)(implicit time:Duration = Seconds(3)): Unit ={

    //初始化环境
    if(t == "serverSocket"){
      envData= new ServerSocket(PropertiesUtil.getValue("server.port").toInt)

    }else if (t == "Socket"){
      envData= new Socket(
        PropertiesUtil.getValue("server.host"),
        PropertiesUtil.getValue("server.port").toInt)
    }else if (t == "Spark"){
      envData = EnvUtil.getEnv()
    }else if (t == "SparkStreaming"){
      envData = EnvUtil.getStreamingEnv()
    }

    // 业务逻辑
    try {
      op
    } catch {
      case ex: Exception => println("业务执行失败 ：" + ex.getMessage)
    }

    // 环境关闭
    if(t == "serverSocket"){
      val serverSocket : ServerSocket = envData.asInstanceOf[ServerSocket]
      if(!serverSocket.isClosed){
        serverSocket.close()
      }
    }else if (t == "Socket"){
      val socket :Socket = envData.asInstanceOf[Socket]
      if(!socket.isClosed){
        socket.close()
      }
    }else if (t == "Spark"){
      EnvUtil.clean()
    }else if (t == "SparkStreaming"){
      val ssc: StreamingContext = envData.asInstanceOf[StreamingContext]
      ssc.start()
      ssc.awaitTermination()
    }
  }


}

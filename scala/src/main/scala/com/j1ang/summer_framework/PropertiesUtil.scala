package com.j1ang.summer_framework

import java.util.ResourceBundle

/**
 * @author J1aNgis0coo1
 * @create 2020-05-24 21:16
 */
//获得配置文件
object PropertiesUtil {

  //关联配置文件
  //ResourceBundle  是专门用来读取配置文件，所以读取是，不需要增加.properties,直接写名字
  val bundle: ResourceBundle = ResourceBundle.getBundle("j1ang")

  def getValue(key:String): String={
    bundle.getString(key)
  }
}

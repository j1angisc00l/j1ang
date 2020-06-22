package com.j1ang.scala

/**
 * @author J1aNgis0coo1
 * @create 2020-05-18 14:28
 */
object Scala_Variable {

  def main(args: Array[String]): Unit = {

    //Scala - 变量
    //声明变量
    //var 变量名 ： 变量类型  =（赋值） 变量值
    var name : String = "j1ang"
    //如果从语法中推断出变量类型，则可以省略类型  （强类型语言嘛！）
    //scala 是静态类型语言，在 编译时必须要确定类型

    //2. var 变量名 = 变量值
    var name1 = "j1ang"

    //3.final 不能直接修饰变量
    // X      final var name = "j1ang"
    //scala为了让变量声明后不能发生修改，而且不能使用final关键字的场合，产生了新的关键字声明变量，声明后的变量无法修饰
    //   val

    val name2 = "j1ang"
    //val 比  var使用 的场景更多更广

    //"======================================================================================="

    /**
    变量初始化
     */



  }

}

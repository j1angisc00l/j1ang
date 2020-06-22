package com.j1ang.scala

/**
 * @author J1aNgis0coo1
 * @create 2020-05-18 9:41
 */
/*
scala是一个完全面向对象的编程语言，而Java不是一个完全面向对象的编程语言（static（静态跟面向对象无关）、基本类型）

object：scala语言中没有静态的语法，采用object的方式来模仿java中的静态语法
        体现点：通过类名来访问方法

def：声明方法关键字的缩写 define。

main（参数列表）：主通道

Scala -----》 args: Array[String]：参数声明  ===>  参数名：参数类型

java ------》强类型语言  String[] args ====> 参数类型：参数名

Array：scala中的数组 ,类似java中的中括号
[string]:表示泛型

Unit ：方法名 与java中的void相同，但unit是一个类型，void是关键字
       main(args: Array[String]): Unit  ===》 方法名：类型
       表示没有返回值

=：赋值  方法方法也是对象，可以赋值

scala语言是基于java语言开发的，所以可以直接嵌入java代码执行

scala默认会导入一些对象，那么这些对象的方法可以直接使用
  有个predef  ！！


 */
object Scala_Hello {

  def main(args: Array[String]): Unit = {

    System.out.println("Hello,Scala")

    println("Hello Scala")

  }
}

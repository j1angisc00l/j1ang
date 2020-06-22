package com {
  package j1ang {
    package scala05 {

      /**
       * @author J1aNgis0coo1
       * @create 2020-05-23 20:18
       */
      object scala_test {

        def main(args: Array[String]): Unit = {
          val claSS: Class = new Class
          val stu1: Student = new Student()
          stu1.name = "j1ang"
          stu1.age = 24
          stu1.Class = claSS //一定要记得 要跟新创建的类关联起来，要不然会报空指针参数
          claSS.claSS = "SZ大数据0213"

          println(s"学生${stu1.name}，今年${stu1.age}在${stu1.Class.claSS}学习")

        }
      }

      class Class {
        var claSS: String = _
      }

      class Student {
        var name: String = _
        var age: Int = _
        var Class: Class = _
      }

    }

  }

}

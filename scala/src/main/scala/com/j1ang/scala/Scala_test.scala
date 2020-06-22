package com.j1ang.scala

/**
 * @author J1aNgis0coo1
 * @create 2020-05-18 10:42
 */
object Scala_test {

  //声明方法 def + 方法名（参数列表 （参数名：参数类型）） + 是否有返回值（无则Unit） =（赋值） {  方法体  }

  //public void main(String[] args)
  def main(args : Array[String]) : Unit = {

    /*
    System.out.println("HELLO WORLD");
    Predef$.MODULE$.print(test(" "));
     */
    System.out.println("HELLO WORLD")
    print(test(" "))
  }

  //public String test(String j1ang) { return "hey j1ang"; }
  def test(j1ang : String) : String = {
    return "hey j1ang"
  }

  def test2(j1ang : String) : String = {
    return "hey j1ang"
  }
}

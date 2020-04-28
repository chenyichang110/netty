package com.atguigu.hashcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test001{
  String str=new String("tarena");
  char[]ch={'a','b','c'};
  public static void main(String args[]){
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(1);
    list.add(1);
    list.add(1);
    list.add(2);
    Set set = new HashSet(list);
    System.out.println(set.size());


  }
  public void change(String str,char ch[]){
    //引用类型变量，传递的是地址，属于引用传递。
    str="test ok";
    ch[0]='g';
  }
  static   class A {
    static String c;
    protected static void test(){}
  }
}
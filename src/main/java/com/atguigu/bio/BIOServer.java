package com.atguigu.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

  public static void main(String[] args) throws IOException {

    //线程池机制
    //1.创建一个线程池
    //2.如果有客户端连接，就创建一线程，与之通讯
    ExecutorService executorService = Executors.newCachedThreadPool();
    //创建ServerSocket
    ServerSocket serverSocket = new ServerSocket(6666);

    System.out.println("服务器启动了");
    while (true) {
      //监听，等待客户端连接
      final Socket socket = serverSocket.accept();

      System.out.println("连接到一个客户端");


    }
  }

  //编写一个handler方法，和客户端通讯
  public static void handler(Socket socket) {

  }
}

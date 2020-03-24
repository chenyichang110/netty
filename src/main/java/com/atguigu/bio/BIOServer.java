package com.atguigu.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

  public static void main(String[] args) throws IOException {

    //线程池机制
    //1.创建一个线程池
    //2.如果有客户端连接，就创建一线程，与之通讯
    ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
    //创建ServerSocket
    ServerSocket serverSocket = new ServerSocket(6666);

    System.out.println("服务器启动了");
    while (true) {
      System.out.println(
          "线程id=" + Thread.currentThread().getId() + "name=" + Thread.currentThread().getName());
      //监听，等待客户端连接
      System.out.println("等待连接");
      final Socket socket = serverSocket.accept();
      System.out.println("连接到一个客户端");

      newCachedThreadPool.execute(() -> {
        handler(socket);
      });

    }
  }

  //编写一个handler方法，和客户端通讯
  public static void handler(Socket socket) {

    try {
      System.out.println(
          "线程id=" + Thread.currentThread().getId() + "name=" + Thread.currentThread().getName());
      //获取socket输入流
      InputStream inputStream = socket.getInputStream();
      byte[] bytes = new byte[1024];
      while (true) {
        System.out.println(
            "线程id=" + Thread.currentThread().getId() + "name=" + Thread.currentThread().getName());
        System.out.println("...read");
        int read = inputStream.read(bytes);
        if (read != -1) {
          //输出到控制台
          System.out.println(new String(bytes, 0, read));
        } else {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      //关闭资源
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

package com.atguigu.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

  public static void main(String[] args) throws IOException {

    //获取通道
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
//    InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
    //设置为非阻塞new InetSocketAddress("127.0.0.1", 6666)
    socketChannel.configureBlocking(false);

    //开始连接服务器
    if (!socketChannel.isConnected()) {
      while (!socketChannel.finishConnect()) {
        System.out.println("正在连接到服务器");
      }
    }

    //连接成功,发送点数据
    String msg = "今天天气真好";
    socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
    System.in.read();
  }
}

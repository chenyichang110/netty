package com.atguigu.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {

  public static void main(String[] args) throws IOException {
    InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

    ServerSocket serverSocket = serverSocketChannel.socket();

    serverSocket.bind(inetSocketAddress);

    ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
    while (true) {
      SocketChannel socketChannel = serverSocketChannel.accept();
      int read = 0;
      while (read != -1) {
        try {
          read = socketChannel.read(byteBuffer);
        } catch (Exception e) {
          break;
        }
        byteBuffer.rewind();//倒带，position = 0 ，mark 作废
      }
    }
  }

}

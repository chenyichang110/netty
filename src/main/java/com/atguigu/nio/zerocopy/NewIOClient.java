package com.atguigu.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

  public static void main(String[] args) throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("localhost", 7000));
    String fileName = "dubbox-dubbox-2.8.4.zip";
    FileChannel fileChannel = new FileInputStream(fileName).getChannel();

    long start = System.currentTimeMillis();

    //在linux下一个transferTo方法就可以传输
    //在Windows 下 一次调用只能发送 8M，所以需要考虑分段传输

    //transferTo 底层用的就是零拷贝
    long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

    System.out.println("发送的总字节数：" + transferCount + "耗时：" + (System.currentTimeMillis() - start));

    fileChannel.close();
  }

}

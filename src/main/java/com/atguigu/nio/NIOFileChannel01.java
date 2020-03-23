package com.atguigu.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {

  public static void main(String[] args) throws Exception {
    String str = "helloworld";
    //输出流
    FileOutputStream fileOutputStream = new FileOutputStream("E:\\file01.txt");
    //得到通道
    FileChannel fileChannel = fileOutputStream.getChannel();

    //创建ByteBuffer
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    //先写入buffer中
    byteBuffer.put(str.getBytes());

    //开始写入到通道中
    //需要先反转 buffer
    byteBuffer.flip();

    fileChannel.write(byteBuffer);

    fileOutputStream.close();
  }
}

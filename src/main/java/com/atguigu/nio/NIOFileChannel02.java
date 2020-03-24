package com.atguigu.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel案例2：读取文件
 */
public class NIOFileChannel02 {

  public static void main(String[] args) throws Exception {
    //创建文件的输入流
    FileInputStream fileInputStream = new FileInputStream("file02.txt");
    FileChannel fileChannel = fileInputStream.getChannel();

    //创建buffer
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    //读文件
    fileChannel.read(byteBuffer);

    System.out.println(new String(byteBuffer.array()));
    fileInputStream.close();

  }
}

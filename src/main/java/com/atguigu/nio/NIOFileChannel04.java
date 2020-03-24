package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

  public static void main(String[] args) throws Exception {
    //创建流
    FileInputStream fileInputStream = new FileInputStream("a.jpg");
    FileOutputStream fileOutputStream = new FileOutputStream("b.jpg");

    //获取channel
    FileChannel fileInputStreamChannel = fileInputStream.getChannel();
    FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

    //直接拷贝文件
    fileOutputStreamChannel.transferFrom(fileInputStreamChannel, 0, fileInputStreamChannel.size());

    //关闭流
    fileInputStream.close();
    fileOutputStream.close();

  }
}

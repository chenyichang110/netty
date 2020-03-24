package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//先读文件--> 再写到另一个文件，即拷贝文件
public class NIOFileChannel03 {

  public static void main(String[] args) throws Exception {
    FileInputStream fileInputStream = new FileInputStream("1.txt");
    FileOutputStream fileOutputStream = new FileOutputStream("2.txt");

    //得到各自的通道
    FileChannel inputStreamChannel = fileInputStream.getChannel();
    FileChannel outputStreamChannel = fileOutputStream.getChannel();

    //创建buffer
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    while (true) {
      //清空buffer
      byteBuffer.clear();
      //读
      int read = inputStreamChannel.read(byteBuffer);
      System.out.println(read);
      if (read == -1) {
        break;
      }

      //必须反转
      byteBuffer.flip();
      //写
      outputStreamChannel.write(byteBuffer);
    }
    //关闭资源
    fileInputStream.close();
    fileOutputStream.close();
  }

}

package com.atguigu.nio.zerocopy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class OldIOClient {

  public static void main(String[] args) throws IOException {

    Socket socket = new Socket("localhost", 7000);
    String fileName = "dubbox-dubbox-2.8.4.zip";
    FileInputStream fileInputStream = new FileInputStream(fileName);
    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

    byte[] bytes = new byte[4096];
    long read;
    long total = 0;
    long start = System.currentTimeMillis();
    while ((read = fileInputStream.read(bytes)) >= 0) {
      total += read;
      dataOutputStream.write(bytes);
    }
    System.out.println("发送总字节数：" + total + ",耗时：" + (System.currentTimeMillis() - start));

    dataOutputStream.close();
    socket.close();
    fileInputStream.close();
  }

}

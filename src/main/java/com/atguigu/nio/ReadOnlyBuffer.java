package com.atguigu.nio;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

public class ReadOnlyBuffer {

  public static void main(String[] args) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(64);
    for (int i = 0; i < 64; i++) {
      byteBuffer.put((byte) i);
    }

    //读取
    byteBuffer.flip();

    ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
    System.out.println(readOnlyBuffer.getClass());

    while (readOnlyBuffer.hasRemaining()) {
      System.out.println(readOnlyBuffer.get());
    }
    //如果尝试写
    readOnlyBuffer.put(1, (byte) 3);//ReadOnlyBufferException
  }

}

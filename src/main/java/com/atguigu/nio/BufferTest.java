package com.atguigu.nio;

import java.nio.IntBuffer;

public class BufferTest {

  public static void main(String[] args) {
    //使用IntBuffer测试
    IntBuffer intBuffer = IntBuffer.allocate(5);

    for (int i = 0; i < intBuffer.capacity(); i++) {
      intBuffer.put(i * i);
    }

    //要读取数据时，一定要 调用flip() 方法，读写切换
    intBuffer.flip();

    intBuffer.position(1);
    intBuffer.limit(2);

    while (intBuffer.hasRemaining()) {
      System.out.println(intBuffer.get());
    }
  }
}

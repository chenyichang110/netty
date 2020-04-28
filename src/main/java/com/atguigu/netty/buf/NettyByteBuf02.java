package com.atguigu.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import java.nio.charset.StandardCharsets;

public class NettyByteBuf02 {

  public static void main(String[] args) {
    ByteBuf buf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);
    //方法
    if (buf.hasArray()) {
      byte[] content = buf.array();
      System.out.println(new String(content, StandardCharsets.UTF_8));

      System.out.println(buf.arrayOffset());//0
      System.out.println(buf.readerIndex());//0
      System.out.println(buf.writerIndex());//12
      System.out.println(buf.capacity());//36

      System.out.println(buf.getByte(0));// h:104
      //可读的字节数：12
      System.out.println(buf.readableBytes());

      //按照某个范围读取
      System.out.println(buf.getCharSequence(0,4,StandardCharsets.UTF_8));
      System.out.println(buf.getCharSequence(4,4,StandardCharsets.UTF_8));
    }
  }

}

package com.atguigu.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {

  public static void main(String[] args) {

    /**
     * 1.创建对象，该对象包含一个数组arr，是一个byte[10]
     * 2.在netty的ByteBuf中，不需要使用flip进行反转
     *    因为已经在底层维护了readerIndex和writerIndex
     * 3.通过readerIndex和writerIndex和capacity，将buffer分成3个区域
     *    0 ~ readerIndex：已经读取的区域
     *    readerIndex~writeIndex：可读的区域
     *    writeIndex~capacity：可写的区域
     */
    ByteBuf buf = Unpooled.buffer(10);
    for (int i = 0; i < 10; i++) {
      buf.writeByte(i);
    }
    System.out.println();
    for (int i = 0; i < buf.capacity(); i++) {
      System.out.print(buf.readByte());
    }
    //必须要调用 writeByte和readByte 里面维护的索引才会改变
    buf.getByte(5);//这种获取数据，不会改变索引

  }
}

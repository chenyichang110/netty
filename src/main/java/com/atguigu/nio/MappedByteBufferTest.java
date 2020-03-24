package com.atguigu.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MappedByteBufferTest {

  public static void main(String[] args) throws Exception {
    RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

    FileChannel fileChannel = randomAccessFile.getChannel();

    /**
     * 参数1： 使用哪种模式：  read_write 读写模式
     * 参数2； 0:修改的起始位置
     * 参数3： 5:映射到内存的大小（不是索引），即将1.txt的多少个字节映射到内存
     * 可以直接修改的范围是  0 - 4 （正好5个字节）
     * 实际类型是  DirectByteBuffer
     */
    MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 5);

    mappedByteBuffer.put(0,(byte) 'a');

    randomAccessFile.close();

  }

}

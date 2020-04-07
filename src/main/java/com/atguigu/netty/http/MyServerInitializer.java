package com.atguigu.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    //向管道加入处理器
    ChannelPipeline pipeline = socketChannel.pipeline();

    //加入netty提供的HTTPServerCodec(编码和解码器）  codec [ coder and decoder]
    pipeline.addLast("myHttpServerCodec", new HttpServerCodec());

    //加入自己的handler
    pipeline.addLast("myHttpServerHandler", new MyHttpServerHandler());

    System.out.println("is ok");
  }
}

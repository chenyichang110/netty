package com.atguigu.netty.simple.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

  public static void main(String[] args) {
    //客户端也需要一个事件循环组
    NioEventLoopGroup group = new NioEventLoopGroup();

    try {
      //创建客户端启动对象 BootStrap（服务端是 ServerBootstrap）
      Bootstrap bootstrap = new Bootstrap();

      //设置相关参数
      bootstrap.group(group) //设置线程组
          .channel(NioSocketChannel.class) //设置客户端通道的实现类
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(new NettyClientHandler());//加入自己的实现器
            }
          });
      System.out.println("客户端 is ok");

      //启动客户端连接服务器
      ChannelFuture channelFuture = bootstrap.connect("localhost", 6668).sync();
      //关闭通道进行监听
      channelFuture.channel().closeFuture().sync();
    } catch (Exception e) {

    } finally {
      group.shutdownGracefully();
    }
  }
}

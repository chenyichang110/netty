package com.atguigu.netty.simple.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;

public class NettyServer {

  public static void main(String[] args) {

    //创建BossGroup 和WorkerGroup
    //bossGroup只处理连接请求，workerGroup才处理业务
    //都是无限循环
    //NioEventLoopGroup的含有子线程个数默认为 cpu核数*2
    /**
     *
     * protected MultithreadEventLoopGroup(int nThreads, Executor executor, Object... args) {
     *     super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor, args);
     *   }
     *
     *  private static final int DEFAULT_EVENT_LOOP_THREADS =
     *  Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
     */

    System.out.println("当前电脑cpu核数：" + NettyRuntime.availableProcessors());
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();

    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      //创建服务器端启动对象，并配置参数
      ServerBootstrap bootstrap = new ServerBootstrap();
      //使用链式编程
      bootstrap.group(bossGroup, workerGroup)//设置2个线程组
          .channel(NioServerSocketChannel.class)//使用NIOServerSocketChannel作为服务器的通道实现
          .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接个数
          .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
//          .handler(null)//该handler对应bossGroup,childHandler对应workerGroup
          .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象（匿名对象）

            //给pipeline设置处理器
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

              socketChannel.pipeline().addLast(new NettyServerHandler());
            }
          });

      System.out.println("服务器 is ready...");
      //绑定一个端口并同步，生成一个ChannelFuture对象
      ChannelFuture channelFuture = bootstrap.bind(6668).sync();

      //给channelFuture注册监听器，监控我们关心的事件
      channelFuture.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
          if (channelFuture.isSuccess()) {
            System.out.println("监听 端口 6668 成功");
          } else {
            System.out.println("监听 端口 6668 失败");
          }
        }
      });

      //对关闭通道进行监听
      channelFuture.channel().closeFuture().sync();

    } catch (Exception e) {

    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }

  }

}

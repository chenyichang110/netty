package com.atguigu.netty.simple.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import java.util.concurrent.TimeUnit;

/**
 * 自定义一个Handler，需要继承某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

  //读取数据（读取客户端发送的消息）

  /**
   *
   * @param ctx ：上下文对象，含有管道pipeline
   * @param msg ：客户端发送的数据，默认是Object类型
   * @throws Exception
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    //如果这里有一个耗时非常长的业务
    //处理方法： 异步执行  NioEventLoop 里面的taskQueue

    //方案一：用户程序自定义的普通任务（队列里是一个线程执行）
    ctx.channel().eventLoop().execute(new Runnable() {
      @Override
      public void run() {
        try {
          //模拟处理业务需要5秒
          Thread.sleep(5 * 1000);
          ctx.writeAndFlush(Unpooled.copiedBuffer("我处理了5秒", CharsetUtil.UTF_8));
          System.out.println("channel code=" + ctx.channel().hashCode());
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    });

    ctx.channel().eventLoop().execute(new Runnable() {
      @Override
      public void run() {
        try {
          //模拟处理业务花费了10秒
          Thread.sleep(10 * 1000);
          ctx.writeAndFlush(Unpooled.copiedBuffer("我处理了10秒", CharsetUtil.UTF_8));
          System.out.println("channel code=" + ctx.channel().hashCode());
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    });

    //解决方案2：用户自定义定时任务 -> 该任务提交到scheduleTaskQueue
    ctx.channel().eventLoop().schedule(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(5 * 1000);
          ctx.writeAndFlush(Unpooled.copiedBuffer("scheduleTaskQueue处理了5秒", CharsetUtil.UTF_8));
          System.out.println("channel code=" + ctx.channel().hashCode());
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }, 5, TimeUnit.SECONDS);

    System.out.println("服务器读取线程：" + Thread.currentThread().getName() + "channel=" + ctx.channel());
    System.out.println("server ctx = " + ctx);
    System.out.println("channel 和 pipeline的关系");
    Channel channel = ctx.channel();
    ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表

    //将msg转成一个ByteBuf
    ByteBuf buf = (ByteBuf) msg;
    System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
    System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());
  }


  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //writeAndFlush是write + flush
    //将数据写入到缓存，并刷新，一般的，都需要将发送的数据进行编码
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端:喵", CharsetUtil.UTF_8));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }

}

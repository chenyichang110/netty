package com.atguigu.netty.http;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import java.net.URI;

/**
 * 1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * 2.httpObject 是 客户端和服务器端相互通讯的数据的封装类型
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

  //读取客户端数据
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg)
      throws Exception {

    System.out.println("对应的channel=" + channelHandlerContext.channel());
    System.out.println("channel对应的pipeline=" + channelHandlerContext.pipeline());
    System.out.println("通过pipeline获取channel=" + channelHandlerContext.pipeline().channel());
    System.out.println("当前context的handler=" + channelHandlerContext.handler());

    //判断msg是否是httprequest请求
    if (msg instanceof HttpRequest) {
      System.out.println("msg类型：" + msg.getClass());
      System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());

      HttpRequest httpRequest = (HttpRequest) msg;
      //获取URI
      URI uri = new URI(httpRequest.uri());
      System.out.println(uri.getPath());
      if ("/favicon.ico".equals(uri.getPath())) {
        System.out.println("请求了favicon.ico，不做响应");
        return;
      }
      //回复消息给浏览器[http协议]
      ByteBuf content = Unpooled.copiedBuffer("hello,nice to meet you", CharsetUtil.UTF_8);

      //构建一个http的响应
      DefaultFullHttpResponse response = new DefaultFullHttpResponse(
          HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
      response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

      //将response 返回
      channelHandlerContext.writeAndFlush(response);

    }
  }
}

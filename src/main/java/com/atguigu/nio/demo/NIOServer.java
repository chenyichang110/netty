package com.atguigu.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

  public static void main(String[] args) throws Exception {

    //创建ServerSocketChannel
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

    //创建Selector对象
    Selector selector = Selector.open();
    //设置为非阻塞
    serverSocketChannel.configureBlocking(false);

    //将 serverSocketChannel  注册到 selector 上,并指定事件为：接收连接请求事件
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    //绑定端口
    serverSocketChannel.bind(new InetSocketAddress(6666));

    System.out.println("注册后的SelectionKey数量= " + selector.keys().size());

    //等待连接
    while (true) {

      //等1s是否有连接事件
      if (selector.select(1000) == 0) {
        System.out.println("等待了1s，无连接");
        continue;
      }

      //有连接，得到该连接事件
      Set<SelectionKey> selectionKeys = selector.selectedKeys();

      //遍历
      Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

      while (selectionKeyIterator.hasNext()) {
        SelectionKey selectionKey = selectionKeyIterator.next();

        //根据事件的类型做处理
        if (selectionKey.isAcceptable()) {//请求连接事件
          //开始连接
          //该方法虽然是阻塞方法，但是经过上面代码的层层逻辑，就是连接，不需要阻塞在这里
          SocketChannel socketChannel = serverSocketChannel.accept();
          System.out.println("客户端连接成功：" + socketChannel);
          //设置为非阻塞
          socketChannel.configureBlocking(false);
          //并注册到选择器上,并关联一个ByteBuffer，关心事件为 读 事件
          socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        }

        if (selectionKey.isReadable()) {//读事件
          //反向获取关联channel
          SocketChannel channel = (SocketChannel) selectionKey.channel();
          //反向获取关联buffer
          ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

          //开始读
          channel.read(buffer);

          System.out.println("服务器收到客户端消息：" + new String(buffer.array()));
        }

        //手动移除当前 selectionKey  ，防止重复操作
        selectionKeyIterator.remove();
      }

    }

  }
}

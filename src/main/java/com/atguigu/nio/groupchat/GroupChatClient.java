package com.atguigu.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊系统 客户端
 */
public class GroupChatClient {

  private final String HOST = "127.0.0.1";
  private final int PORT = 6666;

  private Selector selector;
  private SocketChannel socketChannel;
  private String username;

  //构造器
  public GroupChatClient() throws IOException {
    selector = Selector.open();
    socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
    socketChannel.configureBlocking(false);
    socketChannel.register(selector, SelectionKey.OP_READ);
    username = socketChannel.getLocalAddress().toString().substring(1);
    System.out.println(username + "is ok");
  }

  //向服务器发送消息
  public void sendInfo(String info) throws IOException {
    info = username + "说：" + info;
    socketChannel.write(ByteBuffer.wrap(info.getBytes()));
  }

  //读取服务器发来的消息
  public void readInfo() {
    try {
      int count = selector.select();
      if (count > 0) {
        //有事件
        Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
        while (selectionKeyIterator.hasNext()) {
          SelectionKey selectionKey = selectionKeyIterator.next();
          selectionKeyIterator.remove();
          if (selectionKey.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array()));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {

    //启动500个线程 ，每500ms向服务器读消息
    for (int i = 0; i < 500; i++) {
      GroupChatClient client = new GroupChatClient();
      new Thread(() -> {
        while (true) {
          client.readInfo();
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }).start();

      new Thread(() -> {
        try {
          while (true) {
            client.sendInfo("我0.5s发一个");
            Thread.sleep(500);
          }
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      }).start();

    }

  }


  public void test() throws IOException {
    ServerSocketChannel bind = ServerSocketChannel.open();
    bind.bind(new InetSocketAddress(8080));
    SocketChannel so = SocketChannel.open();
    so.connect(new InetSocketAddress("172.16.120.5", 8080));
    System.out.println(so.getLocalAddress());
    System.out.println(so.getRemoteAddress());
  }
}

package com.atguigu.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 群聊 系统 服务器端
 */
public class GroupChatServer {

  private Selector selector;
  private ServerSocketChannel listenChannel;
  private static final int PORT = 6666;

  //初始化属性
  public GroupChatServer() {
    try {
      //选择器
      selector = Selector.open();
      listenChannel = ServerSocketChannel.open();
      //绑定端口
      listenChannel.bind(new InetSocketAddress(PORT));
      listenChannel.configureBlocking(false);

      //注册到选择器上
      listenChannel.register(selector, SelectionKey.OP_ACCEPT);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //监听
  public void listen() {
    System.out.println("监听线程启动：" + Thread.currentThread().getName());
    try {
      while (true) {
        int count = selector.select();
        if (count > 0) {//有事件需要处理
          //得到事件集合
          Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
          //遍历
          while (selectionKeyIterator.hasNext()) {
            SelectionKey selectionKey = selectionKeyIterator.next();

            //开始判断是什么事件
            if (selectionKey.isAcceptable()) {//请求连接事件
              //开始连接
              SocketChannel socketChannel = listenChannel.accept();
              socketChannel.configureBlocking(false);
              //注册
              socketChannel.register(selector, SelectionKey.OP_READ);
              //提示该用户上线
              System.out.println(socketChannel.getRemoteAddress() + "上线");
            }

            if (selectionKey.isReadable()) {//读事件
              //服务器收到可读取的消息，并且需要转发到其他的通道
              readDataAndSendToOther(selectionKey);
            }

            //删除该事件
            selectionKeyIterator.remove();
          }
        } else {
          System.out.println("暂时没有消息");
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 读取通道的消息，并转发给其他通道
   * @param selectionKey
   */
  private void readDataAndSendToOther(SelectionKey selectionKey) {
    SocketChannel socketChannel = null;
    try {
      //得到channel
      socketChannel = (SocketChannel) selectionKey.channel();
      //创建buffer
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
      int count = socketChannel.read(byteBuffer);
      if (count > 0) {
        String msg = new String(byteBuffer.array());
        System.out.println("服务器收到 来自" + socketChannel.getRemoteAddress() + "的消息：" + msg);

        //服务器需要转发 给其他的通道
        sendToOther(msg, socketChannel);
      }

    } catch (Exception e) {
      //代码离线了
      try {
        System.out.println(socketChannel.getRemoteAddress() + "离线了");
        //取消注册
        selectionKey.cancel();
        //关闭通道
        socketChannel.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * 转发给除了自己的其他的所有通道
   * @param msg
   * @param selfChannel
   */
  private void sendToOther(String msg, SocketChannel selfChannel) throws IOException {
    System.out.println("服务器转发消息中：");

    //遍历注册到selector的所有socketChannel，并排除selfChannel
    for (SelectionKey selectionKey : selector.keys()) {
      Channel targetChannel = selectionKey.channel();
      //排除自己
      if (targetChannel instanceof SocketChannel && targetChannel != selfChannel) {
        SocketChannel socketChannel = (SocketChannel) targetChannel;
        //发送消息
        socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
      }
    }
  }

  public static void main(String[] args) {
    //启动服务器
    GroupChatServer groupChatServer = new GroupChatServer();
    groupChatServer.listen();
  }
}

package com.atguigu.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OldIOServer {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(7000);
    while (true) {
      Socket socket = serverSocket.accept();
      DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
      try {
        byte[] bytes = new byte[4096];

        while (true) {
          int read = dataInputStream.read(bytes, 0, bytes.length);
          if (read == -1) {
            break;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

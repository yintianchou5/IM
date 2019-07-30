package org.dzq.im.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.dzq.im.thread.MyThread;

/**
 * echo服务器 功能：将客户端发送的内容反馈给客户端
 */
public class IMServer {
	public static void main(String[] args) {
		Map<String, Socket> userMap = new HashMap<String, Socket>();
		// 监听端口号
		int port = 10085;
		try {
			// 建立连接
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("服务器端启动.....");
			while (true) {
				// 获得连接
				Socket socket = serverSocket.accept();
				System.out.println("服务器端获得连接.....");
				// 接收客户端发送内容
				MyThread st = new MyThread();
				st.setSocket(socket);
				st.setMap(userMap);
				st.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
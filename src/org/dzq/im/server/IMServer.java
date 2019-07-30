package org.dzq.im.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.dzq.im.thread.MyThread;

/**
 * echo������ ���ܣ����ͻ��˷��͵����ݷ������ͻ���
 */
public class IMServer {
	public static void main(String[] args) {
		Map<String, Socket> userMap = new HashMap<String, Socket>();
		// �����˿ں�
		int port = 10085;
		try {
			// ��������
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("������������.....");
			while (true) {
				// �������
				Socket socket = serverSocket.accept();
				System.out.println("�������˻������.....");
				// ���տͻ��˷�������
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
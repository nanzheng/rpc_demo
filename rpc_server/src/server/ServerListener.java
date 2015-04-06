package server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;


import communication.MyProtocol;

public class ServerListener {
	private int port = 8000;
	private ServerSocket serverSocket;

	public ServerListener() throws IOException {
		serverSocket = new ServerSocket(port, 3); // ����������еĳ���Ϊ3
		System.out.println("����������");
	}

	// byte[]תint


	public void service() throws ClassNotFoundException, SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept(); // ���������������ȡ��һ��
				System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
				
				InputStream inputStream= socket.getInputStream();
				String[] argStr = MyProtocol.serverRec(inputStream);
				
				String className = argStr[0];
				Class<?> classType = Class.forName(className);
				Object obj = classType.newInstance();
				Method method = classType.getMethod(argStr[1]);
				method.invoke(obj);
				
				OutputStream outStream= socket.getOutputStream();
				MyProtocol.serverSent(outStream, "welcome to party");

				inputStream.close();
				outStream.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
		
		ServerListener server = new ServerListener();
		
		server.service();
	}
}
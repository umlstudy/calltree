package com.sample.calltree.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	static ServerSocket serverSocket;
	protected final static int port = 19999;
	static Socket socketConnection;

	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("ServerSocket Initialized");

			while (true) {
				socketConnection = serverSocket.accept();
				BufferedInputStream is = new BufferedInputStream(socketConnection.getInputStream());
				BufferedOutputStream os = new BufferedOutputStream(socketConnection.getOutputStream());
				// 수신처리
				while ( true ) {
					recevingPacket(is);
					handlePacket(os);
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
			}
		}
	}

	private static void handlePacket(BufferedOutputStream os) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
		osw.write("OK");
		osw.flush();
	}

	private static String recevingPacket(BufferedInputStream is) throws IOException {
		StringBuffer process;
		int character;
		String TimeStamp;
		InputStreamReader isr = new InputStreamReader(is);
		process = new StringBuffer();
		while ((character = isr.read()) != 13) {
			process.append((char) character);
		}
		System.out.println(process);
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
		}
		TimeStamp = new java.util.Date().toString();
		return "SocketServer repsonded at " + TimeStamp + (char) 13;
		
	}
}
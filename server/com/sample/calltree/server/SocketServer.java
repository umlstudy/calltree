package com.sample.calltree.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

import com.sample.calltree.packet.Packet;
import com.sample.calltree.packet.body.LoginRequest;
import com.sample.calltree.packet.socket.ReceviedPacketHandler;
import com.sample.calltree.packet.socket.SocketHandler;

public class SocketServer {

	public final static int PORT = 19999;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("ServerSocket Initialized");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				SocketHandler socketHandler = null;
				socketHandler = SocketHandler.newInstance(clientSocket);
				final SocketHandler fSocketHandler = socketHandler;
				socketHandler.setPacketHandler(new ReceviedPacketHandler() {
					@Override
					public void packetReceived(Packet receivedPacket) throws Exception {
						SocketServer.packetReceived(fSocketHandler, receivedPacket);
					}
				});
				Thread socketThread = new Thread(socketHandler);
				socketThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(serverSocket);
		}
	}

//	private static void handlePacket(BufferedOutputStream os) throws IOException {
//		OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
//		osw.write("OK");
//		osw.flush();
//	}
//
//	private static String recevingPacket(BufferedInputStream is) throws IOException {
//		StringBuffer process;
//		int character;
//		String TimeStamp;
//		InputStreamReader isr = new InputStreamReader(is);
//		process = new StringBuffer();
//		while ((character = isr.read()) != 13) {
//			process.append((char) character);
//		}
//		System.out.println(process);
//		try {
//			Thread.sleep(10000);
//		} catch (Exception e) {
//		}
//		TimeStamp = new java.util.Date().toString();
//		return "SocketServer repsonded at " + TimeStamp + (char) 13;
//		
//	}

	private static void packetReceived(SocketHandler socketHandler, Packet receivedPacket) throws IOException {

		switch ( receivedPacket.getMessageId() ) {
		case REQ_LOGIN :
			LoginRequest loginRequest = (LoginRequest)receivedPacket.getBody();
			if ( "test".equals(loginRequest.getLoginId()) && "test".equals(loginRequest.getLoginPassword() ) ) {
				Packet resPacket = Packet.createResPacket(receivedPacket, null);
				socketHandler.send(resPacket);
			} else {
				Packet resPacket = Packet.errorResPacket(receivedPacket, 011, "로그인 ID 및 암호를 확인하세요.");
				socketHandler.send(resPacket);
			}
					
			break;
		}
	}
}
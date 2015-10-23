package com.sample.calltree.packet.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.HexDump;
import org.apache.commons.io.IOUtils;

import com.sample.calltree.packet.Packet;

public class SocketHandler implements Closeable, Runnable {

	private InputStream inputStream;
	private OutputStream outputStream;
	private Socket socket;
	private ReceviedPacketHandler receivedPacketHandler;
	private IOException excepion;

	private SocketHandler(Socket socket, ReceviedPacketHandler packetHandler) throws IOException {
		this.socket = socket;
		this.setPacketHandler(packetHandler);
//		inputStream = socket.getInputStream();
//		outputStream = socket.getOutputStream();
		inputStream = new BufferedInputStream(socket.getInputStream());
		outputStream = new BufferedOutputStream(socket.getOutputStream());
	}
	
	public static final SocketHandler newInstance(Socket socket, ReceviedPacketHandler packetHandler) throws IOException {
		return new SocketHandler(socket, packetHandler);
	}
	
	public static final SocketHandler newInstance(Socket socket) throws IOException {
		return new SocketHandler(socket, null);
	}

	@Override
	public void close() throws IOException {
		IOUtils.closeQuietly(socket);
	}
	
	public void send(Packet packet) throws IOException {
		byte[] bytes = packet.getBytes();
		System.out.println("패킷 전송 시작 : " + packet.getMessageId().toString() );
		System.out.println("패킷 전송 시작 : " + packet.getResType().toString() );
		HexDump.dump(bytes, 0, System.out, 0);
		outputStream.write(bytes);
		outputStream.flush();
		System.out.println("패킷 전송 종료 : " + packet.getMessageId().toString() );
	}
	
	public Packet receive() throws IOException {
		return Packet.receive(inputStream);
	}
	
	@Override
	public void run() {
		try {
			Packet packet;
			while ( true ) {
				try {
					System.out.println("패킷 수신 대기...");
					packet = receive();
					System.out.println(packet.toString());
					HexDump.dump(packet.getBytes(), 0, System.out, 0);
					System.out.println("패킷 수신 완료...");
					System.out.println("패킷 처리 시작...");
					getReceivedPacketHandler().packetReceived(packet);
					System.out.println("패킷 처리 종료...");
				} catch (IOException e) {
					e.printStackTrace();
					this.excepion = e;
					break;
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(this);
		}
	}

	public IOException getExcepion() {
		return excepion;
	}

	private ReceviedPacketHandler getReceivedPacketHandler() {
		return receivedPacketHandler;
	}

	public void setPacketHandler(ReceviedPacketHandler receivedPacketHandler) {
		this.receivedPacketHandler = receivedPacketHandler;
	}
}

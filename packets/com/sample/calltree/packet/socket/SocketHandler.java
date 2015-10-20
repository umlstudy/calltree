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
	private PacketHandler packetHandler;
	private IOException excepion;

	private SocketHandler(Socket socket, PacketHandler packetHandler) throws IOException {
		this.socket = socket;
		this.setPacketHandler(packetHandler);
		inputStream = new BufferedInputStream(socket.getInputStream());
		outputStream = new BufferedOutputStream(socket.getOutputStream());
	}
	
	public static final SocketHandler newInstance(Socket socket, PacketHandler packetHandler) throws IOException {
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
		HexDump.dump(bytes, 0, System.out, 0);
		outputStream.write(bytes);
		outputStream.flush();
	}
	
	private Packet receive() throws IOException {
		return Packet.receive(inputStream);
	}
	
	@Override
	public void run() {
		Packet packet;
		while ( true ) {
			try {
				packet = receive();
				getPacketHandler().handlePacket(packet);
			} catch (IOException e) {
				e.printStackTrace();
				this.excepion = e;
				break;
			}
		}
	}

	public IOException getExcepion() {
		return excepion;
	}

	public PacketHandler getPacketHandler() {
		return packetHandler;
	}

	public void setPacketHandler(PacketHandler packetHandler) {
		this.packetHandler = packetHandler;
	}
}

package com.sample.calltree.packet;

import java.io.IOException;
import java.io.InputStream;

import com.sample.calltree.packet.enums.ConnectorType;
import com.sample.calltree.packet.enums.MessageId;
import com.sample.calltree.packet.enums.ResType;
import com.sample.calltree.packet.enums.ReturnCode;

public class Packet {
	
//	private int packetLength;
//	private ConnectorType connType;
	private ResType resType;
	private MessageId messageId;
	private ReturnCode returnCode;

	// private int packetBodyLenght;

	private Packet() {
	}
	
	public static Packet createReqPacket(MessageId messageId) {
		Packet packet = new Packet();
		packet.setResType(ResType.REQ);
		packet.setMessageId(messageId);
		packet.setReturnCode(ReturnCode.SUCCESS);
		
		// TODO BODY SETTING
		
		return packet;
	}

	public static Packet receive(InputStream is) throws IOException {
		Packet packet = new Packet();

		// 1.패킷랭스 무시
		readInt(is, 8);
		// 2.접속처구분
		ConnectorType connType = ConnectorType.fromTypeValue(readInt(is, 2));
		if ( connType != ConnectorType.Client ) {
			throw new RuntimeException();
		}
		// 3.응답구분
		packet.setResType(ResType.fromTypeValue(readInt(is, 2)));
		// 4.메시지ID
		packet.setMessageId(MessageId.fromIdValue(readInt(is, 2)));
		// 5.성공실패
		packet.setReturnCode(ReturnCode.fromCodeValue(readInt(is, 1)));
		// 6.바디패킷크기
		int bodyPacketLen = readInt(is, 8);
		// 7.바디패킷크기
		String bodyString = readString(is, bodyPacketLen, "UTF-8");
		
		//gson 설정 TODO
		
		return packet;
	}

	private static int readInt(InputStream is, final int len) throws IOException {
		int retVal;
		byte[] fromSocket = new byte[len];
		is.read(fromSocket, 0, fromSocket.length);
		String intStrVal = new String(fromSocket, "ISO-8859-1");
		intStrVal = intStrVal.trim();
		retVal = Integer.parseInt(intStrVal);
		return retVal;
	}
	
	private static String readString(InputStream is, final int len, String encType) throws IOException {
		int retVal;
		byte[] fromSocket = new byte[len];
		is.read(fromSocket, 0, fromSocket.length);
		return new String(fromSocket, encType);
	}

//	public ConnectorType getConnType() {
//		return connType;
//	}
//
//	public void setConnType(ConnectorType connType) {
//		this.connType = connType;
//	}

//	public PacketType getPacketType() {
//		return packetType;
//	}
//
//	public void setPacketType(PacketType packetType) {
//		this.packetType = packetType;
//	}

	public MessageId getMessageId() {
		return messageId;
	}

	public void setMessageId(MessageId messageId) {
		this.messageId = messageId;
	}

	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	public ResType getResType() {
		return resType;
	}

	public void setResType(ResType resType) {
		this.resType = resType;
	}
}

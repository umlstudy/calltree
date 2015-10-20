package com.sample.calltree.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.sample.calltree.packet.enums.ConnectorType;
import com.sample.calltree.packet.enums.MessageId;
import com.sample.calltree.packet.enums.ResType;
import com.sample.calltree.packet.enums.ReturnCode;

public class Packet {
	
	private ResType resType;
	private MessageId messageId;
	private ReturnCode returnCode;
	private BodyPacketBase body;
	
	private static final int INT_PACKET_LENGTH = 8;

	private static final Gson GSON = new Gson();

	private Packet() {
	}
	
	public static Packet createReqPacket(MessageId messageId, BodyPacketBase body) {
		Packet packet = new Packet();
		packet.setResType(ResType.REQ);
		packet.setMessageId(messageId);
		packet.setReturnCode(ReturnCode.SUCCESS);
		packet.setBody(body);
		
		return packet;
	}
	
	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		// 2. 접속처
		writeInt(baos, ConnectorType.Client.getTypeValue(), ConnectorType.LENGTH);
		// 3. 응답구분
		writeInt(baos, getResType().getTypeValue(), ResType.LENGTH);
		// 4.메시지ID
		writeInt(baos, getMessageId().getIdValue(), MessageId.LENGTH);
		// 5.성공실패
		writeInt(baos, getReturnCode().getCodeValue(), ReturnCode.LENGTH);

		String jsonBody = GSON.toJson(body, BodyPacketBase.class);
		byte[] jsonBodyBytes = jsonBody.getBytes(Charset.forName("UTF-8"));
		// 6.바디길이
		writeInt(baos, jsonBodyBytes.length, INT_PACKET_LENGTH);
		// 7.바디
		baos.write(jsonBodyBytes, 0, jsonBodyBytes.length);
		
		// 1.패킷랭스
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] byteArray = baos.toByteArray();
		writeInt(result, byteArray.length, INT_PACKET_LENGTH);
		result.write(byteArray, 0, byteArray.length);
		
		return result.toByteArray();
	}

	public static Packet receive(InputStream is) throws IOException {
		Packet packet = new Packet();

		// 1.패킷랭스 무시
		readInt(is, INT_PACKET_LENGTH);
		// 2.접속처구분
		ConnectorType connType = ConnectorType.fromTypeValue(readInt(is, ConnectorType.LENGTH));
		if ( connType != ConnectorType.Client ) {
			throw new RuntimeException();
		}
		// 3.응답구분
		packet.setResType(ResType.fromTypeValue(readInt(is, ResType.LENGTH)));
		// 4.메시지ID
		packet.setMessageId(MessageId.fromIdValue(readInt(is, MessageId.LENGTH)));
		// 5.성공실패
		packet.setReturnCode(ReturnCode.fromCodeValue(readInt(is, ReturnCode.LENGTH)));
		// 6.바디패킷크기
		int bodyPacketLen = readInt(is, INT_PACKET_LENGTH);
		// 7.바디패킷
		String bodyString = readString(is, bodyPacketLen, "UTF-8");
		// 8.바디
		BodyPacketBase body = GSON.fromJson(bodyString, BodyPacketBase.class);
		packet.setBody(body);
		
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
		byte[] fromSocket = new byte[len];
		is.read(fromSocket, 0, fromSocket.length);
		return new String(fromSocket, encType);
	}
	
	private static void writeInt(OutputStream os, int val, int len) throws IOException {
		String formatString = String.format("\\%%dd", len);
		String strVal = String.format(formatString, val);
		os.write(strVal.getBytes(Charset.forName("ISO-8859-1")));
	}

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

	public BodyPacketBase getBody() {
		return body;
	}

	public void setBody(BodyPacketBase body) {
		this.body = body;
	}
}

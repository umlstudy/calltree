package com.sample.calltree.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.sample.calltree.packet.body.Error;
import com.sample.calltree.packet.body.JobList;
import com.sample.calltree.packet.body.LoginRequest;
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

	public static Packet errorResPacket(Packet reqPacket, int errorCode, String errorMsg) {
		return createResPacket(reqPacket, new Error(errorCode, errorMsg), ReturnCode.FAIL);
	}
	
	public static Packet createResPacket(Packet reqPacket, BodyPacketBase body) {
		return createResPacket(reqPacket, body, ReturnCode.SUCCESS);
	}
	
	public static Packet createResPacket(Packet reqPacket, BodyPacketBase body, ReturnCode rc) {
		Packet resPacket = new Packet();
		resPacket.setResType(ResType.RES);
		resPacket.setMessageId(reqPacket.getMessageId());
		resPacket.setReturnCode(rc);
		resPacket.setBody(body);
		
		return resPacket;
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

		if ( body != null ) {
			String jsonBody = GSON.toJson(body, body.getClass());
			byte[] jsonBodyBytes = jsonBody.getBytes(Charset.forName("UTF-8"));
			// 6.바디길이
			writeInt(baos, jsonBodyBytes.length, INT_PACKET_LENGTH);
			// 7.바디
			baos.write(jsonBodyBytes, 0, jsonBodyBytes.length);
		} else {
			writeInt(baos, 0, INT_PACKET_LENGTH);
		}
		
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
		int packetlen = readInt(is, INT_PACKET_LENGTH);
		System.out.println("PACKETLEN:"+packetlen);
		// 2.접속처구분
		ConnectorType connType = ConnectorType.fromTypeValue(readInt(is, ConnectorType.LENGTH));
		if ( connType != ConnectorType.Client ) {
			throw new RuntimeException();
		}
		System.out.println("ConnectorType:"+connType);

		// 3.응답구분
		packet.setResType(ResType.fromTypeValue(readInt(is, ResType.LENGTH)));
		System.out.println("ResType:"+packet.getResType());

		// 4.메시지ID
		packet.setMessageId(MessageId.fromIdValue(readInt(is, MessageId.LENGTH)));
		System.out.println("MessageId:"+packet.getMessageId());
		
		// 5.성공실패
		packet.setReturnCode(ReturnCode.fromCodeValue(readInt(is, ReturnCode.LENGTH)));
		System.out.println("ReturnCode:"+packet.getReturnCode());

		// 6.바디패킷크기
		int bodyPacketLen = readInt(is, INT_PACKET_LENGTH);
		System.out.println("bodyPacketLen:"+bodyPacketLen);

		// 7.바디패킷
		packet.setBody(null);
		if ( bodyPacketLen > 0 ) {
			String bodyString = readString(is, bodyPacketLen, "UTF-8");
			// 8.바디
			BodyPacketBase body = null;
			if ( packet.getReturnCode() == ReturnCode.FAIL ) {
				body = GSON.fromJson(bodyString, Error.class);
			} else {
				switch ( packet.getMessageId() ) {
				case REQ_LOGIN :
					body = GSON.fromJson(bodyString, LoginRequest.class);
					break;
				case REQ_JOBLIST :
					body = GSON.fromJson(bodyString, JobList.class);
					break;
				default :
					throw new RuntimeException();
				}
			}
			packet.setBody(body);
		}
		
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
		String formatString = String.format("%%%dd", len);
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
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("ResType : ").append(getResType()).append("\n");
		sb.append("MessageId : ").append(getMessageId()).append("\n");
		sb.append("ReturnCode : ").append(getReturnCode()).append("\n");
		if ( getBody() != null ) {
			sb.append("Body : ").append(getBody().toString()).append("\n");
		} else {
			sb.append("Body : ").append("null").append("\n");
		}
		
		return sb.toString();
	}
}

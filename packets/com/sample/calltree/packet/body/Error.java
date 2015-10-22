package com.sample.calltree.packet.body;

import com.sample.calltree.packet.BodyPacketBase;

public class Error extends BodyPacketBase {
	
	private int errorCode;
	private String errorMsg;
	
	public Error(int errorCode, String errorMsg) {
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	public int getErrorCode() {
		return errorCode;
	}

	private void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	private void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}

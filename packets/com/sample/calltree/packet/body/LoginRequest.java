package com.sample.calltree.packet.body;

import com.sample.calltree.packet.BodyPacketBase;

public class LoginRequest extends BodyPacketBase {
	
	private String loginId;
	private String loginPassword;
	
	public LoginRequest(String loginId, String loginPassword) {
		this.loginId = loginId;
		this.loginPassword = loginPassword;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}
}

package com.sample.calltree.packet.enums;

/**
 * 리턴코드
 * 
 * @author fffffff
 *
 */
public enum ReturnCode {
	SUCCESS(0),
	FAIL(1);
	
	private int codeValue;
	
	private ReturnCode(int value) {
		this.setCodeValue(value);
	}

	public int getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(int codeValue) {
		this.codeValue = codeValue;
	}
	
	public static ReturnCode fromCodeValue(int codeValue) {
		for ( ReturnCode code : ReturnCode.values() ) {
			if ( code.getCodeValue() == codeValue ) {
				return code;
			}
		}
		throw new RuntimeException();
	}
}

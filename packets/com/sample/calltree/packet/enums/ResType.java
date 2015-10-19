package com.sample.calltree.packet.enums;

/**
 * 응답구분
 * 
 * @author fffffff
 *
 */
public enum ResType {
	REQ(10),
	RES(20),
	PUSH(30);
	
	private int typeValue;
	
	private ResType(int value) {
		this.setTypeValue(value);
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}
	
	public static ResType fromTypeValue(int typeValue) {
		for ( ResType type : ResType.values() ) {
			if ( type.getTypeValue() == typeValue ) {
				return type;
			}
		}
		throw new RuntimeException();
	}
}

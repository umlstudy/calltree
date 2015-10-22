package com.sample.calltree.packet.enums;

public enum JobStatusType {
	RUNNING(10),
	HOLD(10),
	STOPPED(20);
	
	private int typeValue;
	
	private JobStatusType(int value) {
		this.setTypeValue(value);
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}
	
	public static JobStatusType fromTypeValue(int typeValue) {
		for ( JobStatusType type : JobStatusType.values() ) {
			if ( type.getTypeValue() == typeValue ) {
				return type;
			}
		}
		throw new RuntimeException();
	}
}

package com.sample.calltree.packet.enums;

public enum JobStatus {
	RUNNING(10),
	HOLD(10),
	STOPPED(20);
	
	private int typeValue;
	
	private JobStatus(int value) {
		this.setTypeValue(value);
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}
	
	public static JobStatus fromTypeValue(int typeValue) {
		for ( JobStatus type : JobStatus.values() ) {
			if ( type.getTypeValue() == typeValue ) {
				return type;
			}
		}
		throw new RuntimeException();
	}
}

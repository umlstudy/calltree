package com.sample.calltree.packet.enums;

public enum ConnectorType {
	Client(10),
	Agent(20);
	
	private int typeValue;
	
	private ConnectorType(int value) {
		this.setTypeValue(value);
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}
	
	public static ConnectorType fromTypeValue(int typeValue) {
		for ( ConnectorType type : ConnectorType.values() ) {
			if ( type.getTypeValue() == typeValue ) {
				return type;
			}
		}
		throw new RuntimeException();
	}
}

package com.sample.calltree.packet.enums;

public enum MessageId {
	REQ_JOBLIST(1120),
	REQ_CONFIRM(1130),
	REQ_JOBSTATUS(1140),
	REQ_HOLD(1150);
	
	private int idValue;
	
	private MessageId(int value) {
		this.setIdValue(value);
	}

	public int getIdValue() {
		return idValue;
	}

	public void setIdValue(int idValue) {
		this.idValue = idValue;
	}
	
	public static MessageId fromIdValue(int idValue) {
		for ( MessageId id : MessageId.values() ) {
			if ( id.getIdValue() == idValue ) {
				return id;
			}
		}
		throw new RuntimeException();
	}
}

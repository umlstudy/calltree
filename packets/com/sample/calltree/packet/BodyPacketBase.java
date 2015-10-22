package com.sample.calltree.packet;

import com.google.gson.Gson;

public abstract class BodyPacketBase {
	
	private static final Gson GSON = new Gson();
	
	public String toString() {
		return GSON.toJson(this, this.getClass());
	}
}

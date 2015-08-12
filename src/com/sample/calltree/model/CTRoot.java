package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sample.calltree.model.listener.CTRootListener;

public class CTRoot extends CTContainer {

	private List<CTConnection> connections;
	
	public CTRoot(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	public List<CTConnection> getConnections() {
		if ( connections == null ) {
			return Collections.EMPTY_LIST;
		}
		return connections;
	}
	
	public boolean addConnection(CTConnection connection) {
		if ( connections == null ) {
			connections = new ArrayList<CTConnection>();
		}
		boolean rslt =  connections.add(connection);
		
		if ( !isDoNotNotify() && getModelUpdateListener() != null ) {
			((CTRootListener)getModelUpdateListener()).connAdded(connection);
		}
		
		return rslt;
	}
}

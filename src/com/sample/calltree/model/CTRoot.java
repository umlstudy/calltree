package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

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
		boolean rslt = connections.add(connection);
		
		if ( allowFiringModelUpdate() && getModelUpdateListener() != null ) {
			((CTRootListener)getModelUpdateListener()).connAdded(connection);
		}
		
		return rslt;
	}
	
	private static final int ITEM_WIDTH = 120;
	private static final int ITEM_HEIGHT = 30;
	
	public void update() {
		updateSizeAndLocation();
		updateConnection();
	}
	
	private void updateConnection() {
		updateConnection(null, getChildItems());
	}
	
	public void updateConnection(CTItem parentItem, List<CTItem> ctItems) {
		for ( CTItem item : ctItems ) {
			if ( parentItem != null ) {
				CTConnection ctConnection = CTConnection.newInstance("con");
				ctConnection.setSource(parentItem);
				ctConnection.setTarget(item);
				addConnection(ctConnection);
			}
			if ( item.getChildItems().size() > 0 ) {
				updateConnection(item, item.getChildItems());
			}
		}
	}

	private void updateSizeAndLocation() {
		CTItem[][] ctItems2Matrix = CTItems2MatrixConverter.convert(getChildItems());
		int curY = 0;
		for ( int row=0;row<ctItems2Matrix[0].length; row++ ) {
			int curX = 0;
			for ( int col=0;col<ctItems2Matrix.length; col++ ) {
				if ( ctItems2Matrix[col][row] != null ) {
					CTItem item = ctItems2Matrix[col][row];
					boolean updated = false;
					
					// X
					Point oldLoc = item.getLocation();
					if ( curX != oldLoc.x || curY != oldLoc.y ) {
						item.setLocation(new Point(curX, curY));
						updated = true;
					}
					
					// Y
					Dimension oldSize = item.getDimension();
					if ( ITEM_WIDTH != oldSize.width || ITEM_HEIGHT != oldSize.height ) {
						item.setDimension(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
						updated = true;
					}
					
					if ( updated ) {
						item.setAllowFiringModelUpdate(true);
						item.fireModelUpdated();
					}
				}
				
				curX += 130;
			}
			
			curY += 40;
		}
	}
}

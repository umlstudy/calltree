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
	
	public void updateSizeAndLocation() {
		Point curPoint = new Point(0,0);
		Dimension curSize = new Dimension(120,30);
		updateSizeLocation(getChildItems(), curPoint, curSize);
	}

	private void updateSizeLocation(List<CTItem> items, Point curPoint, Dimension curSize) {
		for ( CTItem item : items ) {
			Point oldLoc = item.getLocation();
			boolean updated = false;
			if ( curPoint.x != oldLoc.x || curPoint.y != oldLoc.y ) {
				item.setLocation(new Point(curPoint));
				updated = true;
			}
			
			Dimension oldSize = item.getDimension();
			if ( curSize.width != oldSize.width || curSize.height != oldSize.height ) {
				item.setDimension(new Dimension(curSize));
				updated = true;
			}
			
			if ( updated ) {
				item.setAllowFiringModelUpdate(true);
				item.fireModelUpdated();
			}
			
			if ( item.getChildItems().size()>0 ) {
				int oriCurPointX = curPoint.x;
				curPoint.x += 130;
				updateSizeLocation(item.getChildItems(), curPoint, curSize);
				curPoint.x = oriCurPointX;
			}
			
			curPoint.y += 40;
		}
	}
}

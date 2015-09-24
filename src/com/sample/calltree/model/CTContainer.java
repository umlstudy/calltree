package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Color;

import com.sample.calltree.model.listener.CTContainerListener;

public class CTContainer extends CTElement {

	private List<CTItem> childItems;
	
	private Color backgroundColor;
	
	private boolean isCollapsed;

	public CTContainer(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	public List<CTItem> getChildItems(boolean checkCollapsed) {
		if ( childItems == null || (checkCollapsed && isCollapsed ) ) {
			return Collections.EMPTY_LIST;
		}
		return childItems;
	}
	
	public boolean addChild(CTItem item) {
		if ( childItems == null ) {
			childItems = new ArrayList<CTItem>();
		}
		item.setOwner(this);
		
		boolean rslt = false;
		if ( !childItems.contains(item) ) {
			rslt =  childItems.add(item);
			if ( allowFiringModelUpdate() && getModelUpdateListener() != null ) {
				((CTContainerListener)getModelUpdateListener()).modelAdded(item);
			}
			
			CTRoot root = getRoot();
			if ( this instanceof CTItem ) {
				CTConnection ctConnection = CTConnection.newInstance("con");
				ctConnection.setSource((CTItem)this);
				ctConnection.setTarget(item);
				// 이미 등록된 연결이 아닐 경우에만 등록
				if ( !root.getConnections().contains(ctConnection) ) {
					root.addConnection(ctConnection);
				}
			}
		}
		
		return rslt;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		Assert.isNotNull(backgroundColor);
		this.backgroundColor = backgroundColor;
	}

	public CTRoot getRoot() {
		CTContainer target = this;
		while ( true ) {
			if ( target.getOwner() != null ) {
				target = target.getOwner();
			} else {
				break;
			}
		}
		if ( target instanceof CTRoot ) {
			return (CTRoot)target;
		} else {
			throw new RuntimeException();
		}
	}

	public void toggleCollapsed() {
		setCollapsed(!isCollapsed());
	}

	public boolean isCollapsed() {
		return isCollapsed;
	}

	public void setCollapsed(boolean isCollapsed) {
		this.isCollapsed = isCollapsed;
	}
}

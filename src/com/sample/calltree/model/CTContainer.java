package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Color;

import com.sample.calltree.model.listener.CTContainerListener;

public class CTContainer extends CTElement {

	public static enum ChildItemSelectOptions {
		All
		,VisibleOnly
		,InvisibleOnly
	};
	
	private List<CTItem> childItems;
	
	private Color backgroundColor;
	
	private boolean isCollapsed;

	public CTContainer(String name) {
		super(name);
		setCollapsed(false);
	}

	@SuppressWarnings("unchecked")
	public List<CTItem> getChildItems(ChildItemSelectOptions option) {
		if ( childItems == null ) {
			return Collections.EMPTY_LIST;
		}
		
		List<CTItem> rslt = null;
		switch ( option ) {
		case All :
			rslt = childItems;
			break;
		case VisibleOnly :
			rslt = new ArrayList<CTItem>();
			for ( CTItem item : childItems ) {
				if ( item.isVisible() ) {
					rslt.add(item);
				}
			}
			break;
		case InvisibleOnly :
			rslt = new ArrayList<CTItem>();
			for ( CTItem item : childItems ) {
				if ( !item.isVisible() ) {
					rslt.add(item);
				}
			}
			break;
		}
		
		return rslt;
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
		childsVisibleAndToggleCollapsed(getChildItems(ChildItemSelectOptions.All));
	}
	
	private void childsVisibleAndToggleCollapsed(List<CTItem> childs) {
		for ( CTItem child : childs ) {
			child.setVisible(isCollapsed() ? false : true);
			// 불일치 하는 아이템만 일치시킴
			if ( child.isCollapsed() != isCollapsed ) {
				child.setCollapsed(isCollapsed());
			}
			
			childsVisibleAndToggleCollapsed(child.getChildItems(ChildItemSelectOptions.All));
		}
	}

	public boolean isCollapsed() {
		return isCollapsed;
	}

	public void setCollapsed(boolean isCollapsed) {
		this.isCollapsed = isCollapsed;
	}
}

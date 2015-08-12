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

	public CTContainer(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	public List<CTItem> getChildItems() {
		if ( childItems == null ) {
			return Collections.EMPTY_LIST;
		}
		return childItems;
	}
	
	public boolean addChild(CTItem item) {
		if ( childItems == null ) {
			childItems = new ArrayList<CTItem>();
		}
		boolean rslt = childItems.add(item);
		
		if ( needUpdateModel() && getModelUpdateListener() != null ) {
			((CTContainerListener)getModelUpdateListener()).modelAdded(item);
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
}

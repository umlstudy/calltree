package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Color;

public class CTContainer extends CTElement {

	private List<CTItem> children;
	
	private Color backgroundColor;

	public CTContainer(String name) {
		super(name);
		setChildren(new ArrayList<CTItem>());
	}

	public List<CTItem> getChildren() {
		return children;
	}
	
	public void addChild(CTItem item) {
		getChildren().add(item);
	}

	public void setChildren(List<CTItem> children) {
		this.children = children;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		Assert.isNotNull(backgroundColor);
		this.backgroundColor = backgroundColor;
	}
}

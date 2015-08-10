package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.List;

public class CTContainer extends CTElement {

	private List<CTItem> children;

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
}

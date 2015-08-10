package com.sample.calltree.model;

import org.eclipse.draw2d.geometry.Point;

public class CTItem extends CTContainer implements CTLocationElement {

	private Point point;
	
	public CTItem(String name) {
		super(name);
	}

	@Override
	public void setLocation(Point point) {
		this.point = point;
		
	}

	@Override
	public Point getLocation() {
		//return point;
		// TODO
		return new Point(10,10);
	}
}

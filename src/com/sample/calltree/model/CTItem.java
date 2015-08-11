package com.sample.calltree.model;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class CTItem extends CTContainer implements CTLocationElement {

	private Point location;
	private Dimension dimension;
	public CTItem(String name) {
		super(name);
	}

	@Override
	public void setLocation(Point location) {
		Assert.isNotNull(location);
		this.location = location;
	}

	@Override
	public Point getLocation() {
		Assert.isNotNull(location);
		return location;
	}

	public Dimension getDimension() {
		Assert.isNotNull(dimension);
		return dimension;
	}

	public void setDimension(Dimension size) {
		Assert.isNotNull(size);
		this.dimension = size;
	}
}

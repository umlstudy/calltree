package com.sample.calltree.model;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class CTItem extends CTContainer implements CTLocationElement, CTConnectionEndPoint {

	private Point location;
	private Dimension dimension;
	
	private Color defaultBackgroundColor;
	private Color mouseOverBackgroundColor;
	
	private boolean isVisible;
	
	public CTItem(String name) {
		super(name);
		setVisible(true);
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

	public void setBounds(Rectangle bounds) {
		setLocation(bounds.getLocation());
		setDimension(bounds.getSize());
	}

	public Color getDefaultBackgroundColor() {
		return defaultBackgroundColor;
	}

	public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
		this.defaultBackgroundColor = defaultBackgroundColor;
	}

	public Color getMouseOverBackgroundColor() {
		return mouseOverBackgroundColor;
	}

	public void setMouseOverBackgroundColor(Color mouseOverBackgroundColor) {
		this.mouseOverBackgroundColor = mouseOverBackgroundColor;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}

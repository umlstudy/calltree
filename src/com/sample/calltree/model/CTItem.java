package com.sample.calltree.model;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import com.sample.calltree.packet.body.JobStatus;
import com.sample.calltree.packet.enums.JobStatusType;

public class CTItem extends CTContainer implements CTLocationElement, CTConnectionEndPoint {

	private Point location;
	private Dimension dimension;

	private JobStatus jobStatus;
	
	private Color defaultBackgroundColor;
	private Color mouseOverBackgroundColor;
	
	private boolean isVisible;
	
	public CTItem(String name, JobStatus jobStatus) {
		super(name);
		setVisible(true);
		setJobStatus(jobStatus);
	}
	
	public CTItem(JobStatus jobStatus) {
		this(jobStatus.getJobId(), jobStatus);
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

	public JobStatusType getJobStatusType() {
		return getJobStatus().getJobStatusType();
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		if ( jobStatus == null ) {
			return;
		}
		
		this.jobStatus = jobStatus;
		jobStatus.setCtItem(this);
		
		changeBackground();
	}

	public void changeBackground() {
		switch( jobStatus.getJobStatusType() ) {
		case HOLD : 
			setBackgroundColor(ColorConstants.yellow);
			break;
		case RUNNING :
			setBackgroundColor(ColorConstants.green);
			break;
		case STOPPED :
			setBackgroundColor(ColorConstants.gray);
			break;
		}
	}
}

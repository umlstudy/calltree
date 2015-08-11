package com.sample.calltree.ctrl;

import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.sample.calltree.model.CTElement;

public abstract class FigureEventHandler extends CTContainerCtrl implements MouseListener, MouseMotionListener {

	private Point location;

	public FigureEventHandler(CTElement element) {
		super(element);
		getFigure().addMouseListener(this);
		getFigure().addMouseMotionListener(this);
	}

	public void mousePressed(MouseEvent event) {
		location = event.getLocation();
		event.consume();
	}

	public void mouseDragged(MouseEvent event) {
		if (location == null) {
			return;
		}
		Point newLocation = event.getLocation();
		if (newLocation == null) {
			return;
		}
		Dimension offset = newLocation.getDifference(location);
		if (offset.width == 0 && offset.height == 0) {
			return;
		}
		location = newLocation;

		UpdateManager updateMgr = getFigure().getUpdateManager();
		LayoutManager layoutMgr = getFigure().getParent().getLayoutManager();
		if ( layoutMgr == null ) {
			return;
		}
		
		Rectangle bounds = getFigure().getBounds();
		updateMgr.addDirtyRegion(getFigure().getParent(), bounds);
		
		// Copy the rectangle using getCopy() to prevent undesired side-effects
		bounds = bounds.getCopy().translate(offset.width, offset.height);
		layoutMgr.setConstraint(getFigure(), bounds);
		getFigure().translate(offset.width, offset.height);
		updateMgr.addDirtyRegion(getFigure().getParent(), bounds);
		event.consume();
	}

	public void mouseReleased(MouseEvent event) {
		if (location == null) {
			return;
		}
		location = null;
		event.consume();
	}

	public void mouseMoved(MouseEvent event) {
	}

	public void mouseDoubleClicked(MouseEvent event) {
	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	public void mouseHover(MouseEvent event) {
	}
}

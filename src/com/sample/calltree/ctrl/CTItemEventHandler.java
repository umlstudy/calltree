package com.sample.calltree.ctrl;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;

public abstract class CTItemEventHandler extends CTContainerCtrl implements MouseListener, MouseMotionListener {

	private Point mousePosition;

	public CTItemEventHandler(CTElement element) {
		super(element);
		getFigure().addMouseListener(this);
		getFigure().addMouseMotionListener(this);
	}

	public void mousePressed(MouseEvent event) {
		mousePosition = event.getLocation();
		event.consume();
	}
	
	public void mouseDragged(MouseEvent event) {
		if (mousePosition == null) {
			return;
		}
		Point newMousePosition = event.getLocation();
		if (newMousePosition == null) {
			return;
		}
		Dimension diff = newMousePosition.getDifference(mousePosition);
		if (diff.width == 0 && diff.height == 0) {
			return;
		}
		mousePosition = newMousePosition;
		
		// apply translate value to model and update figure
		CTItem ctItem = getCTItem();
		Point modelLocation = ctItem.getLocation();
		modelLocation.translate(diff);
		applyElement2FigureAndUpateFigure();
	}
	
	private CTItem getCTItem() {
		return (CTItem)getElement();
	}

//	public void mouseDragged(MouseEvent event) {
//		if (mousePosition == null) {
//			return;
//		}
//		Point newMousePosition = event.getLocation();
//		if (newMousePosition == null) {
//			return;
//		}
//		Dimension offset = newMousePosition.getDifference(mousePosition);
//		if (offset.width == 0 && offset.height == 0) {
//			return;
//		}
//		mousePosition = newMousePosition;
//
//		UpdateManager updateMgr = getFigure().getUpdateManager();
//		LayoutManager layoutMgr = getFigure().getParent().getLayoutManager();
//		if ( layoutMgr == null ) {
//			return;
//		}
//		
//		Rectangle bounds = getFigure().getBounds();
//		updateMgr.addDirtyRegion(getFigure().getParent(), bounds);
//		
//		// Copy the rectangle using getCopy() to prevent undesired side-effects
//		bounds = bounds.getCopy().translate(offset.width, offset.height);
//		layoutMgr.setConstraint(getFigure(), bounds);
//		getFigure().translate(offset.width, offset.height);
//		updateMgr.addDirtyRegion(getFigure().getParent(), bounds);
//		event.consume();
//		
//		((CTItem)getElement()).setBounds(bounds);
//	}

//	public void mouseDragged(MouseEvent event) {
//		if (location == null) {
//			return;
//		}
//		Point newLocation = event.getLocation();
//		if (newLocation == null) {
//			return;
//		}
//		Dimension offset = newLocation.getDifference(location);
//		if (offset.width == 0 && offset.height == 0) {
//			return;
//		}
//		location = newLocation;
//
//		UpdateManager updateMgr = getFigure().getUpdateManager();
//		LayoutManager layoutMgr = getFigure().getParent().getLayoutManager();
//		if ( layoutMgr == null ) {
//			return;
//		}
//		
//		Rectangle bounds = getFigure().getBounds();
//		updateMgr.addDirtyRegion(getFigure().getParent(), bounds);
//		
//		// Copy the rectangle using getCopy() to prevent undesired side-effects
//		bounds = bounds.getCopy().translate(offset.width, offset.height);
//		layoutMgr.setConstraint(getFigure(), bounds);
//		getFigure().translate(offset.width, offset.height);
//		updateMgr.addDirtyRegion(getFigure().getParent(), bounds);
//		event.consume();
//		
//		((CTItem)getElement()).setBounds(bounds);
//	}

	public void mouseReleased(MouseEvent event) {
		if (mousePosition == null) {
			return;
		}
		mousePosition = null;
		event.consume();
	}

	public void mouseMoved(MouseEvent event) {
	}

	public void mouseDoubleClicked(MouseEvent event) {
		CTItem ctItem = (CTItem)getElement();
		ctItem.toggleCollapsed();
		ctItem.getRoot().arrangeChildSizeLocations();
		ctItem.getRoot().fireModelUpdated();
	}

	public void mouseEntered(MouseEvent event) {
		CTItem ctItem = (CTItem)getElement();
		ctItem.setBackgroundColor(ColorConstants.lightGray);
		ctItem.setAllowFiringModelUpdate(true);
		ctItem.fireModelUpdated();
	}

	public void mouseExited(MouseEvent event) {
		CTItem ctItem = (CTItem)getElement();
		ctItem.setBackgroundColor(ColorConstants.lightBlue);
		ctItem.setAllowFiringModelUpdate(true);
		ctItem.fireModelUpdated();
	}

	public void mouseHover(MouseEvent event) {
	}
}

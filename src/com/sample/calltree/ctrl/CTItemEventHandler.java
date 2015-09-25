package com.sample.calltree.ctrl;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Color;

import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTContainer.ChildItemSelectOptions;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;

public abstract class CTItemEventHandler extends CTContainerCtrl implements MouseListener, MouseMotionListener {

	private Point mousePosition;
	private Color savedBackgroundColor;

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
		savedBackgroundColor = ctItem.getBackgroundColor();
		ctItem.setBackgroundColor(ColorConstants.red);
		ctItem.setAllowFiringModelUpdate(true);
		ctItem.fireModelUpdated();
		
		setParent(ctItem, ColorConstants.red);
		setChildrens(ctItem, ColorConstants.red);
	}

	private void setChildrens(CTContainer element, Color background) {
		List<CTItem> childItems = element.getChildItems(ChildItemSelectOptions.All);
		
		if ( childItems.size() > 0 ) {
			for ( CTItem item : childItems ) {
				item.setBackgroundColor(background);
				item.setAllowFiringModelUpdate(true);
				item.fireModelUpdated();
				
				setChildrens(item, background);
			}
		}
	}

	private void setParent(CTElement element, Color background) {
		if ( element.getOwner() != null ) {
			element = element.getOwner();
			if ( element instanceof CTContainer ) {
				CTContainer cont = (CTContainer)element;
				cont.setBackgroundColor(background);
				cont.setAllowFiringModelUpdate(true);
				cont.fireModelUpdated();
			}
			setParent(element, background);
		}
	}

	public void mouseExited(MouseEvent event) {
		CTItem ctItem = (CTItem)getElement();
		if ( savedBackgroundColor == null ) {
			savedBackgroundColor = ColorConstants.lightBlue;
		}
		ctItem.setBackgroundColor(savedBackgroundColor);
		ctItem.setAllowFiringModelUpdate(true);
		ctItem.fireModelUpdated();
		
		setParent(ctItem, savedBackgroundColor);
		setChildrens(ctItem, savedBackgroundColor);
	}

	public void mouseHover(MouseEvent event) {
	}
}

package com.sample.calltree.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.IFigure;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTElement;

public abstract class CTContainerCtrl extends AbstractCtrl {
	
	private List<AbstractCtrl> children;

	protected CTContainerCtrl(CTElement element) {
		super(element);
	}
	
	protected abstract List<?> getElementChildren();
	
	@Override
	protected void applyElement2Figure(CTElement element, CTElementFigure figure) {
		Assert.isLegal(element instanceof CTContainer, "element instanceof CTContainer");
		CTContainer item = (CTContainer)element;
		figure.setBackgroundColor(item.getBackgroundColor());
	}
	
	protected AbstractCtrl createCtrl(CTElement element) {
		AbstractCtrl ctrl = getCallTreeCanvas().getCtrlFactory().createCtrl(this, element);
		return ctrl;
	}
	
	public void refreshChildren() {
		List<?> elementObjects = getElementChildren();
		for (int i=0;i<elementObjects.size(); i++ ) {
			CTElement element = (CTElement)elementObjects.get(i);
			AbstractCtrl childCtrl = createCtrl(element);
			addChildren(childCtrl, i);
			addChildVisual(childCtrl, i);
		}
	}
	
	private void addChildren(AbstractCtrl childCtrl, int index) {
		if ( getChildren() == null ) {
			setChildren(new ArrayList<AbstractCtrl>());
		}
		getChildren().add(index, childCtrl);
	}
	
	private void addChildVisual(AbstractCtrl childCtrl, int index) {
		IFigure childFigure = childCtrl.getFigure();
		getFigure().add(childFigure, index);
	}

	public void refresh() {
		super.refresh();
		refreshChildren();
	}
	
	protected AbstractCtrl findCtrlFormChildren(CTElement element) {
		List<AbstractCtrl> children = getChildren();
		if ( children != null ) {
			for ( AbstractCtrl child : children ) {
				if ( element == child.getElement() ) {
					return child;
				}
			}
		}
		return null;
	}

	public List<AbstractCtrl> getChildren() {
		return children;
	}

	public void setChildren(List<AbstractCtrl> children) {
		this.children = children;
	}
}

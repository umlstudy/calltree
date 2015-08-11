package com.sample.calltree.ctrl;

import java.util.List;

import org.eclipse.draw2d.IFigure;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.ModelUpdateListener;
import com.sample.calltree.ui.CallTreeCanvas;

public abstract class AbstractCtrl implements ModelUpdateListener {

	private CTElement element;
	private CTElementFigure figure;
	
	private AbstractCtrl parent;
	
	protected AbstractCtrl(CTElement element) {
		this.setElement(element);
		this.setFigure(createFigure());
		this.applyElement2Figure(getElement(), getFigure());
	}

	public CTElement getElement() {
		return element;
	}

	private void setElement(CTElement element) {
		this.element = element;
	}
	
	public CTElementFigure getFigure() {
		return figure;
	}

	private void setFigure(CTElementFigure figure) {
		this.figure = figure;
	}
	
	public AbstractCtrl getParent() {
		return parent;
	}

	private void setParent(AbstractCtrl parent) {
		this.parent = parent;
	}
	
	protected abstract List<?> getElementChildren();
	
	protected abstract CTElementFigure createFigure();

	protected abstract void applyElement2Figure(CTElement element, CTElementFigure figure);
	
	protected AbstractCtrl createChild(CTElement element) {
		return getCallTreeCanvas().getCtrlFactory().createCtrl(element);
	}
	
	protected CallTreeCanvas getCallTreeCanvas() {
		return getRoot().getCallTreeCanvas();
	}
	
	private CTRootCtrl getRoot() {
		AbstractCtrl parent_ = this;
		do {
			if ( parent_.getParent() == null ) {
				return (CTRootCtrl)parent_;
			} else {
				parent_ = parent_.getParent();
			}
		} while ( true );
	}

	public void refresh() {
		refreshVisuals();
		refreshChildren();
	}
	
	@Override
	public void modelUpdated() {
		refresh();
	}
	
	protected void addChildVisual(AbstractCtrl childCtrl, int index) {
		IFigure child = childCtrl.getFigure();
		getFigure().add(child, index);
	}

	public void refreshChildren() {
		List<?> elementObjects = getElementChildren();
		for (int i=0;i<elementObjects.size(); i++ ) {
			CTElement element = (CTElement)elementObjects.get(i);
			AbstractCtrl childCtrl = createChild(element);
			childCtrl.setParent(this);
			addChildVisual(childCtrl, i);
		}
	}

	public void refreshVisuals() {
	}
}

package com.sample.calltree.ctrl;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.listener.CTElementUpdateListener;
import com.sample.calltree.ui.CallTreeCanvas;

public abstract class AbstractCtrl implements CTElementUpdateListener {

	private CTElement element;
	private CTElementFigure figure;
	
	private AbstractCtrl parent;
	
	protected AbstractCtrl(CTElement element) {
		this.setElement(element);
		this.setFigure(createFigure());
	}
	
	public void init() {
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

	protected void setParent(AbstractCtrl parent) {
		this.parent = parent;
	}
	
	protected abstract CTElementFigure createFigure();

	protected abstract void applyElement2Figure(CTElement element, CTElementFigure figure);
	
	protected CallTreeCanvas getCallTreeCanvas() {
		return getRootCtrl().getCallTreeCanvas();
	}
	
	protected AbstractCtrl findCtrl(CTElement element) {
		return getRootCtrl().findCtrl(element);
	}
	
	protected final CTRootCtrl getRootCtrl() {
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
	}
	
	@Override
	public void modelUpdated() {
		refresh();
	}
}

package com.sample.calltree.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.IFigure;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.listener.CTContainerListener;

public abstract class CTContainerCtrl extends AbstractCtrl implements CTContainerListener {
	
	private List<AbstractCtrl> childCtrls;

	protected CTContainerCtrl(CTElement element) {
		super(element);
	}
	
	protected abstract List<CTItem> getChildItems();
	
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
		List<CTItem> items = getChildItems();
		for (int i=0;i<items.size(); i++ ) {
			CTItem item = items.get(i);
			addChildWithVisual(item, i);
		}
	}
	
	protected void addChildWithVisual(CTItem item) {
		addChildWithVisual(item, -1);
	}
	
	private void addChildWithVisual(CTItem item, int index) {
		AbstractCtrl childCtrl = createCtrl(item);
		addChild(childCtrl, index);
		addChildVisual(childCtrl, index);
	}

	private void addChild(AbstractCtrl childCtrl, int index) {
		if ( getChildCtrls() == null ) {
			setChildCtrls(new ArrayList<AbstractCtrl>());
		}
		if ( index < 0 ) {
			getChildCtrls().add(childCtrl);
		} else {
			getChildCtrls().add(index, childCtrl);
		}
	}
	
	private void addChildVisual(AbstractCtrl childCtrl, int index) {
		IFigure childFigure = childCtrl.getFigure();
		getFigure().add(childFigure, index);
	}

	public void refresh() {
		super.refresh();
		refreshChildren();
	}
	
	protected AbstractCtrl findCtrlFormChildCtrls(CTElement element) {
		List<AbstractCtrl> childCtrls = getChildCtrls();
		if ( childCtrls != null ) {
			for ( AbstractCtrl child : childCtrls ) {
				if ( element == child.getElement() ) {
					return child;
				}
			}
		}
		return null;
	}

	public List<AbstractCtrl> getChildCtrls() {
		return childCtrls;
	}

	public void setChildCtrls(List<AbstractCtrl> childCtrls) {
		this.childCtrls = childCtrls;
	}
	

	@Override
	public void modelAdded(CTItem item) {
		getRootCtrl().addChildWithVisual(item);
	}

	@Override
	public void modelRemoved(CTItem item) {
		// TODO Auto-generated method stub
	}
}

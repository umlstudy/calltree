package com.sample.calltree.ctrl;

import java.util.List;

import org.eclipse.core.runtime.Assert;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.listener.CTContainerListener;

public abstract class CTContainerCtrl extends AbstractCtrl implements CTContainerListener {
	
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
	
	public void refresh() {
		super.refresh();
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

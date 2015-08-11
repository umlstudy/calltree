package com.sample.calltree.ctrl;

import org.eclipse.core.runtime.Assert;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTElement;

public abstract class CTContainerCtrl extends AbstractCtrl {

	protected CTContainerCtrl(CTElement element) {
		super(element);
	}

	@Override
	protected void applyElement2Figure(CTElement element, CTElementFigure figure) {
		Assert.isLegal(element instanceof CTContainer, "element instanceof CTContainer");
		CTContainer item = (CTContainer)element;
		figure.setBackgroundColor(item.getBackgroundColor());
	}
}

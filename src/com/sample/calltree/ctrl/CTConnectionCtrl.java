package com.sample.calltree.ctrl;

import org.eclipse.draw2d.ChopboxAnchor;

import com.sample.calltree.figure.CTConnectionFigure;
import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTConnection;
import com.sample.calltree.model.CTElement;

public class CTConnectionCtrl extends AbstractCtrl {

	protected CTConnectionCtrl(CTElement element) {
		super(element);
	}
	
	public static AbstractCtrl newInstance(CTConnection element) {
		return new CTConnectionCtrl(element);
	}

	@Override
	protected CTElementFigure createFigure() {
		CTConnectionFigure connFigure = new CTConnectionFigure();
		return connFigure;
	}

	@Override
	protected void applyElement2Figure(CTElement element, CTElementFigure figure) {
		CTConnection connModel = (CTConnection)getElement();
		CTElement src = (CTElement)connModel.getSource();
		CTElement tar = (CTElement)connModel.getTarget();
		
		CTConnectionFigure connFigure = (CTConnectionFigure)figure;
		connFigure.setSourceAnchor(new ChopboxAnchor(findCtrl(src).getFigure()));
		connFigure.setTargetAnchor(new ChopboxAnchor(findCtrl(tar).getFigure()));
	}
}

package com.sample.calltree.ctrl;

import java.util.List;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.figure.CTRootFigure;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;

public class CTRootCtrl extends AbstractCtrl {

	private CallTreeCanvas callTreeCanvas;
	
	private CTRootCtrl(CTRoot ctRoot) {
		super(ctRoot);
	}

	public static CTRootCtrl newInstance(CTRoot ctRoot) {
		return new CTRootCtrl(ctRoot);
	}

	@Override
	protected CTElementFigure createFigure() {
		return new CTRootFigure();
	}

	@Override
	protected void applyElement2Figure(CTElement element, CTElementFigure figure) {
	}

	@Override
	protected List<CTItem> getElementChildren() {
		return ((CTRoot)getElement()).getChildren();
	}
	
	protected CallTreeCanvas getCallTreeCanvas() {
		return callTreeCanvas;
	}

	public void setCallTreeCanvas(CallTreeCanvas callTreeCanvas) {
		this.callTreeCanvas = callTreeCanvas;
	}
	
//	@Override
//	protected List<CTItem> getModelChildren() {
//		return ((CTRoot)getModel()).getChildren();
//	}
//	
//	@Override
//	public void addNotify() {
//		super.addNotify();
//	}
//
//	@Override
//	protected IFigure createFigure() {
//		Figure figure = new FreeformLayer();
//		figure.setBorder(new MarginBorder(3));
//		figure.setLayoutManager(new FreeformLayout());
//		figure.setBackgroundColor(ColorConstants.lightBlue);
//		
//		return figure;
//	}
//
//	@Override
//	protected void createEditPolicies() {
//	}
}
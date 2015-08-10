package com.sample.calltree.ctrl;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.sample.calltree.model.CTRoot;

public class CallTreeCanvas extends FigureCanvas {

	private CtrlFactory ctrlFactory;
	private CTRootCtrl rootCtrl;
	
	public CallTreeCanvas(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		this.setViewport(new FreeformViewport());
		this.setBackground(ColorConstants.white);
	}
	
	public final void setContents(CTRoot ctRoot) {
		Assert.isLegal(getCtrlFactory()!=null, "getCtrlFactory()!=null");
		rootCtrl = (CTRootCtrl)getCtrlFactory().createCtrl(ctRoot);
		rootCtrl.setCallTreeCanvas(this);
		super.setContents(rootCtrl.getFigure());
		rootCtrl.refresh();
	}
	
	public void setContents(IFigure figure) {
		throw new UnsupportedOperationException();
	}

	CtrlFactory getCtrlFactory() {
		return ctrlFactory;
	}

	public void setCtrlFactory(CtrlFactory ctrlFactory) {
		this.ctrlFactory = ctrlFactory;
	}
}

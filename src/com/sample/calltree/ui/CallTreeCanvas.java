package com.sample.calltree.ui;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.sample.calltree.ctrl.CTRootCtrl;
import com.sample.calltree.ctrl.CtrlFactory;
import com.sample.calltree.model.CTRoot;

public class CallTreeCanvas extends FigureCanvas {

	private CtrlFactory ctrlFactory;
	private CTRootCtrl rootCtrl;
	
	private GridLayer gridLayer;
	private FreeformLayer primary;
	private ConnectionLayer connections;
	
	private ScalableFreeformLayeredPane layoutPane;
	
	public CallTreeCanvas(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		
		layoutPane = new ScalableFreeformLayeredPane();
		
		gridLayer = new GridLayer();
		gridLayer.setBorder(new MarginBorder(3));
		gridLayer.setLayoutManager(new FreeformLayout());
		layoutPane.add(gridLayer, "gridLayer");
		
		primary = new FreeformLayer();
		primary.setBorder(new MarginBorder(3));
		primary.setLayoutManager(new FreeformLayout());
		layoutPane.add(primary, "Primary");

		connections = new ConnectionLayer();
		connections.setConnectionRouter(new ShortestPathConnectionRouter(primary));
		layoutPane.add(connections, "Connections");
		
		FreeformViewport freeformViewport = new FreeformViewport();
		freeformViewport.setContents(layoutPane);
		this.setViewport(freeformViewport);
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

	public CtrlFactory getCtrlFactory() {
		return ctrlFactory;
	}

	public void setCtrlFactory(CtrlFactory ctrlFactory) {
		this.ctrlFactory = ctrlFactory;
	}
}

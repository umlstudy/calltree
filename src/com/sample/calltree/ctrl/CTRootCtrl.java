package com.sample.calltree.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.figure.CTRootFigure;
import com.sample.calltree.model.CTConnection;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;
import com.sample.calltree.ui.CallTreeCanvas;

public class CTRootCtrl extends CTContainerCtrl {

	private CallTreeCanvas callTreeCanvas;
	private List<CTConnectionCtrl> connectionCtrls;
	
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
	
	private CTRoot getRoot() {
		return (CTRoot)getElement();
	}

	@Override
	protected List<CTItem> getElementChildren() {
		return (getRoot()).getChildren();
	}
	
	protected CallTreeCanvas getCallTreeCanvas() {
		return callTreeCanvas;
	}

	public void setCallTreeCanvas(CallTreeCanvas callTreeCanvas) {
		this.callTreeCanvas = callTreeCanvas;
	}
	
	private AbstractCtrl findCtrlFormConnections(CTElement element) {
		List<CTConnectionCtrl> ctrls = getConnections();
		if ( ctrls != null ) {
			for (CTConnectionCtrl ctrl : ctrls ) {
				if ( ctrl.getElement() == element ) {
					return ctrl;
				}
			}
		}
		return null;
	}
	
	@Override
	protected AbstractCtrl findCtrl(CTElement element) {
		AbstractCtrl ctrl = null;
		CTRoot ctRoot = getRoot();
		if ( ctRoot == element ) {
			// 1. ROOT 자체와 비교
			ctrl = this;
		} else {
			// 2. 자식목록에서 비교
			ctrl = findCtrlFormChildren(element);
			if ( ctrl == null ) {
				ctrl = findCtrlFormConnections(element);
			}
		}
		
		return ctrl;
	}

	public List<CTConnectionCtrl> getConnections() {
		return connectionCtrls;
	}

	public void setConnections(List<CTConnectionCtrl> connectionCtrls) {
		this.connectionCtrls = connectionCtrls;
	}
	
	public void refresh() {
		super.refresh();
		refreshConnections();
	}
	
	private void refreshConnections() {
		List<CTConnection> connections = getRoot().getConnections();
		for (int i=0;i<connections.size(); i++ ) {
			CTConnection connModel = (CTConnection)connections.get(i);
			CTConnectionCtrl connCtrl = (CTConnectionCtrl)createCtrl(connModel);
			addConnection(connCtrl, i);
			addConnectionVisual(connCtrl, i);
		}
	}
	
	private void addConnection(CTConnectionCtrl connCtrl, int index) {
		if ( getConnections() == null ) {
			setConnections(new ArrayList<CTConnectionCtrl>());
		}
		getConnections().add(index, connCtrl);
	}
	
	private void addConnectionVisual(CTConnectionCtrl connCtrl, int index) {
		IFigure connFigure = connCtrl.getFigure();
		getFigure().add(connFigure, index);
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
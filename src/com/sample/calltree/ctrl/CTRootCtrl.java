package com.sample.calltree.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.IFigure;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.figure.CTRootFigure;
import com.sample.calltree.model.CTConnection;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;
import com.sample.calltree.model.listener.CTRootListener;
import com.sample.calltree.ui.CallTreeCanvas;

public class CTRootCtrl extends CTContainerCtrl implements CTRootListener {

	private CallTreeCanvas callTreeCanvas;
	private ConnectionLayer connectionLayer;
	
	private List<CTConnectionCtrl> connCtrls;
	
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
	protected List<CTItem> getChildItems() {
		return getRoot().getChildItems();
	}
	
	protected CallTreeCanvas getCallTreeCanvas() {
		return callTreeCanvas;
	}

	public void setCallTreeCanvas(CallTreeCanvas callTreeCanvas) {
		this.callTreeCanvas = callTreeCanvas;
	}

	public ConnectionLayer getConnectionLayer() {
		return connectionLayer;
	}

	public void setConnectionLayer(ConnectionLayer connectionLayer) {
		this.connectionLayer = connectionLayer;
	}
	
	private AbstractCtrl findCtrlFormConnCtrls(CTConnection connection) {
		List<CTConnectionCtrl> ctrls = getConnCtrls();
		if ( ctrls != null ) {
			for (CTConnectionCtrl ctrl : ctrls ) {
				if ( ctrl.getElement() == connection ) {
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
			ctrl = findCtrlFormChildCtrls(element);
			if ( ctrl == null && element instanceof CTConnection ) {
				ctrl = findCtrlFormConnCtrls((CTConnection)element);
			}
		}
		
		return ctrl;
	}

	public List<CTConnectionCtrl> getConnCtrls() {
		return connCtrls;
	}

	public void setConnCtrls(List<CTConnectionCtrl> connectionCtrls) {
		this.connCtrls = connectionCtrls;
	}
	
	public void refresh() {
		super.refresh();
		refreshConnections();
	}
	
	private void refreshConnections() {
		List<CTConnection> connections = getRoot().getConnections();
		for (int i=0;i<connections.size(); i++ ) {
			CTConnection conn = (CTConnection)connections.get(i);
			addConnectionWithVisual(conn, i);
		}
	}
	
	protected void addConnectionWithVisual(CTConnection conn) {
		addConnectionWithVisual(conn, -1);
	}
	
	private void addConnectionWithVisual(CTConnection conn, int index) {
		CTConnectionCtrl connCtrl = (CTConnectionCtrl)createCtrl(conn);
		addConnection(connCtrl, index);
		addConnectionVisual(connCtrl, index);
	}

	private void addConnection(CTConnectionCtrl connCtrl, int index) {
		if ( getConnCtrls() == null ) {
			setConnCtrls(new ArrayList<CTConnectionCtrl>());
		}
		if ( index < 0 ) {
			getConnCtrls().add(connCtrl);
		} else {
			getConnCtrls().add(index, connCtrl);
		}
	}

	private void addConnectionVisual(CTConnectionCtrl connCtrl, int index) {
		IFigure connFigure = connCtrl.getFigure();
		getConnectionLayer().add(connFigure, index);
	}

	@Override
	public void connAdded(CTConnection conn) {
		addConnectionWithVisual(conn);
	}

	@Override
	public void connRemoved(CTConnection conn) {
		// TODO Auto-generated method stub
	}
//	
//	public void updateSizeLocation() {
//		if ( getRoot().updateSizeLocation() ) {
//			for ( AbstractCtrl ctrl : getChildCtrls() ) {
//				CTItem item = (CTItem)ctrl.getElement();
//				if ( item.isInvalidate() ) {
//					item.fireModelUpdated();
//				}
//			}
//		}
//	}
	
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
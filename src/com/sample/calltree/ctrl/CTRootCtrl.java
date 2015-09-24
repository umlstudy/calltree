package com.sample.calltree.ctrl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
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
	
	private ListOrderedMap connCtrls;
	private ListOrderedMap childCtrls;
	
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
	protected List<CTItem> getChildItems(boolean checkCollapsed) {
		return getRoot().getChildItems(checkCollapsed);
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
		ListOrderedMap ctrls = getConnCtrls();
		if ( ctrls != null ) {
			return (AbstractCtrl)ctrls.get(connection);
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

	public ListOrderedMap getConnCtrls() {
		return connCtrls;
	}

	public void setConnCtrls(ListOrderedMap connectionCtrls) {
		this.connCtrls = connectionCtrls;
	}
	
	public void refresh() {
		super.refresh();
		refreshConnections();
		refreshChildren();
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
		if ( getConnCtrls() == null ) {
			setConnCtrls(new ListOrderedMap());
		}
		
		CTConnectionCtrl connCtrl = (CTConnectionCtrl)getConnCtrls().get(conn);
		if ( connCtrl == null ) {
			connCtrl = (CTConnectionCtrl)createCtrl(conn);
			addConnection(conn, connCtrl, index);
		}
		
		addConnectionVisual(connCtrl, index);
	}

	private void addConnection(CTConnection conn, CTConnectionCtrl connCtrl, int index) {
		if ( getConnCtrls() == null ) {
			setConnCtrls(new ListOrderedMap());
		}
		if ( index < 0 ) {
			getConnCtrls().put(conn, connCtrl);
		} else {
			getConnCtrls().put(index, conn, connCtrl);
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
	
	public ListOrderedMap getChildCtrls() {
		return childCtrls;
	}

	public void setChildCtrls(ListOrderedMap childCtrls) {
		this.childCtrls = childCtrls;
	}
	
	protected AbstractCtrl findCtrlFormChildCtrls(CTElement element) {
		ListOrderedMap childCtrls = getChildCtrls();
		if ( childCtrls != null ) {
			return (AbstractCtrl)childCtrls.get(element);
		}
		return null;
	}
	
	public void refreshChildren() {
		List<CTItem> items = getChildItems(true);
		for (int i=0;i<items.size(); i++ ) {
			CTItem item = items.get(i);
			addChildWithVisual(item, i);
		}
	}
	
	protected void addChildWithVisual(CTItem item) {
		addChildWithVisual(item, -1);
	}
	
	private void addChildWithVisual(CTItem item, int index) {
		if ( getChildCtrls() == null ) {
			setChildCtrls(new ListOrderedMap());
		}
		
		AbstractCtrl childCtrl = (AbstractCtrl)getChildCtrls().get(item);
		if ( childCtrl == null ) {
			childCtrl = createCtrl(item);
			addChild(item, childCtrl, index);
		}
		
		addChildVisual(childCtrl, index);
	}

	private void addChild(CTItem item, AbstractCtrl childCtrl, int index) {
		if ( getChildCtrls() == null ) {
			setChildCtrls(new ListOrderedMap());
		}
		if ( index < 0 ) {
			getChildCtrls().put(item, childCtrl);
		} else {
			getChildCtrls().put(index, item, childCtrl);
		}
	}
	
	private void addChildVisual(AbstractCtrl childCtrl, int index) {
		IFigure childFigure = childCtrl.getFigure();
		getFigure().add(childFigure, index);
	}
}
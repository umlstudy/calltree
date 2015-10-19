package com.sample.calltree.ui;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.sample.calltree.ctrl.CTRootCtrl;
import com.sample.calltree.ctrl.CtrlFactory;
import com.sample.calltree.model.CTRoot;

public class CallTreeCanvas extends FigureCanvas {

	private CtrlFactory ctrlFactory;
	private CTRootCtrl rootCtrl;
	
	private ScalableFreeformLayeredPane layoutPane;
	
	private PopupActionProvider actionProvider;
	
	public CallTreeCanvas(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		
		layoutPane = new ScalableFreeformLayeredPane();
		
		FreeformViewport freeformViewport = new FreeformViewport();
		freeformViewport.setContents(layoutPane);
		this.setViewport(freeformViewport);
	}
	
	public final void setContents(CTRoot ctRoot, PopupActionProvider actionProvider) {
		Assert.isLegal(getCtrlFactory()!=null, "getCtrlFactory()!=null");
		rootCtrl = (CTRootCtrl)getCtrlFactory().createCtrl(null, ctRoot);
		rootCtrl.setCallTreeCanvas(this);
		
		GridLayer gridLayer = new GridLayer();
		gridLayer.setBorder(new MarginBorder(3));
		gridLayer.setLayoutManager(new FreeformLayout());
		layoutPane.add(gridLayer, "gridLayer");
		
		IFigure primary = rootCtrl.getFigure();
		layoutPane.add(primary, "Primary");

		ConnectionLayer connections = new ConnectionLayer();
		connections.setConnectionRouter(new ShortestPathConnectionRouter(primary));
		layoutPane.add(connections, "Connections");
		
		rootCtrl.setConnectionLayer(connections);
		
		// TODO 임시
		this.actionProvider = actionProvider;
		createContextMenuManager();
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

	// TODO 팝업 메뉴 관련 부분 추가
	private MenuManager contextMenu;
	
	void createContextMenuManager() {
		MenuManager contextMenuManger = new MenuManager("FreedrawingEditorPopup");
		contextMenuManger.setRemoveAllWhenShown(true);
		setContextMenu(contextMenuManger);
		contextMenuManger.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				contextMenu.removeAll();
				List<Action> contextActions = actionProvider.getContextActions(rootCtrl.getMouseOverItem());
				for ( Action action : contextActions ) {
					contextMenu.add(action);
				}
			}
		});
	}

	private void setContextMenu(MenuManager manager) {
		if (contextMenu != null) {
			contextMenu.dispose();
		}
		contextMenu = manager;
		if ( !this.isDisposed() ) {
			this.setMenu(contextMenu.createContextMenu(this));
		}
	}
}

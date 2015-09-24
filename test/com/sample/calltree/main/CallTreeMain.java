package com.sample.calltree.main;

import java.util.Random;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;

import com.sample.calltree.ctrl.CtrlFactory;
import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTContainer.ChildItemSelectOptions;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;
import com.sample.calltree.ui.CallTreeCanvas;

public class CallTreeMain extends ApplicationWindow {

	private CallTreeViewer tv;
	private CTRoot ctRoot;
	
	public CallTreeMain() {
		super(null);
	}

	protected Control createContents(Composite parent_) {
		Composite parent = new Composite(parent_, SWT.NONE);
		
		GridDataFactory gdFactory = GridDataFactory.fillDefaults();
		GridLayoutFactory glFactory = GridLayoutFactory.fillDefaults();
		parent.setLayout(glFactory.numColumns(2).create());
		tv = new CallTreeViewer(parent);
		tv.getControl().setLayoutData(gdFactory.hint(250, 200).create());
		tv.setLabelProvider(new CallTreeViewerLabelProvider());
		tv.setContentProvider(new CallTreeVieweContentProvider());
		
		createContextMenu(tv);
		
		Composite gvParent = new Composite(parent, SWT.NONE);
		gvParent.setLayout(glFactory.numColumns(1).create());
		gvParent.setLayoutData(gdFactory.grab(true, true).create());
		CallTreeCanvas canvas = new CallTreeCanvas(gvParent);
		canvas.setLayoutData(gdFactory.grab(true, true).create());
		//canvas.setBackground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_BLUE));
		canvas.setCtrlFactory(CtrlFactory.newInstance());
		
		ctRoot = createDummyCTRoot();
		//gv.setRootEditPart((RootEditPart)CTEditPartFactory.createEditPart(ctRoot));
		canvas.setContents(ctRoot);
		
		ctRoot.setAllowFiringModelUpdate(true);
		ctRoot.fireModelUpdated();
		
		getShell().addShellListener(new ShellAdapter() {
			
			boolean firstShow = true;
			@Override
			public void shellActivated(ShellEvent e) {
				if ( firstShow ) {
					appStarted();
					firstShow = false;
				}
			}
		});
		tv.setInput(ctRoot);
		
		return parent;
	}

	protected void appStarted() {
		// 임의의 샘플 데이터를 30개 생성함
		for ( int i=0; i<30; i++ ) {
			int itemCnt = getItemCnt(ctRoot) + 1;
			Random r = new Random();
			int randomPos = r.nextInt(itemCnt);
			final CTContainer container = (CTContainer)getItem(ctRoot, randomPos, 0);
			final CTItem ctItem1 = new CTItem(String.format("%d", i));
			ctItem1.setLocation(new Point(20,20));
			ctItem1.setBackgroundColor(ColorConstants.lightBlue);
			ctItem1.setDimension(new Dimension(170, 10));
			container.addChild(ctItem1);
		}
		ctRoot.update();
		tv.refresh();
	}

	private CTElement getItem(CTContainer cont, int randomPos, int curPos) {
		if ( randomPos == 0 ) return cont;
		
		for ( CTElement ele : cont.getChildItems(ChildItemSelectOptions.All) ) {
			curPos ++;
			if ( randomPos == curPos ) return ele;
			if ( ele instanceof CTContainer ) {
				CTContainer ctc = (CTContainer)ele;
				CTElement ele2 = getItem(ctc, randomPos, curPos);
				if ( ele2 != null ) {
					return ele2;
				} else {
					curPos += getItemCnt(ctc);
				}
			}
		} 
		return null;
	}

	private int getItemCnt(CTContainer cont) {
		int cnt = cont.getChildItems(ChildItemSelectOptions.All).size();
		for ( CTElement ele : cont.getChildItems(ChildItemSelectOptions.All) ) {
			if ( ele instanceof CTContainer ) {
				CTContainer ctc = (CTContainer)ele;
				cnt += getItemCnt(ctc);
			}
		} 
		return cnt;
	}

	private void createContextMenu(final CallTreeViewer tv) {
		MenuManager menuMgr = new MenuManager();
	    menuMgr.setRemoveAllWhenShown(true);
	    menuMgr.addMenuListener(new IMenuListener() {
			@Override
	        public void menuAboutToShow(IMenuManager manager) {
				tv.fillContextMenu(manager);
	        }
	    });
	    Menu menu = menuMgr.createContextMenu(tv.getControl());
	    tv.getControl().setMenu(menu);
	}

	private static CTRoot createDummyCTRoot() {
		CTRoot root = new CTRoot("root");
		root.setBackgroundColor(ColorConstants.orange);
		
		CTItem ctItem1 = new CTItem("item1");
		ctItem1.setLocation(new Point(100,100));
		ctItem1.setBackgroundColor(ColorConstants.green);
		ctItem1.setDimension(new Dimension(70, 130));
		root.addChild(ctItem1);
		
		CTItem ctItem2 = new CTItem("item2");
		ctItem2.setLocation(new Point(20,70));
		ctItem2.setBackgroundColor(ColorConstants.darkGray);
		ctItem2.setDimension(new Dimension(70, 40));
		root.addChild(ctItem2);
		
		CTItem ctItem3 = new CTItem("item3");
		ctItem3.setLocation(new Point(50,20));
		ctItem3.setBackgroundColor(ColorConstants.darkGreen);
		ctItem3.setDimension(new Dimension(30, 80));
		root.addChild(ctItem3);
		
//		CTConnection con1 = CTConnection.newInstance("con1");
//		con1.setSource(ctItem1);
//		con1.setTarget(ctItem2);
//		root.addConnection(con1);
		
		return root;
	}

	public static void main(String[] args) {
		CallTreeMain ct = new CallTreeMain();
		ct.setBlockOnOpen(true);
		ct.open();
		Display.getCurrent().dispose();
	}
}

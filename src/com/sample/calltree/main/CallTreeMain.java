package com.sample.calltree.main;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.sample.calltree.ctrl.CtrlFactory;
import com.sample.calltree.model.CTConnection;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;
import com.sample.calltree.ui.CallTreeCanvas;
import com.sample.calltree.ui.CallTreeViewer;

public class CallTreeMain extends ApplicationWindow {

	private CallTreeViewer tv;
	
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
		
		Composite gvParent = new Composite(parent, SWT.NONE);
		gvParent.setLayout(glFactory.numColumns(1).create());
		gvParent.setLayoutData(gdFactory.grab(true, true).create());
		CallTreeCanvas canvas = new CallTreeCanvas(gvParent);
		canvas.setLayoutData(gdFactory.grab(true, true).create());
		//canvas.setBackground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_BLUE));
		canvas.setCtrlFactory(CtrlFactory.newInstance());
		
		CTRoot ctRoot = createDummyCTRoot();
		//gv.setRootEditPart((RootEditPart)CTEditPartFactory.createEditPart(ctRoot));
		canvas.setContents(ctRoot);
		tv.setInput(ctRoot);
		
		return parent;
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
		
		CTConnection con1 = CTConnection.newInstance("con1");
		con1.setSource(ctItem1);
		con1.setTarget(ctItem2);
		root.addConnection(con1);
		
		return root;
	}

	public static void main(String[] args) {
		CallTreeMain ct = new CallTreeMain();
		ct.setBlockOnOpen(true);
		ct.open();
		Display.getCurrent().dispose();
	}
}

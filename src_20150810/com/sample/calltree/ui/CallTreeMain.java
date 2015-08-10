package com.sample.calltree.ui;

import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;

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
		gvParent.setBackground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_BLUE));
		CallTreeGraphicalViewer gv = new CallTreeGraphicalViewer();
		gv.createControl(gvParent).setLayoutData(gdFactory.grab(true, true).create());
		gv.setEditPartFactory(new CTEditPartFactory());
		gv.setRootEditPart(new ScalableFreeformRootEditPart());
		
		
		CTRoot ctRoot = createDummyCTRoot();
		//gv.setRootEditPart((RootEditPart)CTEditPartFactory.createEditPart(ctRoot));
		gv.setContents(ctRoot);
		tv.setInput(ctRoot);
		
		return parent;
	}

	private static CTRoot createDummyCTRoot() {
		CTRoot root = new CTRoot("root");
		root.addChild(new CTItem("item1"));
		return root;
	}

	public static void main(String[] args) {
		CallTreeMain ct = new CallTreeMain();
		ct.setBlockOnOpen(true);
		ct.open();
		Display.getCurrent().dispose();
	}
}

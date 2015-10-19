package com.sample.calltree.main;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.sample.calltree.main.action.AddAction;
import com.sample.calltree.model.CTItem;

public class CallTreeViewer extends TreeViewer {

	public CallTreeViewer(Composite parent) {
		super(parent);
	}

	public void fillContextMenu(IMenuManager manager) {
		IStructuredSelection selection = (IStructuredSelection) getSelection();
		CTItem object = (CTItem)selection.getFirstElement();
		manager.add(new AddAction(object, this));
	}
}

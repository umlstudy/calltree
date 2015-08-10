package com.sample.calltree.ui;

import org.eclipse.jface.viewers.LabelProvider;

public class CallTreeViewerLabelProvider extends LabelProvider {
	public String getText(Object element) {
		return element == null ? "" : element.toString();//$NON-NLS-1$
	}
}

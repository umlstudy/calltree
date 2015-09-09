package com.sample.calltree.main;

import org.eclipse.jface.viewers.LabelProvider;

import com.sample.calltree.model.CTElement;

public class CallTreeViewerLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if ( element == null ) {
			return "";//$NON-NLS-1$
		} else if ( element instanceof CTElement ) {
			return ((CTElement)element).getName();
		}
		return element.toString();
	}
}

package com.sample.calltree.ui;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTRoot;

public class CallTreeVieweContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if ( inputElement instanceof Object[] ) {
			return (Object[])inputElement;
		} else if ( inputElement instanceof CTRoot ) {
			return new Object[] {inputElement,};
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if ( parentElement instanceof CTContainer ) {
			return ((CTContainer)parentElement).getChildren().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		Object[] children = getChildren(element);
		if ( children != null && children.length>0) {
			return true;
		}
		return false;
	}
}

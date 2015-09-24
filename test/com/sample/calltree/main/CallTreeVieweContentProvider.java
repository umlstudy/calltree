package com.sample.calltree.main;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.sample.calltree.model.CTContainer;
import com.sample.calltree.model.CTContainer.ChildItemSelectOptions;

public class CallTreeVieweContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if ( parentElement instanceof CTContainer ) {
			return ((CTContainer)parentElement).getChildItems(ChildItemSelectOptions.VisibleOnly).toArray();
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

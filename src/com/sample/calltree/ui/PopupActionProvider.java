package com.sample.calltree.ui;

import java.util.List;

import org.eclipse.jface.action.Action;

import com.sample.calltree.ctrl.CTContainerCtrl;

public interface PopupActionProvider {

	public List<Action> getContextActions(CTContainerCtrl ctrl);
}

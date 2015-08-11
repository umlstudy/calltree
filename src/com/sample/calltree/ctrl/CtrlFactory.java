package com.sample.calltree.ctrl;

import com.sample.calltree.model.CTConnection;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;

public class CtrlFactory {

	public AbstractCtrl createCtrl(CTContainerCtrl parent, CTElement element) {
		AbstractCtrl ctrl = null;
		if ( element instanceof CTRoot ) {
			ctrl = CTRootCtrl.newInstance((CTRoot)element);
		} else if ( element instanceof CTItem ) {
			ctrl = CTItemCtrl.newInstance((CTItem)element);
		} else if ( element instanceof CTConnection ) {
			ctrl = CTConnectionCtrl.newInstance((CTConnection)element);
		} else {
			throw new RuntimeException();
		}
		
		ctrl.setParent(parent);
		ctrl.init();
		
		element.setModelUpdateListener(ctrl);
		
		return ctrl;
	}

	public static CtrlFactory newInstance() {
		return new CtrlFactory();
	}
}

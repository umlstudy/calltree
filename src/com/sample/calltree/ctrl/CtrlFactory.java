package com.sample.calltree.ctrl;

import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;

public class CtrlFactory {

	public AbstractCtrl createCtrl(CTElement element) {
		AbstractCtrl ctrl = null;
		if ( element instanceof CTRoot ) {
			ctrl = CTRootCtrl.newInstance((CTRoot)element);
		} else if ( element instanceof CTItem ) {
			ctrl = CTItemCtrl.newInstance((CTItem)element);
		} else {
			throw new RuntimeException();
		}
		element.setModelUpdateListener(ctrl);
		return ctrl;
	}

	public static CtrlFactory newInstance() {
		return new CtrlFactory();
	}
}

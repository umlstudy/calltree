package com.sample.calltree.ctrl;

import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;

public class CtrlFactory {

	public AbstractCtrl createCtrl(CTElement element) {
		if ( element instanceof CTRoot ) {
			return CTRootCtrl.newInstance((CTRoot)element);
		} else if ( element instanceof CTItem ) {
			return CTItemCtrl.newInstance((CTItem)element);
		}
 		
		throw new RuntimeException();
	}

	public static CtrlFactory newInstance() {
		return new CtrlFactory();
	}
}

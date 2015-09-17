package com.sample.calltree.ctrl;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.ChopboxAnchor;

import com.sample.calltree.figure.CTConnectionFigure;
import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.model.CTConnection;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;

public class CTConnectionCtrl extends AbstractCtrl {

	protected CTConnectionCtrl(CTElement element) {
		super(element);
	}
	
	public static AbstractCtrl newInstance(CTConnection element) {
		return new CTConnectionCtrl(element);
	}

	@Override
	protected CTElementFigure createFigure() {
		CTConnectionFigure connFigure = new CTConnectionFigure();
		return connFigure;
	}

	@Override
	protected void applyElement2Figure(CTElement element, CTElementFigure figure) {
		CTConnection connModel = (CTConnection)getElement();
		CTItem src = (CTItem)connModel.getSource();
		CTItem tar = (CTItem)connModel.getTarget();
		
		CTConnectionFigure connFigure = (CTConnectionFigure)figure;
		CTItemCtrl srcCtrl = (CTItemCtrl)findCtrl(src);
		CTItemCtrl tarCtrl = (CTItemCtrl)findCtrl(tar);
		if ( tarCtrl == null ) {
			getRootCtrl().addChildWithVisual(tar);
			tarCtrl = (CTItemCtrl)findCtrl(tar);
		}
		if ( srcCtrl == null ) {
			tar.getOwner().setAllowFiringModelUpdate(true);
			src.getOwner().fireModelUpdated();
			srcCtrl = (CTItemCtrl)findCtrl(src);
		}
//		if ( srcCtrl == null ) {
//			
//		}
//		getCallTreeCanvas().getCtrlFactory().createCtrl(tar.get, tar);
		Assert.isNotNull(srcCtrl);
		Assert.isNotNull(tarCtrl);
		connFigure.setSourceAnchor(new ChopboxAnchor(srcCtrl.getTargetEndPoingFigure()));
		connFigure.setTargetAnchor(new ChopboxAnchor(tarCtrl.getSourceEndPoingFigure()));
		
		connFigure.setAntialias(1);
	}
}

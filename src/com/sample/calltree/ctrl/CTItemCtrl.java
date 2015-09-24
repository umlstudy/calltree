package com.sample.calltree.ctrl;


import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.figure.CTItemFigure;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;

public class CTItemCtrl extends CTItemEventHandler {

	private CTItemCtrl(CTItem ctItem) {
		super(ctItem);
	}
	
	public static CTItemCtrl newInstance(CTItem ctItem) {
		return new CTItemCtrl(ctItem);
	}

	@Override
	protected CTElementFigure createFigure() {
		return new CTItemFigure();
	}
	
	@Override
	public CTItemFigure getFigure() {
		return (CTItemFigure)super.getFigure();
	}

	@Override
	protected void applyElement2Figure(CTElement element, CTElementFigure figure) {
		super.applyElement2Figure(element, figure);
		Assert.isLegal(element instanceof CTItem, "element instanceof CTItem");
		CTItem item = (CTItem)element;
		CTItemFigure itemFigure = (CTItemFigure)figure;
		itemFigure.setBounds(new Rectangle(item.getLocation(), item.getDimension()));
		itemFigure.setText(item.getName());
		itemFigure.setBackgroundColor(item.getBackgroundColor());
	}
	
	@Override
	protected List<CTItem> getChildItems(boolean checkCollapsed) {
		return ((CTItem)getElement()).getChildItems(checkCollapsed);
	}
	
	@Override
	public void refresh() {
		super.refresh();
		applyElement2Figure(getElement(), getFigure());
		getFigure().repaint();
	}

	public IFigure getSourceEndPoingFigure() {
		return getFigure().getLeftEndPointFigure();
	}

	public IFigure getTargetEndPoingFigure() {
		return getFigure().getRightEndPointFigure();
	}
}

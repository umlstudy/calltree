package com.sample.calltree.figure;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;

public class CTItemFigure extends RectangleFigure implements CTContainerFigure {
	
	private CTItem element;
	
	public CTItemFigure() {
		setPreferredSize(100, 100);
	}
	
//	protected void paintFigure(Graphics graphics) {
//		setBackgroundColor(ColorConstants.gray);
//		super.paintFigure(graphics);
//	}
	
//	public Rectangle getBounds() {
//		return new Rectangle(element.getLocation(), new Dimension(100,100));
//	}

	@Override
	public void setElement(CTElement element_) {
		Assert.isTrue(element_ instanceof CTItem);
		this.element = (CTItem)element_;
		setBounds(new Rectangle(element.getLocation(), new Dimension(100,100)));
	}
}

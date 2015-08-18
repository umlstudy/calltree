package com.sample.calltree.figure;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

public class CTItemFigure extends Label implements CTContainerFigure {

	private Label label;
	
	public CTItemFigure() {
//		label = new Label();
//		label.setOpaque(true);
//		add(label);
		setOpaque(true);
	}

//	public void setText(String text) {
//		label.setText(text);
//	}
//
//	public void setBounds(Rectangle rect) {
//		label.setBounds(new Rectangle(0, 0, rect.getSize().width, rect.getSize().height));
//		super.setBounds(rect);
//	}
	
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
	}
}

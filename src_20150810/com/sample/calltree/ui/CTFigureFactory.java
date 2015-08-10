package com.sample.calltree.ui;

import org.eclipse.draw2d.IFigure;

import com.sample.calltree.figure.CTElementFigure;
import com.sample.calltree.figure.CTItemFigure;
import com.sample.calltree.model.CTElement;
import com.sample.calltree.model.CTItem;

public class CTFigureFactory {
	public static IFigure createFigure(Object model) {

		CTElementFigure figure = null;
		
		if ( model instanceof CTItem ) {
			figure = new CTItemFigure();
		} else {
			throw new RuntimeException(model.getClass().getName());
		}
		
		figure.setElement((CTElement)model);
		
		return figure;
	}
}

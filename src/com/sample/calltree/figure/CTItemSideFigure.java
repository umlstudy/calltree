package com.sample.calltree.figure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;

public class CTItemSideFigure extends Figure {
	
	private Figure sideTopFigure;
	private Figure sideBottomFigure;
	private Figure sideCenterFigure;
	
	public CTItemSideFigure() {
//		setOpaque(true);
		setBorder(null);
		setLayoutManager(new BorderLayout());
		
		sideTopFigure = new Figure();
		sideBottomFigure = new Figure();
		sideCenterFigure = new Figure();
		sideCenterFigure.setOpaque(true);
		sideCenterFigure.setBackgroundColor(ColorConstants.blue);
		
		add(sideTopFigure, BorderLayout.TOP);
		add(sideCenterFigure, BorderLayout.CENTER);
		add(sideBottomFigure, BorderLayout.BOTTOM);
	}
	
	@Override
	public void setSize(int w, int h) {
		int newH = (h-3)/2;
		sideTopFigure.setSize(w, newH);
		sideBottomFigure.setSize(w, newH);
	}

	public IFigure getEndPointFigure() {
		return sideCenterFigure;
	}
}

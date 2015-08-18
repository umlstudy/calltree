package com.sample.calltree.figure;

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.swt.SWT;

public class CTConnectionFigure extends PolylineConnection implements CTElementFigure {

	public CTConnectionFigure() {
		super();
		setLineStyle(SWT.LINE_DASH);
	}
}

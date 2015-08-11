package com.sample.calltree.figure;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.MarginBorder;

public class CTRootFigure extends FreeformLayer implements CTContainerFigure {

	public CTRootFigure() {
		super();
		this.setBorder(new MarginBorder(3));
		this.setLayoutManager(new FreeformLayout());
	}
}

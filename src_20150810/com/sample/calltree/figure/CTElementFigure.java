package com.sample.calltree.figure;

import org.eclipse.draw2d.IFigure;

import com.sample.calltree.model.CTElement;

public interface CTElementFigure extends IFigure {
	void setElement(CTElement element);
}

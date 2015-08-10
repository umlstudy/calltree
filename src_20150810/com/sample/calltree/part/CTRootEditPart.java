package com.sample.calltree.part;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;

public class CTRootEditPart extends AbstractGraphicalEditPart {

	public CTRootEditPart(CTRoot model) {
		setModel(model);
	}
	
	@Override
	protected List<CTItem> getModelChildren() {
		return ((CTRoot)getModel()).getChildren();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
	}

	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setBorder(new MarginBorder(3));
		figure.setLayoutManager(new FreeformLayout());
		figure.setBackgroundColor(ColorConstants.lightBlue);
		
		return figure;
	}

	@Override
	protected void createEditPolicies() {
	}
}
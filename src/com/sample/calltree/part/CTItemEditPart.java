package com.sample.calltree.part;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.ui.CTFigureFactory;

public class CTItemEditPart extends AbstractGraphicalEditPart implements NodeEditPart {

	public CTItemEditPart(CTItem model) {
		setModel(model);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return null;
	}

	@Override
	protected IFigure createFigure() {
		return CTFigureFactory.createFigure(getModel());
	}

	@Override
	protected void createEditPolicies() {
	}
}

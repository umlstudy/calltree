package com.sample.calltree.ui;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;
import com.sample.calltree.part.CTItemEditPart;
import com.sample.calltree.part.CTRootEditPart;

public class CTEditPartFactory implements EditPartFactory {
	
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		return createEditPart(model);
	}

	public static EditPart createEditPart(Object model) {
		if (model instanceof CTRoot) {
			return new CTRootEditPart((CTRoot) model);
		} else if (model instanceof CTItem) {
			return new CTItemEditPart((CTItem) model);
		}
		throw new IllegalStateException("No EditPart for " + model.getClass());
	}
}
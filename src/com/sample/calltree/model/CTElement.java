package com.sample.calltree.model;

import org.eclipse.core.runtime.Assert;

public abstract class CTElement {
	
	private String name;
	
	private ModelUpdateListener modelUpdateListener;

	public CTElement(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void fireModelUpdated() {
		if( getModelUpdateListener() != null ) {
			getModelUpdateListener().modelUpdated();
		}
	}

	public ModelUpdateListener getModelUpdateListener() {
		return modelUpdateListener;
	}

	public void setModelUpdateListener(ModelUpdateListener modelUpdateListener) {
		Assert.isLegal(this.modelUpdateListener == null, "this.modelUpdateListener == null");
		this.modelUpdateListener = modelUpdateListener;
	}
}

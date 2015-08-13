package com.sample.calltree.model;

import org.eclipse.core.runtime.Assert;

import com.sample.calltree.model.listener.CTElementUpdateListener;

public abstract class CTElement {
	
	private String name;
	
	private boolean allowFiringModelUpdate;
	
	private CTElementUpdateListener modelUpdateListener;

	public CTElement(String name) {
		this.setAllowFiringModelUpdate(false);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void fireModelUpdated() {
		if( allowFiringModelUpdate() && getModelUpdateListener() != null ) {
			getModelUpdateListener().modelUpdated();
		}
	}

	public CTElementUpdateListener getModelUpdateListener() {
		return modelUpdateListener;
	}

	public void setModelUpdateListener(CTElementUpdateListener modelUpdateListener) {
		Assert.isLegal(this.modelUpdateListener == null, "this.modelUpdateListener == null");
		this.modelUpdateListener = modelUpdateListener;
	}

	public boolean allowFiringModelUpdate() {
		return allowFiringModelUpdate;
	}

	public void setAllowFiringModelUpdate(boolean allowFiringModelUpdate) {
		this.allowFiringModelUpdate = allowFiringModelUpdate;
	}
}

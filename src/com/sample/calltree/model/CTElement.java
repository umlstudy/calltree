package com.sample.calltree.model;

import org.eclipse.core.runtime.Assert;

import com.sample.calltree.model.listener.CTElementUpdateListener;

public abstract class CTElement {
	
	private String name;
	
	private CTContainer owner;
	
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
	
	/**
	 * model 은 controller 를 호출함 
	 * ( controller 모델에 updateLister로 등록되어 있음 )
	 * controller 는 model 의 내용을 figure 에 반영하고 figure 를 refresh 함
	 */
	public void fireModelUpdated() {
		if( allowFiringModelUpdate() && getModelUpdateListener() != null ) {
			getModelUpdateListener().modelUpdated();
		}
	}

	public CTElementUpdateListener getModelUpdateListener() {
		return modelUpdateListener;
	}

	public void setModelUpdateListener(CTElementUpdateListener modelUpdateListener) {
		Assert.isTrue(this.modelUpdateListener == null, "this.modelUpdateListener != null");
		this.modelUpdateListener = modelUpdateListener;
	}
	
	public void removeModelUpdateListener() {
		this.modelUpdateListener = null;
	}

	public boolean allowFiringModelUpdate() {
		return allowFiringModelUpdate;
	}

	public void setAllowFiringModelUpdate(boolean allowFiringModelUpdate) {
		this.allowFiringModelUpdate = allowFiringModelUpdate;
	}
	
	public CTContainer getOwner() {
		return owner;
	}

	public void setOwner(CTContainer owner) {
		this.owner = owner;
	}
}

package com.sample.calltree.model.listener;

import com.sample.calltree.model.CTItem;

public interface CTContainerListener extends CTElementUpdateListener  {

	void modelAdded(CTItem item);
	void modelRemoved(CTItem item);
}

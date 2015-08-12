package com.sample.calltree.model.listener;

import com.sample.calltree.model.CTConnection;

public interface CTRootListener extends CTElementUpdateListener {

	void connAdded(CTConnection conn);
	void connRemoved(CTConnection conn);
}

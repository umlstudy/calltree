package com.sample.calltree.main;

import java.util.Random;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredViewer;

import com.sample.calltree.model.CTItem;

public class AddAction extends Action {
	
	private CTItem selected;
	private StructuredViewer viewer;
	
	public AddAction(CTItem selected, StructuredViewer viewer) {
		super();
		this.selected = selected;
		this.viewer = viewer;
	}
	
	Random randomGenerator = new Random();
	
	public void run() {
		CTItem item = new CTItem("aabbcc");
		int x = randomGenerator.nextInt(190)+10;
		int y = randomGenerator.nextInt(190)+10;
		
		item.setLocation(new Point(x,y));
		int w = randomGenerator.nextInt(100)+10;
		int h = randomGenerator.nextInt(100)+10;
		item.setDimension(new Dimension(w,h));
		selected.setNeedUpdateModel(true);
		selected.addChild(item);
		viewer.refresh();
	}
}

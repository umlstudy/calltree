package com.sample.calltree.main.action;

import java.util.Random;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredViewer;

import com.sample.calltree.model.CTItem;

public class HoldAction extends Action {
	
	private CTItem selected;
	private StructuredViewer viewer;
	
	public HoldAction(CTItem selected, StructuredViewer viewer) {
		super("홀드");
		this.selected = selected;
		this.viewer = viewer;
	}
	
	Random randomGenerator = new Random();
	
	public void run() {
		System.out.println("홀드하였습니다.");
//		CTItem item = new CTItem("aabbcc");
//		int x = randomGenerator.nextInt(190)+10;
//		int y = randomGenerator.nextInt(190)+10;
//		
//		item.setLocation(new Point(x,y));
//		int w = randomGenerator.nextInt(100)+10;
//		int h = randomGenerator.nextInt(100)+10;
//		item.setDimension(new Dimension(w,h));
//		selected.setAllowFiringModelUpdate(true);
//		selected.addChild(item);
//		selected.getRoot().arrangeChildSizeLocations();
//		viewer.refresh();
	}
}
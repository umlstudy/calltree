package com.sample.calltree.figure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;

public class CTItemFigure extends RoundedRectangle implements CTContainerFigure {

	private Figure centerFigure;

//	private CTItemSideFigure leftSideFigure;
//	private CTItemSideFigure rightSideFigure;
	private CTItemSideFigure topSideFigure;
	private CTItemSideFigure bottomSideFigure;
	
	public CTItemFigure() {
		centerFigure = new Label();
		centerFigure.setOpaque(true);
		setBorder(null);
		
//		leftSideFigure = new CTItemSideFigure();
//		rightSideFigure = new CTItemSideFigure();
		topSideFigure = new CTItemSideFigure();
		bottomSideFigure = new CTItemSideFigure();

		setLayoutManager(new BorderLayout());
		add(centerFigure, BorderLayout.CENTER);
//		add(leftSideFigure, BorderLayout.LEFT);
//		add(rightSideFigure, BorderLayout.RIGHT);
		add(topSideFigure, BorderLayout.TOP);
		add(bottomSideFigure, BorderLayout.BOTTOM);

//		leftEndPoingFigure.addMouseListener(new MouseListener.Stub() {
//			public void mouseReleased(MouseEvent me) {
//				System.out.println("AAAA");
//			}
//		});
		
	}
	
	public void setText(String text) {
		if ( centerFigure instanceof Label ) {
			((Label)centerFigure).setText(text);
		}
	}
	
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		
//		leftSideFigure.setSize(3, rect.height);
//		rightSideFigure.setSize(3, rect.height);
		topSideFigure.setSize(rect.width, 3);
		bottomSideFigure.setSize(rect.width, 3);
	}

//	public IFigure getLeftEndPointFigure() {
//		return leftSideFigure.getEndPointFigure();
//	}
	public IFigure getTopEndPointFigure() {
		return topSideFigure.getEndPointFigure();
	}
	
//	public IFigure getRightEndPointFigure() {
//		return rightSideFigure.getEndPointFigure();
//	}
	public IFigure getBottomEndPointFigure() {
		return bottomSideFigure.getEndPointFigure();
	}
}

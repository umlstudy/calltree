package com.sample.calltree.figure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;

public class CTItemFigure extends RoundedRectangle implements CTContainerFigure {

	private Figure centerFigure;

	private CTItemSideFigure leftSideFigure;
	private CTItemSideFigure rightSideFigure;
	
	public CTItemFigure() {
		centerFigure = new Label();
		centerFigure.setOpaque(true);
		setBorder(null);
		
		leftSideFigure = new CTItemSideFigure();
		rightSideFigure = new CTItemSideFigure();

		setLayoutManager(new BorderLayout());
		add(centerFigure, BorderLayout.CENTER);
		add(leftSideFigure, BorderLayout.LEFT);
		add(rightSideFigure, BorderLayout.RIGHT);

//		leftEndPoingFigure.addMouseListener(new MouseListener.Stub() {
//			public void mouseReleased(MouseEvent me) {
//				System.out.println("AAAA");
//			}
//		});
		
	}
	
	public void setText(String text) {
		//label.setText(text);
	}
	
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		
		leftSideFigure.setSize(3, rect.height);
		rightSideFigure.setSize(3, rect.height);
	}

	public IFigure getLeftEndPointFigure() {
		return leftSideFigure.getEndPointFigure();
	}

	public IFigure getRightEndPointFigure() {
		return rightSideFigure.getEndPointFigure();
	}
}

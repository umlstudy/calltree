package com.sample.calltree.figure;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;

public class CTItemFigure extends RoundedRectangle implements CTContainerFigure {

	private Figure label;
	private Figure sourceEndPoingFigure;
	private Figure targetEndPoingFigure;
	private Figure st;
	private Figure sb;
	private Figure tt;
	private Figure tb;
	
	public CTItemFigure() {
		label = new RectangleFigure();
		label.setOpaque(true);
		//label.setBackgroundColor(ColorConstants.lightBlue);

		Figure srcFigureBase = new Figure();
		srcFigureBase.setLayoutManager(new BorderLayout());
		st = new Figure();
		st.setBackgroundColor(ColorConstants.black);
		st.setOpaque(true);
		sb = new Figure();
		sb.setBackgroundColor(ColorConstants.black);
		sb.setOpaque(true);
		sourceEndPoingFigure = new Figure();
		srcFigureBase.add(st, BorderLayout.TOP);
		srcFigureBase.add(sourceEndPoingFigure, BorderLayout.CENTER);
		srcFigureBase.add(sb, BorderLayout.BOTTOM);
		srcFigureBase.setSize(3,13);
		sourceEndPoingFigure.setOpaque(true);
		sourceEndPoingFigure.setBackgroundColor(ColorConstants.blue);
		
		
		Figure tarFigureBase = new Figure();
		tarFigureBase.setLayoutManager(new BorderLayout());
		tt = new Figure();
		tb = new Figure();
		targetEndPoingFigure = new Figure();
		tarFigureBase.add(tt, BorderLayout.TOP);
		tarFigureBase.add(targetEndPoingFigure, BorderLayout.CENTER);
		tarFigureBase.add(tb, BorderLayout.BOTTOM);
		tarFigureBase.setSize(3,3);
		targetEndPoingFigure.setOpaque(true);
		targetEndPoingFigure.setBackgroundColor(ColorConstants.blue);

		setLayoutManager(new BorderLayout());
		add(label, BorderLayout.CENTER);
		add(srcFigureBase, BorderLayout.LEFT);
		add(tarFigureBase, BorderLayout.RIGHT);
//		add(sourceEndPoingFigure);
//		add(targetEndPoingFigure);
//		add(srcFigureBase);
//		add(tarFigureBase);
		sourceEndPoingFigure.addMouseListener(new MouseListener.Stub() {
			public void mouseReleased(MouseEvent me) {
				System.out.println("AAAA");
			}
		});
		
//		BorderLayout layout = setBorderLayout();
//		layout.setConstraint(srcFigureBase, BorderLayout.LEFT);
//		layout.setConstraint(tarFigureBase, BorderLayout.RIGHT);
	}
	
//	private BorderLayout setBorderLayout() {
//		BorderLayout layout = new BorderLayout();
//		setLayoutManager(layout);
//		layout.setConstraint(label, BorderLayout.CENTER);
//		layout.setHorizontalSpacing(0);
//		layout.setVerticalSpacing(0);
//		
//		return layout;
//	}

	public void setText(String text) {
		//label.setText(text);
	}
//
//	public void setBounds(Rectangle rect) {
//		label.setBounds(new Rectangle(0, 0, rect.getSize().width, rect.getSize().height));
//		super.setBounds(rect);
//	}
	
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		//label.setSize(rect.width-16, rect.height);
		//sourceEndPoingFigure.setSize(3, 3);
		//targetEndPoingFigure.setSize(3, 3);
		st.setSize(3,13);
		sb.setSize(3,13);
		tt.setSize(3,13);
		tb.setSize(3,13);
		
		//label.setBounds(new Rectangle(3,0,rect.width-6, rect.height));
		//leftFigure.setBounds(new Rectangle(3,0,rect.width-6, rect.height));
	}

	public IFigure getSourceEndPoingFigure() {
		return sourceEndPoingFigure;
	}

	public IFigure getTargetEndPoingFigure() {
		return targetEndPoingFigure;
	}
}

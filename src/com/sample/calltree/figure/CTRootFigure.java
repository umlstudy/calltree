package com.sample.calltree.figure;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.swt.graphics.Color;

public class CTRootFigure extends ScalableFreeformLayeredPane implements CTElementFigure {

//	private GridLayer gridLayer;
	private FreeformLayer primary;
	private ConnectionLayer connections;
	
	public CTRootFigure() {
		super();

//		gridLayer = new GridLayer();
//		gridLayer.setBorder(new MarginBorder(3));
//		gridLayer.setLayoutManager(new FreeformLayout());
//		this.add(gridLayer, "gridLayer");
		
		primary = new FreeformLayer();
		primary.setBorder(new MarginBorder(3));
		primary.setLayoutManager(new FreeformLayout());
		this.add(primary, "Primary");

		connections = new ConnectionLayer();
		connections.setConnectionRouter(new ShortestPathConnectionRouter(primary));
		this.add(connections, "Connections");
	}
	
	@Override
	public void setBackgroundColor(Color background) {
		primary.setBackgroundColor(background);
//		gridLayer.setBackgroundColor(background);
		super.setBackgroundColor(background);
	}
	
	@Override
	public void add(IFigure figure, int index) {
		primary.add(figure, index);
	}
}

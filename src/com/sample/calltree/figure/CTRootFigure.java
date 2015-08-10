package com.sample.calltree.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ShortestPathConnectionRouter;

public class CTRootFigure extends ScalableFreeformLayeredPane implements CTElementFigure {

	private FreeformLayer primary;
	private ConnectionLayer connections;
	
	public CTRootFigure() {
		super();
		
		primary = new FreeformLayer();
		primary.setBorder(new MarginBorder(3));
		primary.setBackgroundColor(ColorConstants.lightBlue);
		primary.setLayoutManager(new FreeformLayout());
		this.add(primary, "Primary");

		connections = new ConnectionLayer();
		connections.setConnectionRouter(new ShortestPathConnectionRouter(primary));
		this.add(connections, "Connections");
	}
}

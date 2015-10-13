package com.sample.calltree.model.util;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import com.sample.calltree.model.CTItem;

public class CTItemPositioner {
	
	private static final int ITEM_WIDTH = 120;
	private static final int ITEM_HEIGHT = 30;
	private static final int ROW_SIZE_WITH_MARGIN = ITEM_HEIGHT + 180;
	private static final int COLUMN_SIZE_WITH_MARGIN = ITEM_WIDTH + 20;
	
	public static void arrangeChildSizeLocations(List<CTItem> ctItems, boolean horizontal) {
		if ( horizontal ) {
			arrangeChildSizeLocationsHorizontal(ctItems);
		} else {
			arrangeChildSizeLocationsVertical(ctItems);
		}
	}
	
	private static void arrangeChildSizeLocationsVertical(List<CTItem> ctItems) {
		CTItem[][] ctItems2Matrix = CTItems2MatrixConverter.convert(ctItems);
		ctItems2Matrix = converMatrixRowColumns(ctItems2Matrix);
		arrangeChildSizeLocationsHorizontal(ctItems2Matrix, false);
	}

	private static CTItem[][] converMatrixRowColumns(CTItem[][] original) {
		CTItem[][] converted = new CTItem[original[0].length][];
		
		for ( int newRow = 0; newRow < converted.length; newRow++ ) {
			CTItem[] itemsOnRow = new CTItem[original.length];
			for ( int oriRow=0;oriRow<original.length;oriRow++) {
				itemsOnRow[oriRow] = original[oriRow][newRow];
			}
			converted[newRow] = itemsOnRow;
		}
		
		return converted;
	}

	private static void arrangeChildSizeLocationsHorizontal(List<CTItem> ctItems) {
		CTItem[][] ctItems2Matrix = CTItems2MatrixConverter.convert(ctItems);
		arrangeChildSizeLocationsHorizontal(ctItems2Matrix, true);
	}
	
	private static void arrangeChildSizeLocationsHorizontal(CTItem[][] ctItems2Matrix, boolean adjustTopLocation) {
		//
		int maxRowCnt = 0;
		for ( int col=0;col<ctItems2Matrix.length; col++ ) {
			int curRowCnt = CTItems2MatrixConverter.getNotNullCount(ctItems2Matrix[col]);
			maxRowCnt = maxRowCnt > curRowCnt ? maxRowCnt : curRowCnt;
		}
		int curX = 0;
		for ( int col=0;col<ctItems2Matrix.length; col++ ) {
			int curY = 0;
			
			if ( adjustTopLocation ) {
				int curRowCnt = CTItems2MatrixConverter.getNotNullCount(ctItems2Matrix[col]);
				if ( curRowCnt < maxRowCnt ) {
					if ( (maxRowCnt % 2) != (curRowCnt % 2) ) {
						curY = ROW_SIZE_WITH_MARGIN/2;
					}
				}
			}
			
			for ( int row=0;row<ctItems2Matrix[col].length; row++ ) {
				if ( ctItems2Matrix[col][row] != null ) {
					CTItem item = ctItems2Matrix[col][row];
					boolean sizeOrLocationChanged = false;
					
					// location
					Point oldLoc = item.getLocation();
					if ( curX != oldLoc.x || curY != oldLoc.y ) {
						item.setLocation(new Point(curX, curY));
						sizeOrLocationChanged = true;
					}
					
					// size
					Dimension oldSize = item.getDimension();
					if ( ITEM_WIDTH != oldSize.width || ITEM_HEIGHT != oldSize.height ) {
						item.setDimension(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
						sizeOrLocationChanged = true;
					}
					
					if ( sizeOrLocationChanged ) {
						item.setAllowFiringModelUpdate(true);
						item.fireModelUpdated();
					}
				}
				
				curY += ROW_SIZE_WITH_MARGIN;
			}
			curX += COLUMN_SIZE_WITH_MARGIN;
		}
	}
}

package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sample.calltree.model.listener.CTRootListener;
import com.sample.calltree.model.util.CTItemPositioner;

public class CTRoot extends CTContainer {

	private List<CTConnection> connections;
	
	public CTRoot(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	public List<CTConnection> getConnections() {
		if ( connections == null ) {
			return Collections.EMPTY_LIST;
		}
		return connections;
	}
	
	public boolean addConnection(CTConnection connection) {
		if ( connections == null ) {
			connections = new ArrayList<CTConnection>();
		}
		boolean rslt = false;
		if ( !connections.contains(connection) ) {
			rslt = connections.add(connection);
			if ( allowFiringModelUpdate() && getModelUpdateListener() != null ) {
				((CTRootListener)getModelUpdateListener()).connAdded(connection);
			}
		}
		
		return rslt;
	}
	
//	private static final int ITEM_WIDTH = 120;
//	private static final int ITEM_HEIGHT = 30;
//	
//	public void arrangeChildSizeLocations() {
//		updateSizeAndLocation();
////		updateConnection();
//	}
	
//	private void updateConnection() {
//		updateConnection(null, getChildItems());
//	}
//	
//	public void updateConnection(CTItem parentItem, List<CTItem> ctItems) {
//		for ( CTItem item : ctItems ) {
//			if ( parentItem != null ) {
//				CTConnection ctConnection = CTConnection.newInstance("con");
//				ctConnection.setSource(parentItem);
//				ctConnection.setTarget(item);
//				// 이미 등록된 연결이 아닐 경우에만 등록
//				if ( !getConnections().contains(ctConnection) ) {
//					addConnection(ctConnection);
//				}
//			}
//			// 자식아이템에 대하여 재귀호출
//			if ( item.getChildItems().size() > 0 ) {
//				updateConnection(item, item.getChildItems());
//			}
//		}
//	}
	
	public void arrangeChildSizeLocations() {
		// TODO
		arrangeChildSizeLocations(false);
	}
	
	private void arrangeChildSizeLocations(boolean horizontal) {
		List<CTItem> childItems = getChildItems(ChildItemSelectOptions.VisibleOnly);
		CTItemPositioner.arrangeChildSizeLocations(childItems, horizontal);
	}
	
//	private void arrangeChildSizeLocationsVertical() {
//		CTItem[][] ctItems2Matrix = CTItems2MatrixConverter.convert(getChildItems(ChildItemSelectOptions.VisibleOnly));
//		
//		//
//		int maxRowCnt = 0;
//		for ( int col=0;col<ctItems2Matrix.length; col++ ) {
//			int curRowCnt = CTItems2MatrixConverter.getNotNullCount(ctItems2Matrix[col]);
//			maxRowCnt = maxRowCnt > curRowCnt ? maxRowCnt : curRowCnt;
//		}
//		int curX = 0;
//		for ( int col=0;col<ctItems2Matrix.length; col++ ) {
//			int curY = 0;
//			int curRowCnt = CTItems2MatrixConverter.getNotNullCount(ctItems2Matrix[col]);
//			if ( curRowCnt < maxRowCnt ) {
//				if ( (maxRowCnt % 2) != (curRowCnt % 2) ) {
////					curY  = (curRowCnt % 2) == 1 ? 20 : 0;
//					curY = 20;
//				}
////				curY *= (maxRowCnt % 2) == 0 ? -1 : 1;
//			}
//			for ( int row=0;row<ctItems2Matrix[col].length; row++ ) {
//				if ( ctItems2Matrix[col][row] != null ) {
//					CTItem item = ctItems2Matrix[col][row];
//					boolean sizeOrLocationChanged = false;
//					
//					// location
//					Point oldLoc = item.getLocation();
//					if ( curX != oldLoc.x || curY != oldLoc.y ) {
//						item.setLocation(new Point(curX, curY));
//						sizeOrLocationChanged = true;
//					}
//					
//					// size
//					Dimension oldSize = item.getDimension();
//					if ( ITEM_WIDTH != oldSize.width || ITEM_HEIGHT != oldSize.height ) {
//						item.setDimension(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
//						sizeOrLocationChanged = true;
//					}
//					
//					if ( sizeOrLocationChanged ) {
//						item.setAllowFiringModelUpdate(true);
//						item.fireModelUpdated();
//					}
//				}
//				
//				curY += 40;
//			}
//			curX += 170;
//		}
//	}
//
//	private void arrangeChildSizeLocationsHorizontal() {
//		CTItem[][] ctItems2Matrix = CTItems2MatrixConverter.convert(getChildItems(ChildItemSelectOptions.VisibleOnly));
//
//		//
//		int maxRowCnt = 0;
//		for ( int col=0;col<ctItems2Matrix.length; col++ ) {
//			int curRowCnt = CTItems2MatrixConverter.getNotNullCount(ctItems2Matrix[col]);
//			maxRowCnt = maxRowCnt > curRowCnt ? maxRowCnt : curRowCnt;
//		}
//		int curX = 0;
//		for ( int col=0;col<ctItems2Matrix.length; col++ ) {
//			int curY = 0;
//			int curRowCnt = CTItems2MatrixConverter.getNotNullCount(ctItems2Matrix[col]);
//			if ( curRowCnt < maxRowCnt ) {
//				if ( (maxRowCnt % 2) != (curRowCnt % 2) ) {
////					curY  = (curRowCnt % 2) == 1 ? 20 : 0;
//					curY = 20;
//				}
////				curY *= (maxRowCnt % 2) == 0 ? -1 : 1;
//			}
//			for ( int row=0;row<ctItems2Matrix[col].length; row++ ) {
//				if ( ctItems2Matrix[col][row] != null ) {
//					CTItem item = ctItems2Matrix[col][row];
//					boolean sizeOrLocationChanged = false;
//					
//					// location
//					Point oldLoc = item.getLocation();
//					if ( curX != oldLoc.x || curY != oldLoc.y ) {
//						item.setLocation(new Point(curX, curY));
//						sizeOrLocationChanged = true;
//					}
//					
//					// size
//					Dimension oldSize = item.getDimension();
//					if ( ITEM_WIDTH != oldSize.width || ITEM_HEIGHT != oldSize.height ) {
//						item.setDimension(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
//						sizeOrLocationChanged = true;
//					}
//					
//					if ( sizeOrLocationChanged ) {
//						item.setAllowFiringModelUpdate(true);
//						item.fireModelUpdated();
//					}
//				}
//				
//				curY += 40;
//			}
//			curX += 170;
//		}
//	}
}

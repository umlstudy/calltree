package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.List;

import com.sample.calltree.model.CTContainer.ChildItemSelectOptions;

public class CTItems2MatrixConverter {
	
	public static CTItem[][] convert(List<CTItem> ctItems) {
		List<List<CTItem>> colDatas = new ArrayList<List<CTItem>>();
		convert(colDatas, ctItems, 0);
		
		int column = 0;
		int row = 0;
		for ( List<CTItem> items : colDatas ) {
			column ++;
			row = row > items.size() ? row : items.size();
		}
		
		CTItem[][] matrix = new CTItem[column][row];
		for ( int curCol=0;curCol<column;curCol++) {
			int curRow = 0;
			List<CTItem> items = colDatas.get(curCol);
			for ( CTItem item : items ) {
				matrix[curCol][curRow] = item;
				curRow++;
			}	
		}
		
		return matrix;
		
	}

	private static void convert(List<List<CTItem>> colDatas, List<CTItem> ctItems, int column) {
		
		for ( CTItem item : ctItems ) {
			List<CTItem> rowDatas = null;
			if ( (colDatas.size()-1)>= column ) {
				rowDatas = colDatas.get(column);
			} else {
				rowDatas = new ArrayList<CTItem>();
				colDatas.add(rowDatas);
			}
			
			// column
			rowDatas.add(item);
			
			List<CTItem> visibleChildItems = item.getChildItems(ChildItemSelectOptions.VisibleOnly);
			if ( visibleChildItems.size() > 0 ) {
				convert(colDatas, visibleChildItems, column+1);
			}
		}
	}

	public static CTItem[][] verticalCenter(CTItem[][] ctItems2Matrix) {
		CTItem[][] rslt = new CTItem[ctItems2Matrix.length][];
		for ( int column = 0; column<ctItems2Matrix.length; column++ ) {
			int rowCount = ctItems2Matrix[column].length;
			int notNullCount = getNotNullCount(ctItems2Matrix[column]);
			rslt[column] = new CTItem[rowCount];
			int midStartIdx = (rowCount - notNullCount) / 2;
			// copy
			for ( int rowIdx=0;rowIdx<notNullCount;rowIdx++) {
				rslt[column][midStartIdx+rowIdx] = ctItems2Matrix[column][rowIdx];
			}
		}
		return rslt;
	}

	public static int getNotNullCount(CTItem[] row) {
		int count = 0;
		for ( int i=0; i<row.length; i++ ) {
			if ( row[i] != null ) {
				count++;
			}
		}
		
		return count;
	}
}

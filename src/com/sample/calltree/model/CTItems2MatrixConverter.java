package com.sample.calltree.model;

import java.util.ArrayList;
import java.util.List;

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
			
			if ( item.getChildItems().size() > 0 ) {
				convert(colDatas, item.getChildItems(), column+1);
			}
		}
	}
}

package com.mousbah.mysis.web.client.common;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;


public class GridPaneHelper {
	
	private Grid gridPane;
	private int rowIndex=0;

	public GridPaneHelper(Grid gridPane) {
		this.gridPane = gridPane;
	}
	
	public void addRowToGridPane(Widget... widgets){
		int columnIndex=0;
		for(Widget widget:widgets){
			gridPane.setWidget(rowIndex, columnIndex++, widget);
		}
		rowIndex++;
	}

}

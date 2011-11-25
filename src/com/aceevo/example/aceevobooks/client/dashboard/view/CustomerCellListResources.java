package com.aceevo.example.aceevobooks.client.dashboard.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;

public interface CustomerCellListResources extends CellList.Resources {

	public static CustomerCellListResources INSTANCE = GWT.create(CustomerCellListResources.class);

	@Override
	@Source("customerCellListStyle.css")
	public Style cellListStyle();
}

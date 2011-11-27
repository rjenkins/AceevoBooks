package com.aceevo.example.aceevobooks.client.view;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractAceevoBooksPage extends Composite implements BreadCrumbView, AceevoBooksView {

	@UiField
	public HeaderView headerView;

	@Override
	public void setBreadCrumb(HTML html) {
		headerView.setBreadCrumb(html);
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

}

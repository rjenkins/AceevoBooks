package com.aceevo.example.aceevobooks.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderView extends Composite {

	interface HeaderViewUiBinder extends UiBinder<HTMLPanel, HeaderView> {
	}

	private static HeaderViewUiBinder headerViewUiBinder = GWT.create(HeaderViewUiBinder.class);

	@UiField
	FlowPanel breadCrumb = new FlowPanel();

	public HeaderView() {
		initWidget(headerViewUiBinder.createAndBindUi(this));
	}

	public void setBreadCrumb(String crumb, String href) {
		breadCrumb.clear();
		breadCrumb.add(new HTML("You are here: <a href=\"" + href + "\">" + crumb + "</a>"));
	}

	public void setBreadCrumb(String crumb) {
		breadCrumb.clear();
		HTML html = new HTML(crumb);
		breadCrumb.add(html);
	}

	public void setBreadCrumb(HTML html) {
		breadCrumb.clear();
		breadCrumb.add(html);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

}

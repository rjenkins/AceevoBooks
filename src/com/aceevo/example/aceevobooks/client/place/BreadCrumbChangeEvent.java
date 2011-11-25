package com.aceevo.example.aceevobooks.client.place;

import com.aceevo.example.aceevobooks.client.view.BreadCrumbView;
import com.google.gwt.event.shared.GwtEvent;

public class BreadCrumbChangeEvent extends GwtEvent<BreadCrumbChangeEventHandler> {
	public static Type<BreadCrumbChangeEventHandler> TYPE = new Type<BreadCrumbChangeEventHandler>();

	BreadCrumbView breadCrumbView;

	public BreadCrumbChangeEvent(BreadCrumbView breadCrumbView) {
		this.breadCrumbView = breadCrumbView;
	}
	
	public BreadCrumbView getBreadCrumbView() {
		return breadCrumbView;
	}

	@Override
	protected void dispatch(BreadCrumbChangeEventHandler handler) {
		handler.onBreadCrumbChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BreadCrumbChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

}
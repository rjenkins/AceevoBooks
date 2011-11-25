package com.aceevo.example.aceevobooks.client.customer.event;

import com.google.gwt.event.shared.GwtEvent;

public class CustomerAddEvent extends GwtEvent<CustomerAddEventHandler> {
	public static Type<CustomerAddEventHandler> TYPE = new Type<CustomerAddEventHandler>();

	@Override
	protected void dispatch(CustomerAddEventHandler handler) {
		handler.onCustomerAdd(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CustomerAddEventHandler> getAssociatedType() {
		return TYPE;
	}

}
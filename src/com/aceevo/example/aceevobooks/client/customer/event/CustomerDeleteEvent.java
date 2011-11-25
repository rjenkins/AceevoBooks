package com.aceevo.example.aceevobooks.client.customer.event;

import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.google.gwt.event.shared.GwtEvent;

public class CustomerDeleteEvent extends GwtEvent<CustomerDeleteEventHandler> {
	public static Type<CustomerDeleteEventHandler> TYPE = new Type<CustomerDeleteEventHandler>();

	public CustomerProxy customerProxy;

	public CustomerDeleteEvent(CustomerProxy customerProxy) {
		this.customerProxy = customerProxy;
	}

	@Override
	protected void dispatch(CustomerDeleteEventHandler handler) {
		handler.onCustomerDelete(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CustomerDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

}
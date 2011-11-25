package com.aceevo.example.aceevobooks.client.customer.event;

import com.google.gwt.event.shared.EventHandler;

public interface CustomerDeleteEventHandler extends EventHandler {
	void onCustomerDelete(CustomerDeleteEvent customerDeleteEvent);
}

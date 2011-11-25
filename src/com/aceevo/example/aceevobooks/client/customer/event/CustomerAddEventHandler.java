package com.aceevo.example.aceevobooks.client.customer.event;

import com.google.gwt.event.shared.EventHandler;

public interface CustomerAddEventHandler extends EventHandler {
	void onCustomerAdd(CustomerAddEvent customerAddEvent);
}

package com.aceevo.example.aceevobooks.client.customer.event;

import com.google.gwt.event.shared.EventHandler;

public interface InvoiceDeleteEventHandler extends EventHandler {
	void onInvoiceDelete(InvoiceDeleteEvent invoiceDeleteEvent);
}

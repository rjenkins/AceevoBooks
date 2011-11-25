package com.aceevo.example.aceevobooks.client.customer.event;

import com.aceevo.example.aceevobooks.client.model.InvoiceProxy;
import com.google.gwt.event.shared.GwtEvent;

public class InvoiceDeleteEvent extends GwtEvent<InvoiceDeleteEventHandler> {
	public static Type<InvoiceDeleteEventHandler> TYPE = new Type<InvoiceDeleteEventHandler>();

	public InvoiceProxy invoiceProxy;

	public InvoiceDeleteEvent(InvoiceProxy invoiceProxy) {
		this.invoiceProxy = invoiceProxy;
	}

	@Override
	protected void dispatch(InvoiceDeleteEventHandler handler) {
		handler.onInvoiceDelete(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<InvoiceDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

}
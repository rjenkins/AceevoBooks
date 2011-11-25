package com.aceevo.example.aceevobooks.client.requests;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface AceevoBooksRequestFactory extends RequestFactory {

	CustomerRequest customerRequest();

	InvoiceRequest invoiceRequest();

}

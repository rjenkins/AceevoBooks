package com.aceevo.example.aceevobooks.client.requests;

import java.util.List;

import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.client.model.InvoiceProxy;
import com.aceevo.example.aceevobooks.server.model.Invoice;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Invoice.class)
public interface InvoiceRequest extends RequestContext {

	Request<List<InvoiceProxy>> findAllInvoices();

	Request<List<InvoiceProxy>> findInvoicesByCustomerId(String customerId, String invoiceState);

	Request<InvoiceProxy> findById(String id);

	InstanceRequest<InvoiceProxy, InvoiceProxy> persist();

	Request<Double> findOutstandingInvoiceTotal();

	Request<Double> findPaidInvoiceTotal();

	InstanceRequest<InvoiceProxy, Void> remove();

}

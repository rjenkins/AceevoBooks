package com.aceevo.example.aceevobooks.client.model;

import java.util.Date;

import com.aceevo.example.aceevobooks.server.model.Invoice;
import com.aceevo.example.aceevobooks.shared.InvoiceState;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Invoice.class)
public interface InvoiceProxy extends AbstractEntityProxy {

	String getCustomerId();
	
	void setCustomerId(String customerId);

	Integer getInvoiceNumber();
	
	void setInvoiceNumber(Integer invoiceNumber);

	Date getDate();
	
	void setDate(Date date);

	String getDescription();
	
	void setDescription(String description);

	Integer getRate();
	
	void setRate(Integer rate);

	Integer getHours();
	
	void setHours(Integer hours);

	Double getInvoiceTotal();
	
	InvoiceState getInvoiceState();
	
	void setInvoiceState(InvoiceState invoiceState);
	
}

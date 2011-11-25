package com.aceevo.example.aceevobooks.client.model;

import com.aceevo.example.aceevobooks.server.model.Customer;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Customer.class)
public interface CustomerProxy extends AbstractEntityProxy {

	String getName();
	void setName(String name);
	AddressProxy getAddress();
	void setAddress(AddressProxy addressProxy);
}

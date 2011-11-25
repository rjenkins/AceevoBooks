package com.aceevo.example.aceevobooks.client.model;

import com.aceevo.example.aceevobooks.server.model.Address;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(Address.class)
public interface AddressProxy extends ValueProxy {

	String getAddress();

	void setAddress(String address);

	String getCity();

	void setCity(String city);

	String getState();

	void setState(String state);

	String getCountry();

	void setCountry(String country);

	String getZip();

	void setZip(String zip);

}

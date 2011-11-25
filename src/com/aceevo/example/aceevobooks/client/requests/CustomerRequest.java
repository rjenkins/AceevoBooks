package com.aceevo.example.aceevobooks.client.requests;

import java.util.List;

import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.server.model.Customer;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Customer.class)
public interface CustomerRequest extends RequestContext {

	Request<List<CustomerProxy>> findAllCustomers();

	Request<CustomerProxy> findById(String id);

	InstanceRequest<CustomerProxy, CustomerProxy> persist();

	InstanceRequest<CustomerProxy, Void> remove();

}

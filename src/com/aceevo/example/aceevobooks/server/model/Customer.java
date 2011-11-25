package com.aceevo.example.aceevobooks.server.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.aceevo.example.aceevobooks.server.framework.EntityManager;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

@Entity
public class Customer extends PersistentEntity {

	@NotNull
	private String name;

	@Embedded
	private Address address;

	public Customer() {
		super();
	}

	public void setName(String companyName) {
		this.name = companyName;
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public static List<Customer> findAllCustomers() {
		return EntityManager.getInstance().getDs().find(Customer.class).asList();

	}

	public static Customer findById(String id) {
		return EntityManager.getInstance().getDs().find(Customer.class, "id", id).get();

	}

	public static Customer findCustomer(String id) {
		return EntityManager.getInstance().getDs().find(Customer.class, "id", id).get();

	}

	public Customer persist() {
		EntityManager.getInstance().persist(this);
		return this;
	}

	public void remove() {
		EntityManager.getInstance().getDs().delete(this);
	}

}

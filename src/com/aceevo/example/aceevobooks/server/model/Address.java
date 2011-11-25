package com.aceevo.example.aceevobooks.server.model;

import javax.validation.constraints.NotNull;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Address {

	@NotNull
	private String address;
	@NotNull
	private String city;
	@NotNull
	private String state;
	@NotNull
	private String country;
	@NotNull
	private String zip;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(String address, String city, String state, String country, String zip) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}

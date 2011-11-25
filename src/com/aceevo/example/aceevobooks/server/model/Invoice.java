package com.aceevo.example.aceevobooks.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.aceevo.example.aceevobooks.server.framework.EntityManager;
import com.aceevo.example.aceevobooks.shared.InvoiceState;
import com.google.code.morphia.annotations.Entity;

@Entity
public class Invoice extends PersistentEntity {

	private static final String CUSTOMER_ID = "customerId";
	
	private String customerId;
	private Integer invoiceNumber;

	@NotNull
	private Date date;

	@NotNull
	private String Description;

	@Min(value = 0)
	@Max(value = 1000)
	@NotNull
	private Integer rate = null;

	@Min(value = 0)
	@Max(value = 1000)
	@NotNull
	private Integer hours = null;

	@NotNull
	private InvoiceState invoiceState;

	public Invoice() {
		super();
	}

	public InvoiceState getInvoiceState() {
		return invoiceState;
	}

	public void setInvoiceState(InvoiceState invoiceState) {
		this.invoiceState = invoiceState;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getRate() {
		return rate;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public static List<Invoice> findAllInvoices() {
		return EntityManager.getInstance().getDs().find(Invoice.class).asList();
	}

	public Double getInvoiceTotal() {
		return new Double(rate * hours);
	}

	public static Invoice findById(String id) {
		return EntityManager.getInstance().getDs().find(Invoice.class, "id", id).get();
	}

	public Invoice persist() {
		EntityManager.getInstance().persist(this);
		return this;
	}

	public void remove() {
		EntityManager.getInstance().getDs().delete(this);
	}

	public static Double findOutstandingInvoiceTotal() {
		return findInvoiceTotalByType(InvoiceState.Outstanding);
	}

	public static Double findPaidInvoiceTotal() {
		return findInvoiceTotalByType(InvoiceState.Paid);
	}

	public static List<Invoice> findInvoicesByCustomerId(String customerId, String invoiceState) {

		List<Invoice> invoices = EntityManager.getInstance().getDs().find(Invoice.class,
				CUSTOMER_ID, customerId).asList();

		List<Invoice> invoiceList = new ArrayList<Invoice>();
		InvoiceState invoiceStateEnum = InvoiceState.valueOf(invoiceState);

		for (Invoice invoice : invoices) {
			if (invoice.getCustomerId().toString().equals(customerId))
				if (invoiceStateEnum.equals(InvoiceState.All)
						|| invoiceStateEnum.equals(invoice.getInvoiceState()))
					invoiceList.add(invoice);
		}

		return invoiceList;
	}

	private static Double findInvoiceTotalByType(InvoiceState invoiceState) {
		double invoiceTotal = 0.00;

		List<Invoice> invoices = EntityManager.getInstance().getDs().find(Invoice.class).asList();
		for (Invoice invoice : invoices) {
			if (invoice.getInvoiceState().equals(invoiceState)) {
				double total = invoice.getHours() * invoice.getRate();
				invoiceTotal += total;
			}
		}
		return new Double(invoiceTotal);

	}

	public static Invoice findInvoice(String id) {
		return EntityManager.getInstance().getDs().find(Invoice.class, "id", id).get();
	}

}

package com.aceevo.example.aceevobooks.server.framework;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.aceevo.example.aceevobooks.server.model.Address;
import com.aceevo.example.aceevobooks.server.model.Customer;
import com.aceevo.example.aceevobooks.server.model.Invoice;
import com.aceevo.example.aceevobooks.server.model.PersistentEntity;
import com.aceevo.example.aceevobooks.shared.InvoiceState;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class EntityManager {

	private static final String[] names = new String[] { "Charlies Plumbing", "Ken Smith C.P.A.",
			"BestBuy" };

	private static final Address[] addresses = new Address[] {
			new Address("123 Main St", "Pheonix", "AZ", "United States", "01378"),
			new Address("500 W. Elm", "Boston", "MA", "United States", "01210"),
			new Address("3232", "San Francisco", "CA", "United States", "11011") };

	private static final EntityManager localInstance = new EntityManager();
	private Mongo mongo;
	private Datastore ds;
	private Morphia morphia;

	private EntityManager() {
		try {
			mongo = new Mongo();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			morphia = new Morphia();
			// morphia.map(PersistentEntity.class);
			morphia.map(Customer.class);
			morphia.map(Invoice.class);
			morphia.map(Address.class);
			ds = morphia.createDatastore(mongo, "aceevo_books");
			ds.delete(ds.createQuery(Customer.class));
			ds.delete(ds.createQuery(Invoice.class));

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		System.out.println("datastore created");
		mockupData();
	}

	public static EntityManager getInstance() {
		return localInstance;
	}

	public void persist(PersistentEntity persistentEntity) {
		ds.save(persistentEntity);
	}

	public Datastore getDs() {
		return ds;
	}

	private void mockupData() {

		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 0; i < names.length; i++) {
			Customer customer = new Customer();
			customer.setName(names[i]);
			customer.setAddress(addresses[i]);
			customers.add(customer);
		}

		/**
		 * Set up invoices
		 */
		for (Customer customer : customers) {
			for (int i = 0; i < 4; i++) {
				Invoice invoice = new Invoice();
				invoice.setCustomerId(customer.getId());
				invoice.setDate(new Date());
				invoice.setRate(40);
				invoice.setDescription("Some work");
				invoice.setInvoiceNumber(new Integer(001));

				if (i % 2 == 0)
					invoice.setInvoiceState(InvoiceState.Outstanding);
				else
					invoice.setInvoiceState(InvoiceState.Paid);

				Random random = new Random();
				invoice.setHours(random.nextInt(100));
				persist(invoice);
			}

			persist(customer);

		}

	}
}

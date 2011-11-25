package com.aceevo.example.aceevobooks.client.customer.view;

import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.client.model.InvoiceProxy;
import com.aceevo.example.aceevobooks.client.view.AceevoBooksView;
import com.aceevo.example.aceevobooks.client.view.BreadCrumbView;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.view.client.HasData;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public interface CustomerView extends AceevoBooksView, BreadCrumbView {

	void setInput(CustomerProxy customerProxy, RequestContext requestContext,
			PlaceController placeController);

	HasData<InvoiceProxy> getInvoiceTable();
	
	

}

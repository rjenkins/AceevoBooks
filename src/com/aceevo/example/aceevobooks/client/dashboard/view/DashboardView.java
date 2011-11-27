package com.aceevo.example.aceevobooks.client.dashboard.view;

import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.client.view.AceevoBooksView;
import com.aceevo.example.aceevobooks.client.view.BreadCrumbView;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.HasData;

public interface DashboardView extends AceevoBooksView, BreadCrumbView {

	HasData<CustomerProxy> getCustomers();

	HasWidgets getInvoiceDetails();

	HasText getOutstandingInvoiceTotal();

	HasText getPaidInvoiceTotal();
}

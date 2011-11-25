package com.aceevo.example.aceevobooks.client.customer;

import java.util.List;

import com.aceevo.example.aceevobooks.client.AceevoBaseAbstractActivity;
import com.aceevo.example.aceevobooks.client.customer.view.CustomerView;
import com.aceevo.example.aceevobooks.client.model.AddressProxy;
import com.aceevo.example.aceevobooks.client.model.BreadCrumb;
import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.client.model.InvoiceProxy;
import com.aceevo.example.aceevobooks.client.place.BreadCrumbChangeEvent;
import com.aceevo.example.aceevobooks.client.requests.CustomerRequest;
import com.aceevo.example.aceevobooks.client.requests.InvoiceRequest;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class ViewCustomerActivity extends AceevoBaseAbstractActivity {

	private CustomerView customerView;
	private AsyncDataProvider<InvoiceProxy> cachedDataProvider;

	public ViewCustomerActivity(ViewCustomer place, CustomerView customerView,
			PlaceController placeController) {
		super(placeController, place);
		this.customerView = customerView;
	}

	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

		panel.setWidget(customerView.asWidget());

		CustomerRequest customerRequest = getRequestFactory(eventBus).customerRequest();

		final AsyncDataProvider<InvoiceProxy> dataProvider = new AsyncDataProvider<InvoiceProxy>() {
			@Override
			protected void onRangeChanged(HasData<InvoiceProxy> display) {

				InvoiceRequest request = getRequestFactory(eventBus).invoiceRequest();
				request.findInvoicesByCustomerId(place.getEntityId(),
						((ViewCustomer) place).getInvoiceState().toString()).fire(
						new Receiver<List<InvoiceProxy>>() {

							@Override
							public void onSuccess(List<InvoiceProxy> response) {
								customerView.getInvoiceTable().setRowData(0, response);
								customerView.getInvoiceTable().setRowCount(response.size());
							}
						});
			}
		};

		Range range = customerView.getInvoiceTable().getVisibleRange();
		customerView.getInvoiceTable().setVisibleRangeAndClearData(range, true);
		dataProvider.addDataDisplay(customerView.getInvoiceTable());
		cachedDataProvider = dataProvider;

		if (place.getEntityId().equals("-1")) {
			CustomerProxy customerProxy = customerRequest.create(CustomerProxy.class);
			AddressProxy addressProxy = customerRequest.create(AddressProxy.class);
			customerProxy.setAddress(addressProxy);
			customerView.setInput(customerProxy, customerRequest, placeController);
			updateBreadCrumb(null, eventBus);
		} else {
			customerRequest.findById(place.getEntityId()).with("address").fire(
					new Receiver<CustomerProxy>() {

						@Override
						public void onSuccess(CustomerProxy response) {
							updateBreadCrumb(response, eventBus);
							customerView.setInput(response, getRequestFactory(eventBus)
									.customerRequest(), placeController);

						}

						@Override
						public void onFailure(ServerFailure error) {
							panel.setWidget(new HTML("unable to find customer"));
						}
					});

		}

	}
	
	private void updateBreadCrumb(CustomerProxy customerProxy, EventBus eventBus) {
		String moduleUrl = Location.getHref();

		if (moduleUrl.indexOf("#") != -1) {
			moduleUrl = moduleUrl.substring(0, Location.getHref().indexOf("#"));
		}

		place.addBreadCrumb(new BreadCrumb("Dashboard", moduleUrl + "#DashboardPlace"));
		place.addBreadCrumb(new BreadCrumb("Customer", null));
		if (customerProxy == null)
			place.addBreadCrumb(new BreadCrumb("New Customer", null));
		else {
			String newUrl = moduleUrl + "#" + "ViewCustomer:" + place.getEntityId();

			place.addBreadCrumb(new BreadCrumb(customerProxy.getName(), newUrl));
		}

		eventBus.fireEvent(new BreadCrumbChangeEvent(customerView));

	}

	@Override
	public void onStop() {
		cachedDataProvider.removeDataDisplay(customerView.getInvoiceTable());
	}
}

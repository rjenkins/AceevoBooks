package com.aceevo.example.aceevobooks.client;

import java.util.List;

import com.aceevo.example.aceevobooks.client.customer.ViewCustomer;
import com.aceevo.example.aceevobooks.client.customer.ViewCustomerActivity;
import com.aceevo.example.aceevobooks.client.customer.event.CustomerAddEvent;
import com.aceevo.example.aceevobooks.client.customer.event.CustomerAddEventHandler;
import com.aceevo.example.aceevobooks.client.customer.event.CustomerDeleteEvent;
import com.aceevo.example.aceevobooks.client.customer.event.CustomerDeleteEventHandler;
import com.aceevo.example.aceevobooks.client.customer.event.InvoiceDeleteEvent;
import com.aceevo.example.aceevobooks.client.customer.event.InvoiceDeleteEventHandler;
import com.aceevo.example.aceevobooks.client.customer.view.CustomerView;
import com.aceevo.example.aceevobooks.client.dashboard.DashboardActivity;
import com.aceevo.example.aceevobooks.client.dashboard.DashboardPlace;
import com.aceevo.example.aceevobooks.client.dashboard.view.DashboardView;
import com.aceevo.example.aceevobooks.client.model.BreadCrumb;
import com.aceevo.example.aceevobooks.client.place.AceevoBooksPlace;
import com.aceevo.example.aceevobooks.client.place.BreadCrumbChangeEvent;
import com.aceevo.example.aceevobooks.client.place.BreadCrumbChangeEventHandler;
import com.aceevo.example.aceevobooks.client.requests.AceevoBooksRequestFactory;
import com.aceevo.example.aceevobooks.client.view.HeaderView;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class AceevoBooksActivityMapper implements ActivityMapper {

	@Inject
	PlaceController placeController;

	@Inject
	EventBus eventBus;

	@Inject
	DashboardView dashboardView;

	@Inject
	CustomerView customerView;

	@Inject
	HeaderView headerView;

	private Boolean init = false;

	private AceevoBooksRequestFactory aceevoBooksRequestFactory;

	@Inject
	public AceevoBooksActivityMapper() {
		super();
	}

	/**
	 * Implement all logic for place changing here
	 */

	@Override
	public Activity getActivity(Place place) {
		if (init == false) {
			initEventsAndHandlers();
			init = true;
		}

		if (place instanceof DashboardPlace)
			return new DashboardActivity((DashboardPlace) place, dashboardView, placeController);
		if (place instanceof ViewCustomer)
			return new ViewCustomerActivity((ViewCustomer) place, customerView, placeController);
		return null;
	}

	/**
	 * Wire all click handlers for singleton views here
	 */
	private void initEventsAndHandlers() {
		eventBus.addHandler(CustomerDeleteEvent.TYPE, new CustomerDeleteEventHandler() {

			@Override
			public void onCustomerDelete(CustomerDeleteEvent customerDeleteEvent) {
				getRequestFactory(eventBus).customerRequest().remove().using(
						customerDeleteEvent.customerProxy).fire(new Receiver<Void>() {

					@Override
					public void onSuccess(Void response) {
						placeController.goTo(new DashboardPlace());
					}
				});
			}
		});
		
		eventBus.addHandler(InvoiceDeleteEvent.TYPE, new InvoiceDeleteEventHandler() {
			
			@Override
			public void onInvoiceDelete(final InvoiceDeleteEvent invoiceDeleteEvent) {
				getRequestFactory(eventBus).invoiceRequest().remove().using(invoiceDeleteEvent.invoiceProxy).fire(new Receiver<Void>() {

					@Override
					public void onSuccess(Void response) {
						ViewCustomer viewCustomer = new ViewCustomer();
						viewCustomer.setEntityId(invoiceDeleteEvent.invoiceProxy.getCustomerId());
						placeController.goTo(viewCustomer);
					}
				
				});
			}
		});

		eventBus.addHandler(CustomerAddEvent.TYPE, new CustomerAddEventHandler() {

			@Override
			public void onCustomerAdd(CustomerAddEvent customerAddEvent) {
				ViewCustomer viewCustomer = new ViewCustomer();
				placeController.goTo(viewCustomer);
			}
		});

		eventBus.addHandler(BreadCrumbChangeEvent.TYPE, new BreadCrumbChangeEventHandler() {

			@Override
			public void onBreadCrumbChange(BreadCrumbChangeEvent breadCrumbChangeEvent) {
				Place place = placeController.getWhere();
				if (place instanceof AceevoBooksPlace) {
					AceevoBooksPlace aceevoBooksPlace = (AceevoBooksPlace) place;
					SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
					List<BreadCrumb> breadCrumbs = aceevoBooksPlace.getBreadCrumbs();

					//safeHtmlBuilder.appendEscaped("You are here: ");
					for (int i = 0; i < breadCrumbs.size(); i++) {
						BreadCrumb breadCrumb = breadCrumbs.get(i);
						String key = breadCrumb.getKey();
						String href = breadCrumb.getHref();
						if (href == null) {
							safeHtmlBuilder.appendEscaped(key);
						} else {
							safeHtmlBuilder.appendHtmlConstant("<a href=\"" + href + "\">" + key
									+ "</a>");
						}

						if (i < breadCrumbs.size() - 1) {
							safeHtmlBuilder.appendHtmlConstant(" > ");
						}
					}
					breadCrumbChangeEvent.getBreadCrumbView().setBreadCrumb(
							new HTML(safeHtmlBuilder.toSafeHtml().asString()));
				}
			}
		});

	}

	public AceevoBooksRequestFactory getRequestFactory(EventBus eventBus) {
		if (aceevoBooksRequestFactory == null) {
			aceevoBooksRequestFactory = GWT.create(AceevoBooksRequestFactory.class);
			aceevoBooksRequestFactory.initialize(eventBus);
		}
		return aceevoBooksRequestFactory;
	}

}
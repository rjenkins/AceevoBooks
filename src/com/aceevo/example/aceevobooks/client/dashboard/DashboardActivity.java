package com.aceevo.example.aceevobooks.client.dashboard;

import java.util.List;

import com.aceevo.example.aceevobooks.client.AceevoBaseAbstractActivity;
import com.aceevo.example.aceevobooks.client.dashboard.view.DashboardView;
import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class DashboardActivity extends AceevoBaseAbstractActivity {

	private DashboardView dashboardView;

	public DashboardActivity(DashboardPlace place, DashboardView dashboardView,
			PlaceController placeController) {
		super(placeController, place);
		this.dashboardView = dashboardView;

	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		panel.setWidget(dashboardView.asWidget());

		final AsyncDataProvider<CustomerProxy> dataProvider = new AsyncDataProvider<CustomerProxy>() {

			@Override
			protected void onRangeChanged(HasData<CustomerProxy> display) {
				getRequestFactory(eventBus).customerRequest().findAllCustomers().with("address")
						.fire(new Receiver<List<CustomerProxy>>() {
							@Override
							public void onSuccess(List<CustomerProxy> response) {
								dashboardView.getCustomers().setRowData(0, response);
							}
						});
			}
		};

		Range range = dashboardView.getCustomers().getVisibleRange();
		dashboardView.getCustomers().setVisibleRangeAndClearData(range, true);
		dataProvider.addDataDisplay(dashboardView.getCustomers());

		getRequestFactory(eventBus).invoiceRequest().findOutstandingInvoiceTotal().fire(
				new Receiver<Double>() {

					@Override
					public void onSuccess(Double response) {
						dashboardView.getOutstandingInvoiceTotal().setText(response.toString());
					}
				});

		getRequestFactory(eventBus).invoiceRequest().findPaidInvoiceTotal().fire(
				new Receiver<Double>() {

					@Override
					public void onSuccess(Double response) {
						dashboardView.getPaidInvoiceTotal().setText(response.toString());
					}
				});
	}

}

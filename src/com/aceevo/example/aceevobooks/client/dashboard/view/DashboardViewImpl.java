package com.aceevo.example.aceevobooks.client.dashboard.view;

import com.aceevo.example.aceevobooks.client.customer.event.CustomerAddEvent;
import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.client.view.AbstractAceevoBooksPage;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import com.sun.corba.se.pept.transport.ContactInfo;

public class DashboardViewImpl extends AbstractAceevoBooksPage implements DashboardView {

	interface DashboardViewUiBinder extends UiBinder<HTMLPanel, DashboardViewImpl> {
	}

	private static DashboardViewUiBinder dashboardViewUiBinder = GWT
			.create(DashboardViewUiBinder.class);

	@UiField
	CellList<CustomerProxy> customerList;

	@UiField
	Button addCustomer;

	@UiField
	VerticalPanel dashboardInvoiceDetail;

	@UiField
	Label outstandingInvoiceTotal;

	@UiField
	Label paidInvoiceTotal;

	@Inject
	EventBus eventBus;

	@Inject
	PlaceController placeController;

	@UiFactory
	CellList<CustomerProxy> makeCustomerList() {
		return new CellList<CustomerProxy>(new CustomerCell(), CustomerCellListResources.INSTANCE);
	}

	public DashboardViewImpl() {
		initWidget(dashboardViewUiBinder.createAndBindUi(this));
		setBreadCrumb(new HTML("Aceevo Books > <a href='#'> Dashboard</a>"));
	}


	@Override
	public HasData<CustomerProxy> getCustomers() {
		return customerList;
	}

	/**
	 * The Cell used to render a {@link ContactInfo}.
	 */
	static class CustomerCell extends AbstractCell<CustomerProxy> {

		/**
		 * The html of the image used for contacts.
		 */

		public CustomerCell() {

		}

		@Override
		public void render(Context context, CustomerProxy value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			sb.appendHtmlConstant("<div class=\"customerDashboardWidget\">");
			sb.appendHtmlConstant("<div class=\"name\">");

			String moduleUrl;
			if (Location.getHref().indexOf("#") == -1) {
				moduleUrl = Location.getHref();
			} else {
				moduleUrl = Location.getHref().substring(0, Location.getHref().indexOf("#"));
			}
			String newUrl = moduleUrl + "#" + "ViewCustomer:" + value.getId() + "!All";

			sb.appendHtmlConstant("<a href=\"" + newUrl + "\">");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</a>");
			sb.appendHtmlConstant("</div>");
			sb.appendHtmlConstant("<div class=\"address\">");
			sb.appendEscaped(value.getAddress().getAddress());
			sb.appendHtmlConstant("<br/>");
			sb.appendEscaped(value.getAddress().getCity() + ", " + value.getAddress().getState()
					+ " " + value.getAddress().getZip());
			sb.appendHtmlConstant("</div>");

			sb.appendHtmlConstant("</div>");
		}
	}

	@Override
	public HasWidgets getInvoiceDetails() {
		return dashboardInvoiceDetail;
	}

	@Override
	public HasText getOutstandingInvoiceTotal() {
		return outstandingInvoiceTotal;
	}

	@Override
	public HasText getPaidInvoiceTotal() {
		return paidInvoiceTotal;
	}

	@UiHandler("addCustomer")
	public void onAddCustomerClick(ClickEvent e) {
		eventBus.fireEvent(new CustomerAddEvent());
	}

}

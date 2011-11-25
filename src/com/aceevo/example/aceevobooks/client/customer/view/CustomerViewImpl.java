package com.aceevo.example.aceevobooks.client.customer.view;

import java.util.Date;

import com.aceevo.example.aceevobooks.client.customer.ViewCustomer;
import com.aceevo.example.aceevobooks.client.customer.event.CustomerDeleteEvent;
import com.aceevo.example.aceevobooks.client.editor.AbstractAceevoEditor.EditorMode;
import com.aceevo.example.aceevobooks.client.invoice.view.InvoiceEditor;
import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.client.model.InvoiceProxy;
import com.aceevo.example.aceevobooks.client.requests.AceevoBooksRequestFactory;
import com.aceevo.example.aceevobooks.client.requests.InvoiceRequest;
import com.aceevo.example.aceevobooks.client.view.HeaderView;
import com.aceevo.example.aceevobooks.shared.InvoiceState;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public class CustomerViewImpl extends Composite implements CustomerView {

	@UiField
	HeaderView headerView;

	@UiField
	CustomerEditor customerEditor;

	@Inject
	EventBus eventBus;

	@Inject
	PlaceController placeController;

	@UiField
	Button editCustomer;

	@UiField
	Button deleteCustomer;

	@UiField
	Button addInvoice;

	@UiField
	CellTable<InvoiceProxy> invoiceTable;

	@UiField
	Anchor paidInvoicesFilter;

	@UiField
	Anchor allInvoicesFilter;

	@UiField
	Anchor outstandingInvoicesFilter;

	@UiField
	InvoiceEditor invoiceEditor;

	private CustomerProxy customerProxy;

	interface CustomerViewUiBinder extends UiBinder<HTMLPanel, CustomerViewImpl> {

	}

	private static CustomerViewUiBinder customerViewUiBinder = GWT
			.create(CustomerViewUiBinder.class);

	public CustomerViewImpl() {
		initWidget(customerViewUiBinder.createAndBindUi(this));

		TextColumn<InvoiceProxy> invoiceStateColumn = new TextColumn<InvoiceProxy>() {
			@Override
			public String getValue(InvoiceProxy invoiceProxy) {
				return invoiceProxy.getInvoiceState().toString();
			}
		};

		TextColumn<InvoiceProxy> descriptionColumn = new TextColumn<InvoiceProxy>() {
			@Override
			public String getValue(InvoiceProxy invoiceProxy) {
				return invoiceProxy.getDescription();
			}
		};

		TextColumn<InvoiceProxy> totalColumn = new TextColumn<InvoiceProxy>() {
			@Override
			public String getValue(InvoiceProxy invoiceProxy) {
				return invoiceProxy.getInvoiceTotal().toString();
			}
		};

		TextColumn<InvoiceProxy> dateColumn = new TextColumn<InvoiceProxy>() {
			@SuppressWarnings("deprecation")
			@Override
			public String getValue(InvoiceProxy invoiceProxy) {
				return DateTimeFormat.getShortDateFormat().format(invoiceProxy.getDate());
			}
		};

		invoiceTable.addColumn(invoiceStateColumn, "State");
		invoiceTable.addColumn(dateColumn, "Date");
		invoiceTable.addColumn(descriptionColumn, "Description");
		invoiceTable.addColumn(totalColumn, "Total");

		invoiceTable.setWidth("100%", true);
		invoiceTable.setColumnWidth(dateColumn, "30%");

		invoiceTable.setEmptyTableWidget(new HTML("No Results"));

		final SingleSelectionModel<InvoiceProxy> selectionModel = new SingleSelectionModel<InvoiceProxy>();
		invoiceTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				InvoiceProxy selected = selectionModel.getSelectedObject();
				if (selected != null) {
					invoiceEditor.setEventBus(eventBus);
					invoiceEditor.setVisible(true);
					invoiceEditor.setInput(selected, getRequestFactory(eventBus).invoiceRequest(),
							EditorMode.VIEW, placeController);
				} else {
					invoiceEditor.setVisible(false);
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setBreadCrumb(HTML html) {
		headerView.setBreadCrumb(html);
	}

	@Override
	public void setInput(CustomerProxy customerProxy, RequestContext requestContext,
			PlaceController placeController) {

		this.customerProxy = customerProxy;
		EditorMode editorMode = EditorMode.VIEW;
		editCustomer.setVisible(true);
		deleteCustomer.setVisible(true);
		addInvoice.setVisible(true);

		if (customerProxy.getId() == null) {
			editorMode = EditorMode.CREATE;
			editCustomer.setVisible(false);
			deleteCustomer.setVisible(false);
			addInvoice.setVisible(false);
			invoiceEditor.setVisible(false);
		}
		customerEditor.setInput(customerProxy, requestContext, editorMode, placeController);
	}

	@UiHandler("editCustomer")
	void handleEditClick(ClickEvent e) {
		customerEditor.setEditMode(EditorMode.EDIT);
	}

	@UiHandler("deleteCustomer")
	void handleDeleteClick(ClickEvent e) {
		eventBus.fireEvent(new CustomerDeleteEvent(customerProxy));
	}

	@UiHandler("paidInvoicesFilter")
	void handlePaidInvoicesFilterClick(ClickEvent e) {
		applyInvoiceFilter(InvoiceState.Paid);
	}

	@UiHandler("outstandingInvoicesFilter")
	void handleOutstandingInvoicesFilterClick(ClickEvent e) {
		applyInvoiceFilter(InvoiceState.Outstanding);
	}

	@UiHandler("allInvoicesFilter")
	void handleAllInvoicesFilterClick(ClickEvent e) {
		applyInvoiceFilter(InvoiceState.All);
	}

	@UiHandler("addInvoice")
	void handleAddInvoice(ClickEvent e) {
		invoiceEditor.setVisible(true);
		InvoiceRequest invoiceRequest = getRequestFactory(eventBus).invoiceRequest();
		InvoiceProxy invoiceProxy = invoiceRequest.create(InvoiceProxy.class);
		invoiceProxy.setCustomerId(customerProxy.getId());
		invoiceProxy.setDate(new Date());
		invoiceProxy.setHours(new Integer(8));
		invoiceProxy.setRate(new Integer(100));
		invoiceProxy.setInvoiceState(InvoiceState.Outstanding);
		invoiceEditor.setInput(invoiceProxy, invoiceRequest, EditorMode.EDIT, placeController);
	}

	@Override
	public HasData<InvoiceProxy> getInvoiceTable() {
		return invoiceTable;
	}

	private void applyInvoiceFilter(InvoiceState invoiceState) {
		ViewCustomer viewCustomer = new ViewCustomer();
		viewCustomer.setEntityId(customerProxy.getId().toString());
		viewCustomer.setInvoiceState(invoiceState);
		placeController.goTo(viewCustomer);
	}

	public AceevoBooksRequestFactory getRequestFactory(EventBus eventBus) {
		AceevoBooksRequestFactory aceevoBooksRequestFactory = GWT
				.create(AceevoBooksRequestFactory.class);
		aceevoBooksRequestFactory.initialize(eventBus);
		return aceevoBooksRequestFactory;
	}

}

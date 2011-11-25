package com.aceevo.example.aceevobooks.client.invoice.view;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.aceevo.example.aceevobooks.client.customer.ViewCustomer;
import com.aceevo.example.aceevobooks.client.customer.event.InvoiceDeleteEvent;
import com.aceevo.example.aceevobooks.client.editor.AbstractAceevoEditor;
import com.aceevo.example.aceevobooks.client.editor.BaseProxyEditorDecorator;
import com.aceevo.example.aceevobooks.client.model.InvoiceProxy;
import com.aceevo.example.aceevobooks.client.requests.InvoiceRequest;
import com.aceevo.example.aceevobooks.shared.InvoiceState;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;

public class InvoiceEditor extends AbstractAceevoEditor<InvoiceProxy> {

	@UiField
	DateBox date;

	@UiField
	IntegerBox rate;

	@UiField
	IntegerBox hours;

	@UiField
	TextBox description;

	@UiField
	Button editInvoice;

	@UiField
	Button deleteInvoice;

	private EventBus eventBus;

	@UiField(provided = true)
	ValueListBox<InvoiceState> invoiceState = new ValueListBox<InvoiceState>(
			new Renderer<InvoiceState>() {

				@Override
				public String render(InvoiceState object) {
					if (object != null)
						return object.name();
					return "";
				}

				@Override
				public void render(InvoiceState object, Appendable appendable) throws IOException {
					render(object);
				}

			});

	@UiField
	HTMLPanel editor;

	@UiField
	BaseProxyEditorDecorator baseProxyEditorDecorator;

	interface InvoiceEditorUiBinder extends UiBinder<HTMLPanel, InvoiceEditor> {
	}

	interface EditorDriver extends RequestFactoryEditorDriver<InvoiceProxy, InvoiceEditor> {
	}

	private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

	InvoiceEditorUiBinder invoiceEditorUiBinder = GWT.create(InvoiceEditorUiBinder.class);

	@SuppressWarnings("unchecked")
	public InvoiceEditor() {
		initWidget(invoiceEditorUiBinder.createAndBindUi(this));
		setEditorDriver((RequestFactoryEditorDriver) editorDriver);
		baseProxyEditorDecorator.setEditor((AbstractAceevoEditor) this);

		invoiceState.setAcceptableValues(Arrays.asList(new InvoiceState[] {
				InvoiceState.Outstanding, InvoiceState.Paid }));

	}

	public void notifyEditMode(EditorMode editorMode) {
		date.setEnabled(editorMode.equals(EditorMode.EDIT));
		rate.setReadOnly(editorMode.equals(EditorMode.VIEW));
		hours.setReadOnly(editorMode.equals(EditorMode.VIEW));
		description.setReadOnly(editorMode.equals(EditorMode.VIEW));
		DOM.setElementPropertyBoolean(invoiceState.getElement(), "disabled", editorMode
				.equals(EditorMode.VIEW));

		baseProxyEditorDecorator.setVisible(!editorMode.equals(EditorMode.VIEW));
	}

	public InstanceRequest<InvoiceProxy, InvoiceProxy> getInstanceRequest() {
		InvoiceRequest requestContext = (InvoiceRequest) editorDriver.flush();
		return requestContext.persist();
	}

	@Override
	public Place getSavePlace(InvoiceProxy customerProxy) {
		ViewCustomer viewCustomer = new ViewCustomer();
		viewCustomer.setEntityId(customerProxy.getCustomerId());
		return viewCustomer;
	}

	@Override
	public Map<String, Widget> getPathToFieldMap() {
		HashMap<String, Widget> map = new HashMap<String, Widget>();
		map.put("date", date);
		map.put("rate", rate);
		map.put("hours", hours);
		map.put("Description", description);
		map.put("InvoiceState", invoiceState);
		return map;
	}

	@UiHandler("deleteInvoice")
	void handleDeleteClick(ClickEvent e) {
		eventBus.fireEvent(new InvoiceDeleteEvent((InvoiceProxy) cachedObject));
	}

	@UiHandler("editInvoice")
	void handleEditClick(ClickEvent e) {
		setEditMode(EditorMode.EDIT);
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}

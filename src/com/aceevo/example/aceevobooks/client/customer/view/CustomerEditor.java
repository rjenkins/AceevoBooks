package com.aceevo.example.aceevobooks.client.customer.view;

import java.util.HashMap;
import java.util.Map;

import com.aceevo.example.aceevobooks.client.customer.ViewCustomer;
import com.aceevo.example.aceevobooks.client.editor.AbstractAceevoEditor;
import com.aceevo.example.aceevobooks.client.editor.BaseProxyEditorDecorator;
import com.aceevo.example.aceevobooks.client.model.CustomerProxy;
import com.aceevo.example.aceevobooks.client.requests.CustomerRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;

public class CustomerEditor extends AbstractAceevoEditor<CustomerProxy> {

	@UiField
	TextBox name;

	@UiField
	AddressEditor addressEditor;

	@UiField
	HTMLPanel editor;

	@UiField
	BaseProxyEditorDecorator baseProxyEditorDecorator;

	interface CustomerEditorUiBinder extends UiBinder<HTMLPanel, CustomerEditor> {
	}

	interface EditorDriver extends RequestFactoryEditorDriver<CustomerProxy, CustomerEditor> {
	}

	private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

	CustomerEditorUiBinder customerEditorUiBinder = GWT.create(CustomerEditorUiBinder.class);

	@SuppressWarnings("unchecked")
	public CustomerEditor() {
		initWidget(customerEditorUiBinder.createAndBindUi(this));
		setEditorDriver((RequestFactoryEditorDriver) editorDriver);
		baseProxyEditorDecorator.setEditor((AbstractAceevoEditor) this);
	}

	@Override
	public void notifyEditMode(
			com.aceevo.example.aceevobooks.client.editor.AbstractAceevoEditor.EditorMode editorMode) {
		baseProxyEditorDecorator.setVisible(!editorMode.equals(EditorMode.VIEW));
		name.setReadOnly(editorMode.equals(EditorMode.VIEW));
		addressEditor.notifyEditMode(editorMode);
	}

	public InstanceRequest<CustomerProxy, CustomerProxy> getInstanceRequest() {
		CustomerRequest requestContext = (CustomerRequest) editorDriver.flush();
		return requestContext.persist();
	}

	@Override
	public Place getSavePlace(CustomerProxy customerProxy) {
		ViewCustomer viewCustomer = new ViewCustomer();
		viewCustomer.setEntityId(customerProxy.getId());
		return viewCustomer;
	}

	@Override
	public void notifyErrorsCleared() {
		addressEditor.clearErrors();
	}
	
	@Override
	public Map<String, Widget> getPathToFieldMap() {
		HashMap<String, Widget> map = new HashMap<String, Widget>();
		map.put("name", name);
		
		for(String key: addressEditor.getPathToFieldMap().keySet()) {
			map.put(key, addressEditor.getPathToFieldMap().get(key));
		}
		
		return map;
	}

}
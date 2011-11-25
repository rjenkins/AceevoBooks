package com.aceevo.example.aceevobooks.client.customer.view;

import java.util.HashMap;
import java.util.Map;

import com.aceevo.example.aceevobooks.client.editor.AbstractAceevoEditor;
import com.aceevo.example.aceevobooks.client.model.AddressProxy;
import com.aceevo.example.aceevobooks.client.view.AceevoBooksView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;

public class AddressEditor extends AbstractAceevoEditor<AddressProxy> implements AceevoBooksView {

	@UiField
	TextBox address;

	@UiField
	TextBox city;

	@UiField
	TextBox state;

	@UiField
	TextBox zip;

	@UiField
	TextBox country;

	interface AddressEditorUiBinder extends UiBinder<HTMLPanel, AddressEditor> {
	}

	private Map<String, Widget> pathToFieldMap;

	AddressEditorUiBinder addressEditorUiBinder = GWT.create(AddressEditorUiBinder.class);

	public AddressEditor() {
		initWidget(addressEditorUiBinder.createAndBindUi(this));
	}

	@Override
	public void notifyEditMode(
			com.aceevo.example.aceevobooks.client.editor.AbstractAceevoEditor.EditorMode editorMode) {
		address.setReadOnly(editorMode.equals(EditorMode.VIEW));
		city.setReadOnly(editorMode.equals(EditorMode.VIEW));
		state.setReadOnly(editorMode.equals(EditorMode.VIEW));
		zip.setReadOnly(editorMode.equals(EditorMode.VIEW));
		country.setReadOnly(editorMode.equals(EditorMode.VIEW));
	}

	@Override
	public Map<String, Widget> getPathToFieldMap() {
		if (pathToFieldMap == null) {
			pathToFieldMap = new HashMap<String, Widget>();
			pathToFieldMap.put("address", address);
			pathToFieldMap.put("city", city);
			pathToFieldMap.put("state", state);
			pathToFieldMap.put("zip", zip);
			pathToFieldMap.put("country", country);
		}
		return pathToFieldMap;

	}

	@Override
	public Place getSavePlace(AddressProxy addressProxy) throws Exception {
		throw new UnsupportedOperationException("getSavePlace not supported for AddressEditor");
	}

	@Override
	public InstanceRequest<AddressProxy, AddressProxy> getInstanceRequest() throws Exception {
		throw new UnsupportedOperationException(
				"getInstanceRequest not supported for AddressEditor");
	}

}

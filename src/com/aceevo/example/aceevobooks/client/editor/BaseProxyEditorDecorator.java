package com.aceevo.example.aceevobooks.client.editor;

import com.aceevo.example.aceevobooks.client.editor.AbstractAceevoEditor.EditorMode;
import com.aceevo.example.aceevobooks.client.view.AceevoBooksView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.requestfactory.shared.BaseProxy;

public class BaseProxyEditorDecorator extends Composite implements AceevoBooksView {

	interface BaseProxyEditorUiBinder extends UiBinder<HTMLPanel, BaseProxyEditorDecorator> {
	}

	BaseProxyEditorUiBinder baseProxyEditorUiBinder = GWT.create(BaseProxyEditorUiBinder.class);

	@UiField
	HTMLPanel baseProxyEditorActionPanel;

	private AbstractAceevoEditor<BaseProxy> editor;

	public BaseProxyEditorDecorator() {
		initWidget(baseProxyEditorUiBinder.createAndBindUi(this));
	}

	public void setEditor(AbstractAceevoEditor<BaseProxy> editor) {
		this.editor = editor;
	}

	@UiHandler("cancel")
	void handleCancelClick(ClickEvent e) {
		editor.setEditMode(EditorMode.VIEW);
		editor.clearErrors();
	}

	@UiHandler("save")
	void handleSaveClick(ClickEvent e) {
		try {
			editor.save();
		} catch (Exception ex) {
			Window.alert("Error saving editor: " + ex.getMessage());
		}
	}

}

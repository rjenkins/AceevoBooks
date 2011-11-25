package com.aceevo.example.aceevobooks.client.editor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aceevo.example.aceevobooks.client.model.AbstractEntityProxy;
import com.aceevo.example.aceevobooks.client.view.AceevoBooksView;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Violation;

public abstract class AbstractAceevoEditor<T extends BaseProxy> extends Composite implements
		Editor<T>, AceevoBooksView, HasEditorErrors<T> {

	public enum EditorMode {
		VIEW, EDIT, CREATE
	};

	protected RequestFactoryEditorDriver<T, Editor<T>> editorDriver;
	protected T cachedObject;
	protected RequestContext cachedRequestContext;

	protected PlaceController placeController;

	protected Map<String, Widget> pathToFieldMap;

	protected Receiver<T> updateReceiver = new Receiver<T>() {
		@Override
		public void onSuccess(T response) {
			clearErrors();
			try {
				placeController.goTo(getSavePlace(response));
			} catch (Exception ex) {
				Window.alert("Error saving Editor: " + ex.getMessage());
			}
		}

		public void onViolation(Set<Violation> errors) {
			clearErrors();
			editorDriver.setViolations(errors);
			for (Violation violation : errors)
				setWidgetError(pathToFieldMap.get(violation.getPath()));
		}
	};

	public AbstractAceevoEditor() {

	}

	public abstract Map<String, Widget> getPathToFieldMap();

	public abstract InstanceRequest<T, T> getInstanceRequest() throws Exception;

	public abstract Place getSavePlace(T t) throws Exception;

	public void setEditorDriver(RequestFactoryEditorDriver<T, Editor<T>> editorDriver) {
		this.editorDriver = editorDriver;
	}

	public void setInput(T entityProxy, RequestContext requestContext, EditorMode editorMode,
			PlaceController placeController) {

		this.cachedObject = entityProxy;
		this.placeController = placeController;
		this.cachedRequestContext = requestContext;

		editorDriver.initialize(this);
		setEditMode(editorMode);
		setVisible(true);

		clearErrors();
	}

	public void setEditMode(EditorMode editorMode) {
		if (editorMode == EditorMode.EDIT || editorMode == EditorMode.CREATE) {
			editorDriver.edit(this.cachedObject, this.cachedRequestContext);
			removeStyleName("readOnly");
		} else if (editorMode == EditorMode.VIEW) {
			editorDriver.display(this.cachedObject);
			addStyleName("readOnly");
		}

		notifyEditMode(editorMode);
	}

	public void notifyEditMode(EditorMode editorMode) {
		// no op
	}

	public void clearErrors() {
		if (pathToFieldMap == null)
			pathToFieldMap = getPathToFieldMap();

		for (Widget w : pathToFieldMap.values())
			w.removeStyleName("errorWidget");
		notifyErrorsCleared();
	}

	public void save() throws Exception {
		if (cachedObject instanceof AbstractEntityProxy) {
			editorDriver.flush();
			getInstanceRequest().using(cachedObject).fire(updateReceiver);
		}
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		if (pathToFieldMap == null)
			pathToFieldMap = getPathToFieldMap();

		for (EditorError error : errors)
			setWidgetError(pathToFieldMap.get(error.getPath()));
	}

	public void setWidgetError(Widget w) {
		if (w != null)
			w.addStyleName("errorWidget");
	}

	public void notifyErrorsCleared() {
	}

}

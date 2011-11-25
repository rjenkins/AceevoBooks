package com.aceevo.example.aceevobooks.client;

import com.aceevo.example.aceevobooks.client.place.AceevoBooksPlace;
import com.aceevo.example.aceevobooks.client.requests.AceevoBooksRequestFactory;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class AceevoBaseAbstractActivity extends AbstractActivity {

	private AceevoBooksRequestFactory aceevoBooksRequestFactory;
	protected PlaceController placeController;
	protected AceevoBooksPlace place;

	public AceevoBaseAbstractActivity(PlaceController placeController, AceevoBooksPlace place) {
		this.placeController = placeController;
		this.place = place;
	}

	@Override
	public abstract void start(AcceptsOneWidget panel, EventBus eventBus);

	public AceevoBooksRequestFactory getRequestFactory(EventBus eventBus) {
		if (aceevoBooksRequestFactory == null) {
			aceevoBooksRequestFactory = GWT.create(AceevoBooksRequestFactory.class);
			aceevoBooksRequestFactory.initialize(eventBus);
		}
		return aceevoBooksRequestFactory;
	}

	public void setRequestFactory(AceevoBooksRequestFactory aceevoBooksRequestFactory) {
		this.aceevoBooksRequestFactory = aceevoBooksRequestFactory;
	}

	/**
	 * Navigate to a new Place in the browser
	 */
	public void goTo(Place place) {
		placeController.goTo(place);
	}

	/**
	 * Ask user before stopping this activity
	 */
	@Override
	public String mayStop() {
		return null;
	}

}

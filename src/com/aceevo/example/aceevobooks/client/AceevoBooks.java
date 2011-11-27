package com.aceevo.example.aceevobooks.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AceevoBooks implements EntryPoint {

	private final AceevoBooksGinjector injector = GWT.create(AceevoBooksGinjector.class);
	private SimplePanel appWidget = new SimplePanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		ActivityMapper activityMapper = injector.getActivityMapper();
		ActivityManager activityManager = new ActivityManager(activityMapper, injector
				.getEventBus());
		activityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AceevoBooksHistoryMapper historyMapper = GWT.create(AceevoBooksHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

		RootPanel.get().add(appWidget);

		historyHandler.register(injector.getPlaceController(), injector.getEventBus(), injector
				.getDashboardPlace());
		historyHandler.handleCurrentHistory();

	}

}

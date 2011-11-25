package com.aceevo.example.aceevobooks.client;

import com.aceevo.example.aceevobooks.client.customer.ViewCustomer;
import com.aceevo.example.aceevobooks.client.customer.view.CustomerEditor;
import com.aceevo.example.aceevobooks.client.customer.view.CustomerView;
import com.aceevo.example.aceevobooks.client.customer.view.CustomerViewImpl;
import com.aceevo.example.aceevobooks.client.dashboard.DashboardPlace;
import com.aceevo.example.aceevobooks.client.dashboard.view.DashboardView;
import com.aceevo.example.aceevobooks.client.dashboard.view.DashboardViewImpl;
import com.aceevo.example.aceevobooks.client.requests.InjectablePlaceController;
import com.aceevo.example.aceevobooks.client.view.HeaderView;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Singleton;

public class AceevoBooksGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceController.class).to(InjectablePlaceController.class).in(Singleton.class);
		bind(ActivityMapper.class).to(AceevoBooksActivityMapper.class).in(Singleton.class);
		bind(DashboardView.class).to(DashboardViewImpl.class).in(Singleton.class);
		bind(CustomerView.class).to(CustomerViewImpl.class).in(Singleton.class);
		bind(CustomerEditor.class).in(Singleton.class);
		bind(HeaderView.class).in(Singleton.class);
		bind(DashboardPlace.class);
		bind(ViewCustomer.class);

	}

}

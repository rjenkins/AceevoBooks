package com.aceevo.example.aceevobooks.client;

import com.aceevo.example.aceevobooks.client.customer.ViewCustomer;
import com.aceevo.example.aceevobooks.client.customer.view.CustomerEditor;
import com.aceevo.example.aceevobooks.client.customer.view.CustomerView;
import com.aceevo.example.aceevobooks.client.dashboard.DashboardPlace;
import com.aceevo.example.aceevobooks.client.dashboard.view.DashboardView;
import com.aceevo.example.aceevobooks.client.view.HeaderView;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;

@GinModules(AceevoBooksGinModule.class)
public interface AceevoBooksGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceController getPlaceController();

	DashboardPlace getDashboardPlace();

	ViewCustomer getViewCustomer();

	ActivityMapper getActivityMapper();

	DashboardView getDashboardView();

	CustomerView getCustomerView();

	HeaderView getHeaderView();

	CustomerEditor getCustomerEditor();

}

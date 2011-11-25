package com.aceevo.example.aceevobooks.client;

import com.aceevo.example.aceevobooks.client.customer.ViewCustomer;
import com.aceevo.example.aceevobooks.client.dashboard.DashboardPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers( { DashboardPlace.Tokenizer.class, ViewCustomer.Tokenizer.class })
public interface AceevoBooksHistoryMapper extends PlaceHistoryMapper {
}

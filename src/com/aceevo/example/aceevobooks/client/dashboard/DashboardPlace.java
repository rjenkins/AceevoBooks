package com.aceevo.example.aceevobooks.client.dashboard;

import com.aceevo.example.aceevobooks.client.place.AceevoBooksPlace;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DashboardPlace extends AceevoBooksPlace {

	public static class Tokenizer implements PlaceTokenizer<DashboardPlace> {

		@Override
		public DashboardPlace getPlace(String token) {
			DashboardPlace dashboardPlace = new DashboardPlace();
			return dashboardPlace;
		}

		@Override
		public String getToken(DashboardPlace place) {
			return "";
		}
	}
}
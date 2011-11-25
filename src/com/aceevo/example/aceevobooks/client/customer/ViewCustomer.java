package com.aceevo.example.aceevobooks.client.customer;

import com.aceevo.example.aceevobooks.client.place.AceevoBooksPlace;
import com.aceevo.example.aceevobooks.shared.InvoiceState;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ViewCustomer extends AceevoBooksPlace {

	private InvoiceState invoiceState = InvoiceState.All;

	public ViewCustomer() {

	}

	public void setInvoiceState(InvoiceState invoiceState) {
		this.invoiceState = invoiceState;
	}

	public InvoiceState getInvoiceState() {
		return invoiceState;
	}

	public static class Tokenizer implements PlaceTokenizer<ViewCustomer> {

		@Override
		public ViewCustomer getPlace(String token) {
			String[] keys = token.split("!");
			ViewCustomer viewCustomerPlace = new ViewCustomer();
			viewCustomerPlace.setEntityId(keys[0]);
			if (keys.length == 2)
				viewCustomerPlace.setInvoiceState(InvoiceState.valueOf(keys[1]));
			return viewCustomerPlace;
		}

		@Override
		public String getToken(ViewCustomer place) {
			return place.getEntityId() + "!" + place.getInvoiceState().name();
		}
	}

}

package com.aceevo.example.aceevobooks.client.place;

import java.util.ArrayList;
import java.util.List;

import com.aceevo.example.aceevobooks.client.model.BreadCrumb;
import com.google.gwt.place.shared.Place;

public class AceevoBooksPlace extends Place {

	private String entityId = "-1";
	private List<BreadCrumb> breadCrumbs = new ArrayList<BreadCrumb>();

	public AceevoBooksPlace() {
		// TODO Auto-generated constructor stub
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityId() {
		return entityId;
	}
	
	public List<BreadCrumb> getBreadCrumbs() {
		return breadCrumbs;
	}
	
	public void addBreadCrumb(BreadCrumb breadCrumb) {
		breadCrumbs.add(breadCrumb);
	}
}

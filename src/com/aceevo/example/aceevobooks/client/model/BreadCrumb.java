package com.aceevo.example.aceevobooks.client.model;

public class BreadCrumb {

	private String key;
	private String href;

	public BreadCrumb(String key, String href) {
		this.key = key;
		this.href = href;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}

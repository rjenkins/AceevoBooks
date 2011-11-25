package com.aceevo.example.aceevobooks.server.model;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Version;

public class PersistentEntity {

	@Id
	private String id;
	@Version
	private Long version = null;

	public PersistentEntity() {
		id = new ObjectId().toString();
	}

	public String getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}

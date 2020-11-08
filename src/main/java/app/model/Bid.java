package app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Bid {

	private String id;
	@JsonProperty("ts")
	private Timestamp timestamp;
	@JsonProperty("ty")
	private String name;
	@JsonProperty("pl")
	private String payload;

	public Bid() {

	}

	public Bid(String id, Timestamp timestamp, String name, String payload) {

		this.id = id;
		this.timestamp = timestamp;
		this.name = name;
		this.payload = payload;
	}

	public String getId() {

		return id;
	}

	public Timestamp getTimestamp() {

		return timestamp;
	}

	public String getName() {

		return name;
	}

	public String getPayload() {

		return payload;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setTimestamp(Timestamp timestamp) {

		this.timestamp = timestamp;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setPayload(String payload) {

		this.payload = payload;
	}
}

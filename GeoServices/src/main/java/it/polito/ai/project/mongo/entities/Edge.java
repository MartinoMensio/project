package it.polito.ai.project.mongo.entities;

import java.util.List;

import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "edges")
public class Edge {
	
	@Id
	private Key id;
	@ManyToOne
	private String idSource;
	private String idDestination;
	private boolean mode;
	private int cost;
	private String lineId;
	
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	private List<String> stopsId;

	public List<String> getStopsId() {
		return stopsId;
	}

	public void setStopsId(List<String> stopsId) {
		this.stopsId = stopsId;
	}

	public String getIdSource() {
		return idSource;
	}

	public void setIdSource(String idSource) {
		this.idSource = idSource;
	}

	public String getIdDestination() {
		return idDestination;
	}

	public void setIdDestination(String idDestination) {
		this.idDestination = idDestination;
	}

	public boolean isMode() {
		return mode;
	}

	public void setMode(boolean mode) {
		this.mode = mode;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}

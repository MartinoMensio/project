package it.polito.ai.project.postgis.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class BusStop {
	
	@Id
	private String id;
	private String name;
	@Column(name="lat")
	private double latitude;
	@Column(name="lng")
	private double longitude;
	@ManyToMany(mappedBy="stops")
	private List<BusLine> lines = new ArrayList<BusLine>();
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public List<BusLine> getLines() {
		return lines;
	}

}


package it.polito.ai.project.postgis.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class BusLine {

	@Id
	private String line;
	private String description;
	@ManyToMany
	@JoinTable(name = "BusLineStop",
		joinColumns = { @JoinColumn(name = "lineId") },
		inverseJoinColumns = {@JoinColumn(name = "stopId")
	})
	private List<BusStop> stops = new ArrayList<BusStop>();

	public String getLine() {
		return line;
	}

	public String getDescription() {
		return description;
	}
	
	public List<BusStop> getStops() {
		return stops;
	}

}

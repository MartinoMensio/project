package it.polito.ai.project.repo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="travelDocuments")
public class TravelDocument {
	@Id
	private Long id;
	private String name;
	private float cost;
	private String description;
	
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public float getCost() {
		return cost;
	}

	public String getDescription() {
		return description;
	}
}

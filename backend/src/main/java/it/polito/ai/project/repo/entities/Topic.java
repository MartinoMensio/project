package it.polito.ai.project.repo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "topics")
public class Topic {

	@Id
	private Long id;
	private String value;
	private String description;

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
	
	public String getDescription() {
		return description;
	}
}

package it.polito.ai.project.repo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="educationLevels")
public class EducationLevel {
	@Id
	private Long id;
	private String value;
	
	
	public Long getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
}

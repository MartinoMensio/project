package it.polito.ai.project.repo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="fuels")
public class Fuel {
	@Id
	private long id;
	@Column
	private String value;
	
	
	public long getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
}

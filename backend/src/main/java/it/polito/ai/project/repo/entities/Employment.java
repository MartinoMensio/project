package it.polito.ai.project.repo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="employments")
public class Employment {
	@Id
	private Long id;
	@Column
	private String value;
	
	
	public Long getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
}

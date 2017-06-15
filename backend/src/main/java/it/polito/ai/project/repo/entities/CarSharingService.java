package it.polito.ai.project.repo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="carSharingServices")
public class CarSharingService {
	@Id
	private Long id;
	@Column
	private String name;
	
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}

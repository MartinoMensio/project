package it.polito.ai.project.repo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "topics")
public class Topic {

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

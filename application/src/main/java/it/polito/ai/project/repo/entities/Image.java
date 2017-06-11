package it.polito.ai.project.repo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "images")
public class Image {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private byte[] value;

	public Image() {
	}

	public Image(byte[] value) {
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
	
	public String getUrl() {
		if (id != null) {
			return "/images/" + id;
		} else {
			return null;
		}
		
	}

}

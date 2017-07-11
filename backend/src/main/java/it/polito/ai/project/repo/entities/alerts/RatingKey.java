package it.polito.ai.project.repo.entities.alerts;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import it.polito.ai.project.repo.entities.User;

@Embeddable
public class RatingKey implements Serializable {
	private static final long serialVersionUID = -4525787133880540276L;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="alert_id")
	private Alert alert;
}

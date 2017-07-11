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
	
	public RatingKey() {
	}
	
	public RatingKey(User user, Alert alert) {
		this.user = user;
		this.alert = alert;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}
}

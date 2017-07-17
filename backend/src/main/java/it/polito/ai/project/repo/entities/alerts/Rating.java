package it.polito.ai.project.repo.entities.alerts;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import it.polito.ai.project.repo.entities.User;

@Entity(name="ratings")
public class Rating {
	@EmbeddedId
	@GeneratedValue
	private RatingKey id;
	private int vote;
	
	
	public Rating() {
	}
	
	public Rating(User user, Alert alert, int vote) {
		this.id = new RatingKey(user, alert);
		this.vote = vote;
	}
	
	public int getVote() {
		return vote;
	}
	
	public void setVote(int vote) {
		this.vote = vote;
	}
	
	public Alert getAlert() {
		return id.getAlert();
	}
	
	public void setAlert(Alert alert) {
		this.id.setAlert(alert);
	}
	
	public User getUser() {
		return id.getUser();
	}
	
	public void setUser(User user) {
		this.id.setUser(user);
	}
}

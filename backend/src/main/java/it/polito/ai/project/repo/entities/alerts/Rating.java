package it.polito.ai.project.repo.entities.alerts;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import it.polito.ai.project.repo.entities.User;

@Entity(name="ratings")
public class Rating {
	@EmbeddedId
	private RatingKey id;
	
	/*@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")*/
	@Transient
	private User user;

	/*@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="alert_id")*/
	@Transient
	private Alert alert;
	private int vote;
	
	
	public Rating() {
	}
	
	public Rating(User user, Alert alert, int vote) {
		this.user = user;
		this.alert = alert;
		this.vote = vote;
	}
	
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public Alert getAlert() {
		return alert;
	}
	public void setAlert(Alert alert) {
		this.alert = alert;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}

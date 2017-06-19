package it.polito.ai.project.repo.entities.alerts;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import it.polito.ai.project.repo.entities.User;

@Entity(name="alerts")
public class Alert {
	
	@Id
	private long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="alert_type_id")
	private AlertType alertType;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	private float rating;
	private String address;
	private double lat;
	private double lng;
	private Date activationDate;
	private LocalTime lastViewTime; // TODO LocalTime è appropriato?
	private String comment;

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public LocalTime getLastViewTime() {
		return lastViewTime;
	}

	public void setLastViewTime(LocalTime lastViewTime) {
		this.lastViewTime = lastViewTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

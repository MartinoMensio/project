package it.polito.ai.project.repo.entities.alerts;

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
	private Date lastViewTime;
	private Boolean active;
	private String comment;
	
	
	public Alert() {
	}
	
	public Alert(AlertType alertType, User user, String address, double lat, double lng, String comment) {
		this.alertType = alertType;
		this.user = user;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.comment = comment;
		
		this.rating = 0f;
		this.activationDate = new Date();
		this.lastViewTime = new Date();
		this.active = true;
	}

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
	}// TODO LocalTime è appropriato?

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getLastViewTime() {
		return lastViewTime;
	}

	public void setLastViewTime(Date lastViewTime) {
		this.lastViewTime = lastViewTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}

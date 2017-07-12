package it.polito.ai.project.repo.entities.alerts;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import it.polito.ai.project.repo.entities.User;

@Entity(name="alerts")
public class Alert {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="alert_type_id")
	private AlertType alertType;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	private String hashtag;
	private float rating;
	private String address;
	private double lat;
	private double lng;
	private Date activationDate;
	private Date lastViewTime;
	private Boolean active;
	private String comment;
	
	@OneToMany(mappedBy="id.alert")
	private List<Rating> ratings;
	
	
	public Alert() {
	}
	
	public Alert(AlertType alertType, String hashtag, User user, String address, double lat, double lng, String comment) {
		this.alertType = alertType;
		this.hashtag = hashtag;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
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

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
}

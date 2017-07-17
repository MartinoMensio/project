package it.polito.ai.project.rest.controllers.reqbody;

import javax.validation.constraints.NotNull;


public class NewAlertBodyRequest {
	@NotNull
	private Long alertTypeId;
	@NotNull
	private String hashtag;
	@NotNull
	private String address;
	@NotNull
	private Double lat;
	@NotNull
	private Double lng;
	private String comment;
	
	
	public Long getAlertTypeId() {
		return alertTypeId;
	}
	public void setAlertTypeId(Long alertTypeId) {
		this.alertTypeId = alertTypeId;
	}
	public String getHashtag() {
		return hashtag;
	}
	public void setHashTag(String hashtag) {
		this.hashtag = hashtag;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}

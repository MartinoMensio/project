package it.polito.ai.project.repo.entities;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import it.polito.ai.project.repo.entities.alerts.Alert;

@Entity(name = "messages")
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;

	private String text;

	private Calendar sendingTime;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
	@OneToOne(cascade = CascadeType.ALL, optional=true)
	@JoinColumn(name="image_id")
	private Image image;
	
	@OneToOne(optional = true)
	@JoinColumn(name="alert_id")
	private Alert alert;
	
	public Image getImage() {
		return image;
	}

	public Message() {
	}
	
	public Message(User sender, String text, Calendar sendingTime, Topic topic, Image image, Alert alert) {
		this.sender = sender;
		this.text = text;
		this.sendingTime = sendingTime;
		this.topic = topic;
		this.image = image;
		this.alert = alert;
	}

	public Long getId() {
		return id;
	}

	public User getSender() {
		return sender;
	}

	public String getText() {
		return text;
	}

	public Calendar getSendingTime() {
		return sendingTime;
	}

	public Topic getTopic() {
		return topic;
	}

	public Alert getAlert() {
		return alert;
	}

}

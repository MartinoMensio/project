package it.polito.ai.project.business.services.chat;

import java.util.Calendar;

import javax.persistence.Id;

import it.polito.ai.project.repo.entities.Image;
import it.polito.ai.project.repo.entities.Message;
import it.polito.ai.project.repo.entities.UserProfile;
import it.polito.ai.project.repo.entities.alerts.Alert;

public class ChatMessageImpl implements ChatMessage {
	@Id
	private Long id;
	private Calendar sendingTime;
	private long userId;
	private String userNickname;
	private String userImageUrl;
	private String text;
	private String imageUrl;
	private Alert alert;
	
	
	public ChatMessageImpl(Message message) {
		this.id = message.getId();
		this.sendingTime = message.getSendingTime();
		this.userId = message.getSender().getId();
		this.userNickname = message.getSender().getNickname();
		this.text = message.getText();
		
		UserProfile userProfile = message.getSender().getProfile();
		if (userProfile != null) {
			Image profileImage = userProfile.getImage();
			if (profileImage != null) {
				this.userImageUrl = profileImage.getUrl();
			}
		}
		Image messageImage = message.getImage();
		if (messageImage != null) {
			// build the link to the image
			this.imageUrl = message.getImage().getUrl();
		}
		this.alert = message.getAlert();
	}

	@Override
	public Calendar getSendingTime() {
		return sendingTime;
	}
	
	@Override
	public long getUserId() {
		return userId;
	}

	@Override
	public String getUserNickname() {
		return userNickname;
	}

	@Override
	public String getUserImageUrl() {
		return userImageUrl;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public Alert getAlert() {
		return alert;
	}
}

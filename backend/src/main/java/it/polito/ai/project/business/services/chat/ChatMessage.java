package it.polito.ai.project.business.services.chat;

import java.util.Calendar;

import org.springframework.hateoas.Identifiable;

import it.polito.ai.project.repo.entities.alerts.Alert;

public interface ChatMessage extends Identifiable<Long>{

	/**
	 * Return the message id
	 * 
	 * @return
	 */
	public Long getId();
	
	/**
	 * Return the calendar (time+date) on which the message has been sent.
	 * 
	 * @return a Calendar object
	 */
	public Calendar getSendingTime();

	/**
	 * Return the user id of the sender. Can be useful because the nickname
	 * could be the same for different users.
	 * 
	 * @return
	 */
	public long getUserId();

	/**
	 * Return the nickname of the user that has sent the message.
	 * 
	 * @return a String object
	 */
	public String getUserNickname();

	/**
	 * Return the URL to the user image user's profile message if exists, otherwise null
	 * 
	 * @return 
	 */
	public String getUserImageUrl();

	/**
	 * If the message contains text gives the text, otherwise null
	 * 
	 * @return a String object
	 */
	public String getText();

	/**
	 * If the message contains an image gives a URL to the image, otherwise null
	 * 
	 * @return
	 */
	public String getImageUrl();
	
	/**
	 * If the message contains a linked alert, otherwise null
	 * @return
	 */
	public Alert getAlert();
}

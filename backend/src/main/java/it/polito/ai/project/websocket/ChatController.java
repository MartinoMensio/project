package it.polito.ai.project.websocket;

import java.util.Base64;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.business.services.chat.ChatMessage;
import it.polito.ai.project.business.services.chat.ChatMessageImpl;
import it.polito.ai.project.business.services.chat.ChatService;
import it.polito.ai.project.repo.entities.Image;
import it.polito.ai.project.repo.entities.Message;
import it.polito.ai.project.repo.entities.Topic;
import it.polito.ai.project.repo.entities.User;

@Controller
public class ChatController {

	// interface towards the broker
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private CurrentUserService currentUserService;

	@Autowired
	ChatService chatService;

	@MessageMapping("/{topicId}")
	public void handleMessage(SimpMessageHeaderAccessor hs, @DestinationVariable String topicId, WebSocketMessage webSocketMessage) throws Exception {
		User sender = currentUserService.getCurrentUser();
		if (sender != null) {
			Topic topic = chatService.getTopicById(topicId);
			if (topic != null) {
				Image image = null;
				if (webSocketMessage.getImage() != null && !webSocketMessage.getImage().equals("")) {
					String imageDataWithPrefix = webSocketMessage.getImage();
					// check MIME type
					if (imageDataWithPrefix.startsWith("data:image/")) {
						// get the string after "data:image/png;base64," prefix
						String imageData = imageDataWithPrefix.substring(imageDataWithPrefix.indexOf(",")+1);
						byte[] imageBytes = Base64.getDecoder().decode(imageData);
						image = new Image(imageBytes);
					}
				}
				
				// create the message entity
				Message messageEntity = new Message(sender, webSocketMessage.getContent(), Calendar.getInstance(), topic, image);
				// save the message in the service (that saves in the repository
				chatService.saveMessage(messageEntity);
				// create an object to be shown to the client
				ChatMessage chatMessage = new ChatMessageImpl(messageEntity);
				// send the message to the topic
				messagingTemplate.convertAndSend("/topic/" + topic.getValue(), chatMessage);
			} else {
				// inexistent topic
				System.err.println("Inexistent topic " + topicId);
			}
		} else {
			// should never be there because the endpoint requires
			// authentication
			System.err.println("Message from unknown user!!");
		}
	}

}

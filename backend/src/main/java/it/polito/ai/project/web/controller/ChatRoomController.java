package it.polito.ai.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.polito.ai.project.business.services.chat.ChatService;
import it.polito.ai.project.repo.entities.Topic;

@Controller
@RequestMapping({ "/room" })
public class ChatRoomController extends AbstractPageWithHeaderController{

	@Autowired
	ChatService chatService;

	@RequestMapping(value = "/{topicId}", method = RequestMethod.GET)
	public String showLogin(ModelMap model, @PathVariable String topicId, CsrfToken csrfToken) {
		super.attachData(model);
		Topic topic = chatService.getTopicByName(topicId);
		if (topic != null) {
			model.put("topic", topic);
			model.put("csrfToken", csrfToken);
			model.put("lastMessages", chatService.getLastMessages(topic, 10));
			return "room";
		} else {
			return "redirect:/home";
		}
	}
}

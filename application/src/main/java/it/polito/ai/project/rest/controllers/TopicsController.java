package it.polito.ai.project.rest.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.chat.ChatService;
import it.polito.ai.project.repo.entities.Topic;

@RestController
public class TopicsController {

	@Autowired
	private ChatService chatService;

	@RequestMapping(value = "/rest/topics", method = RequestMethod.GET, headers = "Accept=application/json")
	public Set<Topic> topicsReturn() {
		return chatService.getTopics();
	}
}
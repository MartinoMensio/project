package it.polito.ai.project.rest.controllers.open;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.chat.ChatService;
import it.polito.ai.project.repo.entities.Topic;

/**
 * 
 * This class provides an open endpoint for topics as required in assignment 4
 *
 */
@RestController
public class OpenTopicsController {

	@Autowired
	private ChatService chatService;

	@RequestMapping(value = "/api/open/topics", method = RequestMethod.GET, headers = "Accept=application/json")
	public Set<Topic> getTopics() {
		return chatService.getTopics();
	}
	
	@GetMapping("/api/open/topics/{id}")
	public Topic getTopicById(@PathVariable(value = "id") Long id) {
		return chatService.getTopicById(id);
	}
}
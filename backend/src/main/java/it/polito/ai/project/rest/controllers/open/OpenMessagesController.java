package it.polito.ai.project.rest.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.chat.ChatService;
import it.polito.ai.project.repo.entities.Topic;
import it.polito.ai.project.rest.resources.ChatMessageResource;
import it.polito.ai.project.web.exceptions.NotFoundException;

/**
 * 
 * This class provides an open endpoint for messages as required in assignment 4
 *
 */
@RestController
public class OpenMessagesController {

	@Autowired
	private ChatService chatService;

	@RequestMapping(value = "/api/open/messages/{topicId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public PagedResources<Resource<ChatMessageResource>> getOpenMessages(@PathVariable("topicId") Long topicId,
			Pageable pageable, PagedResourcesAssembler<ChatMessageResource> assembler) {
		// get the topic by id
		Topic topic = chatService.getTopicById(topicId);
		if (topic != null) {
			Page<ChatMessageResource> chatMessageResources = chatService
					// get the ChatMessages
					.findByTopic(topic, pageable)
					// and anonymize them
					.map(m -> new ChatMessageResource(m));
			// provide result HATEOAS
			return assembler.toResource(chatMessageResources);
		} else {
			throw new NotFoundException();
		}
	}
}
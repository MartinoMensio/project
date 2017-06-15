package it.polito.ai.project.rest.controllers;

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

@RestController
public class MessagesController {

	@Autowired
	private ChatService chatService;

	@RequestMapping(value = "/rest/messages/{topicName}", method = RequestMethod.GET, headers = "Accept=application/json")
	public PagedResources<Resource<ChatMessageResource>> messagesReturn(@PathVariable("topicName") String topicName,
			Pageable pageable, PagedResourcesAssembler<ChatMessageResource> assembler) {
		Topic topic = chatService.getTopicByName(topicName);
		if (topic != null) {
			Page<ChatMessageResource> chatMessageResources = chatService.findByTopic(topic, pageable).map(m -> new ChatMessageResource(m));
			return assembler.toResource(chatMessageResources);
		} else {
			throw new NotFoundException();
		}
	}
}
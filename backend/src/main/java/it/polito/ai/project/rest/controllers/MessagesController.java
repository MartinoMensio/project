package it.polito.ai.project.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.chat.ChatMessage;
import it.polito.ai.project.business.services.chat.ChatService;
import it.polito.ai.project.repo.entities.Topic;
import it.polito.ai.project.web.exceptions.NotFoundException;

@RestController
public class MessagesController {
	@Autowired
	private ChatService chatService;

	/**
	 * this endpoint exposes the messages of a certain topic to authenticated
	 * users
	 * 
	 * @param topicId
	 *            the topic id
	 * @param pageable
	 *            this defaults to results of page size = 10 sorted by sending
	 *            time descending, so that requests to this endpoint without
	 *            paging request will get the last 10 messages of this topic
	 * @param assembler
	 *            this will wrap the ChatMessages into a HATEOAS object
	 * @return
	 */
	@RequestMapping(value = "/api/messages/{topicId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public PagedResources<Resource<ChatMessage>> getMessages(@PathVariable("topicId") String topicId,
			@PageableDefault(sort = { "sendingTime" }, direction = Direction.DESC, size = 10) Pageable pageable,
			PagedResourcesAssembler<ChatMessage> assembler) {
		// get the topic
		Topic topic = chatService.getTopicById(topicId);
		if (topic != null) {
			Page<ChatMessage> chatMessages = chatService
					// get the messages of this topic
					.findByTopic(topic, pageable);
			// return result HATEOAS
			return assembler.toResource(chatMessages);
		} else {
			throw new NotFoundException();
		}
	}
}

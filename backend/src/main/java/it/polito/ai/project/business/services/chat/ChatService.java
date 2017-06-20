package it.polito.ai.project.business.services.chat;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.polito.ai.project.repo.entities.Message;
import it.polito.ai.project.repo.entities.Topic;

public interface ChatService {
	
	/**
	 * Return the last messages for the specified topic.
	 * 
	 * @param topic - the requested topic
	 * @param lastMessages - the amount of requested messages
	 * @return the list of requested messages
	 */
	public List<ChatMessage> getLastMessages(Topic topic, Integer lastMessages);
	
	/**
	 * Return the set of topics
	 * @return
	 */
	public Set<Topic> getTopics();
	
	public Topic getTopicById(String id);
	
	public void saveMessage(Message message);
	
	public Page<ChatMessage> findByTopic(Topic topic, Pageable pageable);
}

package it.polito.ai.project.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.Topic;

public interface TopicsRepository extends CrudRepository<Topic, Long> {
	public Set<Topic> findAll();
	public Topic findByValue(String value);
}

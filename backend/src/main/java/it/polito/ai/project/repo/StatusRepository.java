package it.polito.ai.project.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

	public List<Status> findByValue(String value);
}

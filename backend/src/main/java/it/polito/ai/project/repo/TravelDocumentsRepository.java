package it.polito.ai.project.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.TravelDocument;

public interface TravelDocumentsRepository extends CrudRepository<TravelDocument, Long> {
	Set<TravelDocument> findAll();
}

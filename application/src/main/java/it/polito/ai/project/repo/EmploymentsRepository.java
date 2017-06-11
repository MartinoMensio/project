package it.polito.ai.project.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.Employment;

public interface EmploymentsRepository extends CrudRepository<Employment, Long> {
	Set<Employment> findAll();
}

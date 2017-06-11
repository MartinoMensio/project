package it.polito.ai.project.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.EducationLevel;

public interface EducationLevelsRepository extends CrudRepository<EducationLevel, Long> {
	Set<EducationLevel> findAll();
}

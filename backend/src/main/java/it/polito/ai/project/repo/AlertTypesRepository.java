package it.polito.ai.project.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.alerts.AlertType;

public interface AlertTypesRepository extends CrudRepository<AlertType, Long> {

	public List<AlertType> findAll();
}

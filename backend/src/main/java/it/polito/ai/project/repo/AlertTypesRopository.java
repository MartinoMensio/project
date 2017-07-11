package it.polito.ai.project.repo;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.alerts.AlertType;

public interface AlertTypesRopository extends CrudRepository<AlertType, Long> {

}

package it.polito.ai.project.repo;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.alerts.Alert;


public interface AlertsRepository extends CrudRepository<Alert, Long> {

}

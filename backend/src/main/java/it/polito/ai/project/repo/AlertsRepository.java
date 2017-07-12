package it.polito.ai.project.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.alerts.Alert;


public interface AlertsRepository extends CrudRepository<Alert, Long> {
	
	public List<Alert> findAll();
	public List<Alert> findByHashtagContaining(String hashtag);
}

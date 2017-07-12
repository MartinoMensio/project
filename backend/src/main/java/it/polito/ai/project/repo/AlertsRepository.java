package it.polito.ai.project.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.alerts.Alert;


public interface AlertsRepository extends CrudRepository<Alert, Long> {
	
	public List<Alert> findAll();
	
	/**
	 * Returns the list of active alerts. An active alert is the one that has the active flag set to true and
	 * the lastViewTime less then maxAlertDuration.
	 * 
	 * @param date - It has to be the currentTime - maxAlertDuration
	 * @return the list of active alerts
	 */
	public List<Alert> findByActiveTrueAndLastViewTimeAfter(Date date);
	
	/**
	 * Returns the list of alerts that are active and that have the hashtag equal to the one specified
	 * as parameter.
	 * 
	 * @param hashtag - the string representing the searched hashtag 
	 * @param date - It has to be the currentTime - maxAlertDuration
	 * @return the list of found alerts
	 */
	public List<Alert> findByHashtagContainingAndActiveTrueAndLastViewTimeAfter(String hashtag, Date date);
}

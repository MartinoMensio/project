package it.polito.ai.project.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.alerts.Alert;


public interface AlertsRepository extends CrudRepository<Alert, Long> {
	
	public List<Alert> findAll();
	
	public Alert findOneByIdAndLastViewTimeAfter(Long id, Date date);
	
	/**
	 * Returns the list of active alerts. An active alert is the one that has lastViewTime less than maxAlertDuration.
	 * 
	 * @param date - It has to be the currentTime - maxAlertDuration
	 * @return the list of active alerts
	 */
	public List<Alert> findByLastViewTimeAfter(Date date);
	
	/**
	 * Returns the list of alerts that are active and that have the hashtag equal to the one specified
	 * as parameter.
	 * 
	 * @param hashtag - the string representing the searched hashtag 
	 * @param date - It has to be the currentTime - maxAlertDuration
	 * @return the list of found alerts
	 */
	public List<Alert> findByHashtagContainingAndLastViewTimeAfter(String hashtag, Date date);
}

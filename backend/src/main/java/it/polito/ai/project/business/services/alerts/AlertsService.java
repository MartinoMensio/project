package it.polito.ai.project.business.services.alerts;

import java.util.List;

import it.polito.ai.project.repo.entities.alerts.Alert;
import it.polito.ai.project.repo.entities.alerts.AlertType;

public interface AlertsService {
	
	public List<AlertType> getAlertsType();
	
	public List<Alert> getAlerts();
	
	public Alert getAlertById(Long id);
	
	/**
	 * 
	 * @param hashtag
	 * @return
	 */
	public List<Alert> getAlertsByHashtag(String hashtag);
	
	public Alert addNewAlert(Alert newAlert);
	
	public Alert updateAlertLastViewTime(Long id);
}

package it.polito.ai.project.rest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.alerts.AlertsService;
import it.polito.ai.project.repo.entities.alerts.Alert;
import it.polito.ai.project.repo.entities.alerts.AlertType;

@RestController
public class AlertsController {
	
	@Autowired
	AlertsService alertsService;

	@GetMapping("/api/alerts/types")
	public List<AlertType> getAlertTypes() {
		return alertsService.getAlertsType();
	}

	@PostMapping("/api/alerts")
	public Alert createAlert(@Valid @RequestBody Alert alert) {
		return alertsService.addNewAlert(alert);
	}

	/**
	 * get the alerts list with optional filter on hashtag
	 * 
	 * @param hashtag
	 *            optional
	 * @return
	 */
	@GetMapping("/api/alerts")
	public List<Alert> getAlerts(@RequestParam(defaultValue="") String hashtag) {
		if (hashtag != null && !hashtag.equals("")) {
			return alertsService.getAlertsByHashtag(hashtag);
		} else {
			return alertsService.getAlerts();
		}
	}
	
	@GetMapping("/api/alerts/{id}")
	public Alert getAlertById(@PathVariable(value = "id") Long id) {
		return alertsService.getAlertById(id);
	}
	
	// TODO endpoint for ratings of alerts

}

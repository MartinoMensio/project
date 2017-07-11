package it.polito.ai.project.rest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.alerts.AlertsService;
import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.repo.entities.alerts.Alert;
import it.polito.ai.project.repo.entities.alerts.AlertType;
import it.polito.ai.project.rest.controllers.reqbody.NewAlertBodyRequest;
import it.polito.ai.project.web.exceptions.ClientErrorException;

@RestController
public class AlertsController {
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private AlertsService alertsService;

	@GetMapping("/api/alerts/types")
	public List<AlertType> getAlertTypes() {
		return alertsService.getAlertsType();
	}

	@PostMapping("/api/alerts")
	public Alert createAlert(@Valid @RequestBody NewAlertBodyRequest newAlert, BindingResult result) {
		if (result.hasErrors()) {
			throw new ClientErrorException("wrong fields in the request");
		}
		
		// Create the new alert from the newAlert. It is just a conversion, sorry for the bad naming :-)
		Alert alert = new Alert(null,//TODO should be the object that represents the alert type
								currentUserService.getCurrentUser(),
								newAlert.getAddress(),
								newAlert.getLat(),
								newAlert.getLng(),
								newAlert.getComment());
		
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
		// update the last view time of the requested alert, then return it
		Alert alert = alertsService.updateAlertLastViewTime(id);
		
		return alert;
	}
	
	// TODO endpoint for ratings of alerts

}

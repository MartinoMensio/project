package it.polito.ai.project.business.services.alerts;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polito.ai.project.repo.AlertTypesRepository;
import it.polito.ai.project.repo.AlertsRepository;
import it.polito.ai.project.repo.entities.alerts.Alert;
import it.polito.ai.project.repo.entities.alerts.AlertType;

@Service
@Transactional
public class AlertsServiceImpl implements AlertsService {
	@Autowired
	private AlertTypesRepository alertTypesRepository;
	@Autowired
	private AlertsRepository alertsRepository; 
	

	@Override
	public List<AlertType> getAlertsType() {
		return alertTypesRepository.findAll();
	}

	@Override
	public List<Alert> getAlerts() {
		return alertsRepository.findAll();
	}

	@Override
	public Alert getAlertById(Long id) {
		return alertsRepository.findOne(id);
	}

	@Override
	public List<Alert> getAlertsByHashtag(String hashtag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alert addNewAlert(Alert newAlert) {
		// TODO Auto-generated method stub
		return null;
	}

}

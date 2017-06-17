package it.polito.ai.project.rest.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.repo.CarSharingServicesRepository;
import it.polito.ai.project.repo.EducationLevelsRepository;
import it.polito.ai.project.repo.EmploymentsRepository;
import it.polito.ai.project.repo.FuelsRepository;
import it.polito.ai.project.repo.TravelDocumentsRepository;
import it.polito.ai.project.repo.entities.CarSharingService;
import it.polito.ai.project.repo.entities.EducationLevel;
import it.polito.ai.project.repo.entities.Employment;
import it.polito.ai.project.repo.entities.Fuel;
import it.polito.ai.project.repo.entities.TravelDocument;

/**
 * 
 * This controller provides the profile attribute values to the clients
 *
 */
@RestController
public class ProfileAttributesValuesController {

	@Autowired
	CarSharingServicesRepository carSharingServices;
	
	@Autowired
	EducationLevelsRepository educationLevels;
	
	@Autowired
	EmploymentsRepository employments;
	
	@Autowired
	FuelsRepository fuels;
	
	@Autowired
	TravelDocumentsRepository travelDocuments;
	
	@GetMapping("/api/attributes/carSharingServices")
	public Set<CarSharingService> getCarSharingServices() {
		return carSharingServices.findAll();
	}
	
	@GetMapping("/api/attributes/educationLevels")
	public Set<EducationLevel> getEducationLevels() {
		return educationLevels.findAll();
	}
	
	@GetMapping("/api/attributes/employments")
	public Set<Employment> getEmployments() {
		return employments.findAll();
	}
	
	@GetMapping("/api/attributes/fuels")
	public Set<Fuel> getFuels() {
		return fuels.findAll();
	}
	
	@GetMapping("/api/attributes/travelDocuments")
	public Set<TravelDocument> getTravelDocuments() {
		return travelDocuments.findAll();
	}
}

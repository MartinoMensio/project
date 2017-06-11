package it.polito.ai.project.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.CarSharingService;

public interface CarSharingServicesRepository extends CrudRepository<CarSharingService, Long> {
	Set<CarSharingService> findAll();
}

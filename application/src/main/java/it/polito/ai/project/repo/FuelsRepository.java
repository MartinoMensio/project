package it.polito.ai.project.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.Fuel;

public interface FuelsRepository extends CrudRepository<Fuel, Long> {
	Set<Fuel> findAll();
}

package it.polito.ai.project.postgis.repo;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import it.polito.ai.project.postgis.entities.BusStop;

@Repository
@RepositoryRestResource
@CrossOrigin(origins = "*")
public interface BusStopsRepository extends PagingAndSortingRepository<BusStop, String> {

	public Set<BusStop> findAll();
}

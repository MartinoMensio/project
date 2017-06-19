package it.polito.ai.project.postgis.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import it.polito.ai.project.postgis.entities.BusStop;

@Repository
@RepositoryRestResource
public interface BusStopsRepository extends PagingAndSortingRepository<BusStop, String> {

}

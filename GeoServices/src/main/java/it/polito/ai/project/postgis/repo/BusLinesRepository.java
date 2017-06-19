package it.polito.ai.project.postgis.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import it.polito.ai.project.postgis.entities.BusLine;

@Repository
@RepositoryRestResource
public interface BusLinesRepository  extends PagingAndSortingRepository<BusLine, String> {

}

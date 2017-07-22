package it.polito.ai.project.postgis.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import it.polito.ai.project.postgis.entities.BusLine;

@Repository
@RepositoryRestResource
@CrossOrigin(origins = "*")
public interface BusLinesRepository extends PagingAndSortingRepository<BusLine, String> {

	@RestResource(path="getByLine")
	public List<BusLine> getByLineContainingIgnoreCase(@Param("line") String line);
}

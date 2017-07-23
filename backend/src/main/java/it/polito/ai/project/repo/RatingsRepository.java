package it.polito.ai.project.repo;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.alerts.Rating;
import it.polito.ai.project.repo.entities.alerts.RatingKey;

public interface RatingsRepository extends CrudRepository<Rating, RatingKey> {

}

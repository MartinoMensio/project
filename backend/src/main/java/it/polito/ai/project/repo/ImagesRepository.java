package it.polito.ai.project.repo;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.Image;

public interface ImagesRepository extends CrudRepository<Image, Long> {

}

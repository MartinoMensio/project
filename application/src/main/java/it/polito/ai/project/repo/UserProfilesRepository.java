package it.polito.ai.project.repo;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.UserProfile;

public interface UserProfilesRepository extends CrudRepository<UserProfile, Long> {

}

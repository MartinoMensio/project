package it.polito.ai.project.repo;

import org.springframework.data.repository.CrudRepository;

import it.polito.ai.project.repo.entities.VerificationToken;

public interface VerificationTokensRepository extends CrudRepository<VerificationToken, Long>{

}

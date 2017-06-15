package it.polito.ai.project.business.services.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.polito.ai.project.repo.entities.User;

public interface UsersService {

	/**
	 * Gives access to the repository of users
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<User> findAllUsers(Pageable pageable);
}

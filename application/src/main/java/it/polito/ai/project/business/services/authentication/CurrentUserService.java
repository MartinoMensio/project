package it.polito.ai.project.business.services.authentication;

import it.polito.ai.project.repo.entities.User;

public interface CurrentUserService {

	public User getCurrentUser();
}

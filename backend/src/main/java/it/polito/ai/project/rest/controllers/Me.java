package it.polito.ai.project.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.project.business.services.authentication.CurrentUserService;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;
import it.polito.ai.project.rest.resources.AnonymizedUser;
import it.polito.ai.project.rest.resources.UserResource;

@RestController
public class Me {
	
	@Autowired
	CurrentUserService currentUserService;
	
	// This endpoint returns something also if the user is not logged in
	@RequestMapping("/api/me")
	public UserResource getMe() {
		User currentUser = currentUserService.getCurrentUser();
		if (currentUser != null) {
			return new UserResource(currentUser);
		} else {
			return null;
		}
	}
	
	@RequestMapping("/api/me/details")
	public AnonymizedUser getProfile(){
		User currentUser = currentUserService.getCurrentUser();
		// TODO use UserDetails class
		return new AnonymizedUser(currentUser);
	}

}

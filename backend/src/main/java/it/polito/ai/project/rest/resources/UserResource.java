package it.polito.ai.project.rest.resources;

import it.polito.ai.project.repo.entities.User;

public class UserResource {

	public Long userId;
	public String email;
	public String nickname;
	
	public UserResource(User user) {
		this.userId = user.getId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
	}
}

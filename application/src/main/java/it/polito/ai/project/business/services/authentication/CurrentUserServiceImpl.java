package it.polito.ai.project.business.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.polito.ai.project.repo.UsersRepository;
import it.polito.ai.project.repo.entities.User;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	@Autowired
	UsersRepository userRepository;
	
	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			return userRepository.findByEmail(email);
		} else {
			return null;
		}
	}

}

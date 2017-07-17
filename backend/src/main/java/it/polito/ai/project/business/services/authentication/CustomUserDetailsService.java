package it.polito.ai.project.business.services.authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.polito.ai.project.repo.StatusRepository;
import it.polito.ai.project.repo.UsersRepository;
import it.polito.ai.project.repo.entities.Status;
import it.polito.ai.project.repo.entities.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// the field email is used as username (to identify customer)
		// don't do confusion with nickname!!
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		// The admin has also role admin
		if (email.equals("admin")) {
			auth.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
		}
		String password = user.getPassword();
		// check if user confirmed the email
		Status notConfirmedStatus = statusRepository.findByValue("NOT_VERIFIED").stream().findFirst().get();
		boolean enabled = !user.getStatus().getId().equals(notConfirmedStatus.getId());
		return new org.springframework.security.core.userdetails.User(email, password, enabled, true, true, true, auth);
	}

}

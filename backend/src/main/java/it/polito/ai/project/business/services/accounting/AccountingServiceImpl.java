package it.polito.ai.project.business.services.accounting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polito.ai.project.repo.UsersRepository;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;

@Service
@Transactional
public class AccountingServiceImpl implements AccountingService {
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public User addNewUser(String mail, String nickname, String password) {
		usersRepository.saveUserWithEncPwd(nickname, mail, password);
		return usersRepository.findByEmail(mail);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_USER')")
	public User updateUserProfileInfo(String email, UserProfile userProfile, String nickname) {
		User user = usersRepository.findByEmail(email);
		user.setNickname(nickname);
		user.setProfile(userProfile);
		
		usersRepository.save(user);
		usersRepository.enableUser(email);
		
		return user;
	}

	@Override
	public UserProfile getUserProfileInfo(String username) {
		User user = usersRepository.findByEmail(username);
		if (user == null)
			return null;
		
		return user.getProfile();
	}

	@Override
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResultInfo changePassword(String email, String oldPassword, String newPassword,
			String newConfirmedPassword) {
		
		/* Check if the new password is equal to the confirmed password.
		 * If not return error.
		 */
		if (newPassword.equals(newConfirmedPassword) == false) {
			return ResultInfo.PASSWORD_CHANGE_FAILED;
		}
		
		// Get the user data from the DB
		User user = usersRepository.findByEmail(email);
		
		/*
		 * Check if the old inserted password is equal to the stored password
		 * If not return error
		 */
		if (BCrypt.checkpw(oldPassword, user.getPassword()) == false) {
			return ResultInfo.PASSWORD_CHANGE_FAILED;
		}
		
		// Store the new password into the DB
		usersRepository.changeUserPassword(email, newPassword);

		return ResultInfo.PASSWORD_CHANGE_OK;
	}

}

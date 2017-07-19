package it.polito.ai.project.business.services.accounting;

import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.UserProfile;

public interface AccountingService {
	
	/**
	 * Register a new user to the service.
	 * 
	 * @param email - The user's mail. It represents also the user ID (must be unique)
	 * @param nickname - The user's nickname
	 * @param password - The user's password
	 * @param url - this parameter is optional. If it is set, it indicated the url of
	 * frontend state on which send the user
	 * @return the result status of the registration
	 * 				<ul>
	 * 					<li>Success: ResultInfo.REGISTRATION_OK</li>
	 * 					<li>User already exists: ResultInfo.REGISTRATION_ERR_USERNAME_ALREADY_EXISTS</li>
	 * 					<li>Error: REGISTRATION_ERROR</li>
	 * 				</ul>
	 */
	public User addNewUser(String email, String nickname, String password, String url);
	
	/**
	 * Insert the user's profile details 
	 *  
	 * @param email - The user Id
	 * @param userProfile - The object that represents the user's profile info
	 * @return The updated user object
	 */
	public User updateUserProfileInfo(String mail, UserProfile userProfile, String nickname);
	
	/**
	 * Return the user's profile details
	 * 
	 * @param username - The user's mail
	 * @return an user profile object. If the user does not exist returns null
	 */
	public UserProfile getUserProfileInfo(String username);
	
	/**
	 * Update the user's password
	 * 
	 * @param username - The user's mail
	 * @param oldPassword - The old password
	 * @param newPassword - The new password
	 * @param newConfirmedPassword - The new confirmed password, must be equal to newPassword
	 * @return The result of the password change
	 * 				<ul>
	 * 					<li>Success: ResultInfo.PASSWORD_CHANGE_OK</li>
	 * 					<li>Failure: ResultInfo.PASSWORD_CHANGE_FAILED</li>
	 * 				</ul>
	 */
	public ResultInfo changePassword(String username, String oldPassword, String newPassword, String newConfirmedPassword);
}

package it.polito.ai.project.business.services.emailVerification;

public interface EmailVerificationService {

	/**
	 * Sends a verification mail
	 * @param mail
	 * @param nickname
	 */
	public void sendVerificationMail(String mail, String nickname, String url);
	
	/**
	 * Attempts to verify the email address with the provided token
	 * @param email
	 * @param token
	 * @return
	 */
	public boolean verifyMail(String email, String token);
}

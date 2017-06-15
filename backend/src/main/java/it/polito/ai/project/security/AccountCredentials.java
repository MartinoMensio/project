package it.polito.ai.project.security;

/**
 * 
 * Simple POJO class for login requests
 *
 */
public class AccountCredentials {

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

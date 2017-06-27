package it.polito.ai.project.business.services.emailVerification;

public class EmailAddress {

	private String email;

	public EmailAddress(String destinationEmail) {
		this.email = destinationEmail;
	}

	public String getEmail() {
		return email;
	}

}

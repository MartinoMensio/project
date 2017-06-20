package it.polito.ai.project.business.services.emailVerification;

import java.util.ArrayList;
import java.util.List;

public class EmailRequest {

	private List<EmailPersonalization> personalizations;
	private EmailAddress from;
	private String template_id;

	public EmailRequest(String destinationEmail, String nickname, String activation_link, String fromEmail) {
		from = new EmailAddress(fromEmail);
		template_id = "729dad35-3c36-48ba-a679-c8485a42630e";
		personalizations = new ArrayList<>();
		personalizations.add(new EmailPersonalization(destinationEmail, nickname, activation_link));
	}

	public List<EmailPersonalization> getPersonalizations() {
		return personalizations;
	}

	public EmailAddress getFrom() {
		return from;
	}

	public String getTemplate_id() {
		return template_id;
	}

}

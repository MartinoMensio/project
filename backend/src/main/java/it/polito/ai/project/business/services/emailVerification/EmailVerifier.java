package it.polito.ai.project.business.services.emailVerification;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EmailVerifier {
	
	private RestTemplate restTemplate = new RestTemplate();
	private static String sendgrid_endpoint = "https://api.sendgrid.com/v3/mail/send";
	private static String sendgrid_token = "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";
	
	
	public void sendVerificationMail(String mail, String nickname) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + sendgrid_token);
		
		// TODO create activation link
		EmailRequest request = new EmailRequest(mail, nickname, "http://test.org/" + nickname, "project@ai.polito.it");
		HttpEntity<EmailRequest> entity = new HttpEntity<EmailRequest>(request, headers);
		
		ResponseEntity<String> sendgridResponse = restTemplate.postForEntity(sendgrid_endpoint, entity, String.class);
		if (sendgridResponse.getStatusCode() != HttpStatus.ACCEPTED) {
			throw new RuntimeException("failed to send email");
		}
	}

}

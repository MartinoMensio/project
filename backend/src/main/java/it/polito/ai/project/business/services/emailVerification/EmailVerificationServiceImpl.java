package it.polito.ai.project.business.services.emailVerification;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import it.polito.ai.project.repo.UsersRepository;
import it.polito.ai.project.repo.VerificationTokensRepository;
import it.polito.ai.project.repo.entities.User;
import it.polito.ai.project.repo.entities.VerificationToken;
import it.polito.ai.project.rest.controllers.ProfileRestController;

@Transactional
@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
	
	private RestTemplate restTemplate = new RestTemplate();
	private static String sendgrid_endpoint = "https://api.sendgrid.com/v3/mail/send";
	private static String sendgrid_token = "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";
	
	@Autowired
	private UsersRepository users;

	@Autowired
	private VerificationTokensRepository tokens;
	
	@Override
	public void sendVerificationMail(String email, String nickname) {
		User user = users.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("current user is null");
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + sendgrid_token);
		
		// create activation link with random token
		String token = new BigInteger(130, new SecureRandom()).toString(32);
		String link = generateLink(email, token);
		
		EmailRequest request = new EmailRequest(email, nickname, link, "project@ai.polito.it");
		HttpEntity<EmailRequest> entity = new HttpEntity<EmailRequest>(request, headers);
		
		ResponseEntity<String> sendgridResponse = restTemplate.postForEntity(sendgrid_endpoint, entity, String.class);
		if (sendgridResponse.getStatusCode() != HttpStatus.ACCEPTED) {
			throw new RuntimeException("failed to send email");
		}
		tokens.save(new VerificationToken(user, token));
	}
	
	@Override
	public boolean verifyMail(String email, String token) {
		User user = users.findByEmail(email);
		if (user == null) {
			return false;
		}
		VerificationToken verificationToken = tokens.findOne(user.getId());
		if (!verificationToken.getToken().equals(token)) {
			return false;
		}
		users.enableUser(email);
		return true;
	}
	
	private String generateLink(String email, String token) {
		Link verificationLink = ControllerLinkBuilder 
		          .linkTo(ControllerLinkBuilder.methodOn(ProfileRestController.class).confirmEmail(email, token)) 
		          .withSelfRel();
		return verificationLink.getHref();
	}

}

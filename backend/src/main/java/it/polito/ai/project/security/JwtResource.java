package it.polito.ai.project.security;

import java.util.Date;

/**
 * 
 * This class contains the informations about the JWT token that will be sent to the client that logs in
 *
 */
public class JwtResource {

	private String token;
	private Date expiration;

	public JwtResource(String token, Date expiration) {
		this.token = token;
		this.expiration = expiration;
	}

	public String getToken() {
		return token;
	}

	public Date getExpiration() {
		return expiration;
	}

}

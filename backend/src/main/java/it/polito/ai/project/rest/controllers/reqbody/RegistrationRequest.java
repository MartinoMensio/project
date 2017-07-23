package it.polito.ai.project.rest.controllers.reqbody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

public class RegistrationRequest {
	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(min = 1)
	private String nickname;
	@NotNull
	@Size(min = 8)
	private String password;

	/**
	 * this parameter is optional. If it is set, it indicated the url of
	 * frontend state on which send the user
	 */
	@URL
	private String url;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

package it.polito.ai.project.business.services.emailVerification.sendgrid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailValues {

	private String nickname;

	private String activation_link;

	public EmailValues(String nickname, String activation_link) {
		this.nickname = nickname;
		this.activation_link = activation_link;
	}

	@JsonProperty(":nickname")
	public String getNickname() {
		return nickname;
	}

	@JsonProperty(":activation_link")
	public String getActivation_link() {
		return activation_link;
	}

}

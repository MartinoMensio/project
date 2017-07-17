package it.polito.ai.project.rest.controllers.reqbody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordChangeRequest {
	@NotNull
	private String oldPassword;
	@NotNull
	@Size(min=8)
	private String newPassword;
	@NotNull
	@Size(min=8)
	private String confirmedPassword;
	
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getConfirmedPassword() {
		return confirmedPassword;
	}
	
	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
}

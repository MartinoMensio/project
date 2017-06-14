package it.polito.ai.project.rest;

public class CustomResponse {

	public int status;
	public String message;
	
	public CustomResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
}

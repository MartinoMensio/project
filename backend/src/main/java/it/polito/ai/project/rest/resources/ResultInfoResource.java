package it.polito.ai.project.rest.resources;

public class ResultInfoResource {

	private int status;
	private String message;

	public ResultInfoResource(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}

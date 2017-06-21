package it.polito.ai.project.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncompleteProfileException extends RuntimeException {

	private static final long serialVersionUID = 277563367911444650L;

	public IncompleteProfileException() {
		super();
	}

	public IncompleteProfileException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompleteProfileException(String message) {
		super(message);
	}

	public IncompleteProfileException(Throwable cause) {
		super(cause);
	}
}

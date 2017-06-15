package it.polito.ai.project.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientErrorException extends RuntimeException {

	private static final long serialVersionUID = -8692509938405410788L;

	public ClientErrorException() {
        super();
    }

	public ClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }

	public ClientErrorException(String message) {
        super(message);
    }

	public ClientErrorException(Throwable cause) {
        super(cause);
    }
}

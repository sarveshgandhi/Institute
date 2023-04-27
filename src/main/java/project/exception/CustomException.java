package project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomException extends ResponseStatusException {

    public CustomException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public CustomException(String reason, Object... args) {
        super(HttpStatus.BAD_REQUEST, String.format(reason, args));
    }
}

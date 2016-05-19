package com.nichesoftware.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 28/04/2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This user is not found.")
public class UserNotFoundException extends ServerException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.nichesoftware.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 28/04/2016.
 */
@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason = "Token is not correct.")
public class NotAuthorizedException extends Exception {
    public NotAuthorizedException() {
        super();
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.nichesoftware.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 28/04/2016.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends Exception {
    public GenericException() {
        super();
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}

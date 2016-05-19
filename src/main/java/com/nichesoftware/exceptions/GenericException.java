package com.nichesoftware.exceptions;

/**
 * Created by n_che on 28/04/2016.
 */
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

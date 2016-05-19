package com.nichesoftware.exceptions;

/**
 * Created by n_che on 28/04/2016.
 */
public class ServerException extends Exception {
    public ServerException() {
        super();
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.nichesoftware.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 28/04/2016.
 */
@ResponseStatus(value= HttpStatus.CONFLICT, reason = "This key already exists.")
public class KeyAlreadyExistsException extends ServerException {
    /**
     * Clé
     */
    private String key;

    public KeyAlreadyExistsException() {
        super();
    }

    public KeyAlreadyExistsException(String message) {
        super(message);
    }

    public KeyAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public KeyAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Getter of the key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter of the key
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
}

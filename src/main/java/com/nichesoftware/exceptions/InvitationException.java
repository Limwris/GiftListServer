package com.nichesoftware.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 27/06/2016.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "The invitation cannot be sent.")
public class InvitationException extends ServerException {
    public InvitationException() {
        super();
    }

    public InvitationException(String message) {
        super(message);
    }

    public InvitationException(Throwable cause) {
        super(cause);
    }

    public InvitationException(String message, Throwable cause) {
        super(message, cause);
    }
}

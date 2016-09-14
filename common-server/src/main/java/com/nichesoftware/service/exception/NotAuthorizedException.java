package com.nichesoftware.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 28/04/2016.
 */
@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason = "Token is not correct.")
public class NotAuthorizedException extends GenericException {
}

package com.nichesoftware.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 13/09/2016.
 */
@ResponseStatus(value= HttpStatus.CONFLICT)
public class ConflitException extends GenericException {
}

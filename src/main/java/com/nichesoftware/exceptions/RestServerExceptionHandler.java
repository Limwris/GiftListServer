package com.nichesoftware.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by n_che on 28/04/2016.
 */
@ControllerAdvice
@EnableWebMvc
public class RestServerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(KeyAlreadyExistsException.class)
    public void handleKeyAlreadyExistsException(KeyAlreadyExistsException e) {
        // Nothing
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenericException.class)
    public String handleGenericException(GenericException e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(UserNotFoundException e) {
        // Nothing
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public void handleAuthenticationException(AuthenticationException e) {
        // Nothing
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public void handleNotAuthorizedException(NotAuthorizedException e) {
        // Nothing
    }
}

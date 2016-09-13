package com.nichesoftware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by n_che on 08/09/2016.
 */
public class BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class.getSimpleName());

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> defaultErrorHandler(Exception e) throws Exception {
        logger.debug("defaultErrorHandler");
        // If the exception is annotated with @ResponseStatus, and if its
        // message is empty rethrow it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null
                && StringUtils.isEmpty(e.getMessage())) {
            logger.debug("defaultErrorHandler - rethrow exception, let framework handles it");
            throw e;
        }
        ResponseStatus status = AnnotationUtils.getAnnotation(e.getClass(), ResponseStatus.class);
        logger.debug(String.format("Custom handling, with status: %s & message: %s", status.value(), e.getMessage()));
        return new ResponseEntity<>(e.getMessage(), status.value());
    }
}

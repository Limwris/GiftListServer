package com.nichesoftware.controller;

import com.nichesoftware.dto.DTO;
import com.nichesoftware.dto.ErrorMetadata;
import com.nichesoftware.dto.Metadata;
import com.nichesoftware.service.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by n_che on 08/09/2016.
 */
@Controller
public class BaseController {
    // Fields   --------------------------------------------------------------------------------------------------------
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class.getSimpleName());

    // Methods   -------------------------------------------------------------------------------------------------------
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
        logger.debug(String.format("defaultErrorHandler - custom handling, with status: %s & message: %s",
                status.value(), e.getMessage()));
        return new ResponseEntity<>(e.getMessage(), status.value());
    }

    @ExceptionHandler(value = GenericException.class)
    public ResponseEntity<DTO> defaultGenericErrorHandler(GenericException e) throws Exception {
        logger.debug("defaultGenericErrorHandler");
        // If the exception is annotated with @ResponseStatus, and if its
        // error message is empty rethrow it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null
                && StringUtils.isEmpty(e.getErrorMessage())) {
            logger.debug("defaultGenericErrorHandler - rethrow exception, let framework handles it");
            throw e;
        }
        ResponseStatus status = AnnotationUtils.getAnnotation(e.getClass(), ResponseStatus.class);
        logger.debug(String.format("defaultGenericErrorHandler - custom handling, with status: %s & message: %s",
                status.value(), e.getMessage()));

        DTO response = new DTO();
        ErrorMetadata metadata = new ErrorMetadata();
        metadata.setCode(e.getErrorCode());
        metadata.setMessage(e.getErrorMessage());
        response.setMetadata(metadata);

        return new ResponseEntity<>(response, status.value());
    }
}

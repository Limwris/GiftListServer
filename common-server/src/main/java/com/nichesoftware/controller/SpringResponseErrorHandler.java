package com.nichesoftware.controller;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nichesoftware.dto.DTO;
import com.nichesoftware.dto.ErrorMetadata;
import com.nichesoftware.service.exception.NotAuthorizedException;
import com.nichesoftware.utils.RestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by n_che on 13/09/2016.
 */
public class SpringResponseErrorHandler implements ResponseErrorHandler {
    // Fields   --------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(SpringResponseErrorHandler.class.getSimpleName());

    // Methods   -------------------------------------------------------------------------------------------------------
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtils.isError(response.getStatusCode());
    }

    // Sources: - http://www.develmagic.com/2016/02/10/handle-spring-boot-exceptions/
    //          - http://springinpractice.com/2013/10/07/handling-json-error-object-responses-with-springs-resttemplate
    //          - https://blog.zenika.com/2011/05/18/error-handling-with-rest/
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        logger.error(String.format("SpringResponseErrorHandler - handleError [code: %s,  message: %s]",
                response.getStatusCode(), response.getStatusText()));

        final String errorPayload = IOUtils.toString(response.getBody());
        logger.error(String.format("SpringResponseErrorHandler - handleError [payload: %s]", errorPayload));

        GsonBuilder gson = new GsonBuilder();
        Type DTOType = new TypeToken<DTO<Serializable, ErrorMetadata>>() {}.getType();
        DTO<Serializable, ErrorMetadata> dto = gson.create().fromJson(errorPayload, DTOType);

        NotAuthorizedException exception = new NotAuthorizedException();
        exception.setErrorMessage(dto.getMetadata().getMessage());
        exception.setErrorCode(dto.getMetadata().getCode());
        throw exception;
    }
}

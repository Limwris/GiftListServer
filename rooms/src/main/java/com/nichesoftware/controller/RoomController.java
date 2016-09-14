package com.nichesoftware.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nichesoftware.dto.*;
import com.nichesoftware.service.IRoomService;
import com.nichesoftware.service.RoomService;
import com.nichesoftware.service.exception.GenericException;
import com.nichesoftware.service.exception.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.*;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collections;

/**
 * Created by n_che on 12/09/2016.
 */
@RestController
public class RoomController extends BaseController {
    // Fields   --------------------------------------------------------------------------------------------------------
    private final static String ROOMS_PATH = "rooms";
    private final static String AUTORIZATION_URL = "http://localhost:8090/giftlist/authorization";

    /**
     * Service
     */
    private IRoomService service;

    // Constructors   --------------------------------------------------------------------------------------------------
    @Autowired
    public RoomController(RoomService service) {
        this.service = service;
    }

    // Methods   -------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ROOMS_PATH,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DTO<RoomDTO, Metadata>> getRooms(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        // Set the X-Auth-Token header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Auth-Token", token);
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        // Sources: - http://docs.spring.io/autorepo/docs/spring-android/1.0.x/reference/html/rest-template.html#rest-template-message-converters
        RestTemplate rt = new RestTemplate();
        rt.setErrorHandler(new SpringResponseErrorHandler());
        ResponseEntity<DTO<UserDTO, Metadata>> authorizationResponse = rt.exchange(AUTORIZATION_URL,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<DTO<UserDTO, Metadata>>() {
                });
        UserDTO userDTO = authorizationResponse.getBody().getData();

        Metadata metadata = new Metadata();
        metadata.setUrl(ROOMS_PATH);

        DTO<RoomDTO, Metadata> dto = new DTO<>();
        RoomDTO roomDTO = new RoomDTO();
        // Todo
        roomDTO.setRoomName(userDTO.getUsername());
        dto.setData(new RoomDTO());
        dto.setMetadata(metadata);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

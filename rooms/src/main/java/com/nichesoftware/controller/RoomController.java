package com.nichesoftware.controller;

import com.nichesoftware.dto.DTO;
import com.nichesoftware.dto.Metadata;
import com.nichesoftware.dto.RoomDTO;
import com.nichesoftware.dto.UserDTO;
import com.nichesoftware.service.IRoomService;
import com.nichesoftware.service.RoomService;
import com.nichesoftware.service.exception.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<DTO<RoomDTO, Metadata>> authorization(@RequestHeader(value="X-Auth-Token") String token) throws Exception {
        // Set the X-Auth-Token header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Auth-Token", token);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<DTO<UserDTO, Metadata>> authorizationResponse = rt.exchange(AUTORIZATION_URL,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<DTO<UserDTO, Metadata>>() {});

        // Todo: Gestion plus fine des erreurs du webservice d'authorization
        if (authorizationResponse.getStatusCode() != HttpStatus.OK) {
            throw new NotAuthorizedException();
        }

        UserDTO userDTO = authorizationResponse.getBody().getData();

        Metadata metadata = new Metadata();
        metadata.setUrl(ROOMS_PATH);

        DTO<RoomDTO, Metadata> dto = new DTO<>();
        dto.setData(new RoomDTO());
        dto.setMetadata(metadata);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
